package com.main.app.service.category;

import com.main.app.converter.category_parent.ParentCategoryConverter;
import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.category.CategoryDTO;
import com.main.app.domain.dto.category_parent.ParentCategoryDTO;
import com.main.app.domain.model.category.Category;
import com.main.app.domain.model.category_parent.ParentCategory;
import com.main.app.elastic.dto.category.CategoryElasticDTO;
import com.main.app.elastic.repository.category.CategoryElasticRepository;
import com.main.app.elastic.repository.category.CategoryElasticRepositoryBuilder;
import com.main.app.repository.category.CategoryRepository;
import com.main.app.repository.category_parent.ParentCategoryRepository;
import com.main.app.service.product.ProductService;
import com.main.app.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.*;

import static com.main.app.converter.category.CategoryConverter.listToDTOList;
import static com.main.app.converter.category.CategoryConverter.listToFilterDTOList;
import static com.main.app.static_data.Messages.*;
import static com.main.app.util.MD5HashUtil.md5;
import static com.main.app.util.Util.categoriesToIds;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryServiceImpl implements CategoryService {

    @Value("${document.productcategory.path}")
    private String documentPath;

    private final CategoryRepository categoryRepository;

    private final CategoryElasticRepository categoryElasticRepository;

    private  final CategoryElasticRepositoryBuilder categoryElasticRepositoryBuilder;

    private final ProductService productService;

    private final ParentCategoryRepository parentCategoryRepository;


    @Override
    public Entities getAll() {
        List<Category> productCategoryList = categoryRepository.findAll();

        Entities entities = new Entities();
        entities.setEntities(listToDTOList(productCategoryList));
        entities.setTotal(productCategoryList.size());

        return entities;
    }

    @Override
    public Entities getAllBySearchParam(String searchParam, Pageable pageable) {
        Page<CategoryElasticDTO> pagedCategories = categoryElasticRepository.search(categoryElasticRepositoryBuilder.generateQuery(searchParam), pageable);

        List<Long> ids = categoriesToIds(pagedCategories);

        Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        List<Category> productCategories = categoryRepository.findAllByIdIn(ids, mySqlPaging).getContent();

        Entities entities = new Entities();
        entities.setEntities(listToDTOList(productCategories));
        entities.setTotal(pagedCategories.getTotalElements());

        return entities;
    }

    @Override
    public Category getOne(Long id) {
        return categoryRepository.getOne(id);
    }

    @Override
    public Category save(Category category,CategoryDTO categoryDTO) {
        Optional<Category> oneProductCategory = categoryRepository.findOneByName(category.getName());

        if(oneProductCategory.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CATEGORY_WITH_NAME_ALREADY_EXIST);
        }

        if(category.getName() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CATEGORY_NAME_CANT_BE_NULL);
        }

        Category savedCategory = categoryRepository.save(category);
        categoryElasticRepository.save(new CategoryElasticDTO(savedCategory));

        for (Long parentCategoryId : categoryDTO.getParentCategoriesIds()) {
            Category cat = categoryRepository.findById(parentCategoryId).get();
            if(cat == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CATEGORY_NOT_EXIST);
            }
            ParentCategory parentCategory = new ParentCategory();
            parentCategory.setCategory(category);
            parentCategory.setParentCategory(cat);
            ParentCategory savedParentCategory = parentCategoryRepository.save(parentCategory);
        }

        return savedCategory;
    }

    @Override
    public Category edit(Category category, CategoryDTO categoryDTO, Long id) {
        Optional<Category> oneProductCategory = categoryRepository.findOneByName(category.getName());

        if(oneProductCategory.isPresent() && !id.equals(oneProductCategory.get().getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CATEGORY_WITH_NAME_ALREADY_EXIST);
        }

        Optional<Category> optionalProductCategory = categoryRepository.findOneById(id);

        if(!optionalProductCategory.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CATEGORY_NOT_EXIST);
        }

        Category foundProductCategory = optionalProductCategory.get();

        if(category.getName() != null){
            foundProductCategory.setName(category.getName());
        }
        if(category.getTitle() != null){
            foundProductCategory.setTitle(category.getTitle());
        }
        if(category.getSubtitle() != null){
            foundProductCategory.setSubtitle(category.getSubtitle());
        }
        if(category.getContentText() != null){
            foundProductCategory.setContentText(category.getContentText());
        }
        if(category.getDescription() != null){
            foundProductCategory.setDescription(category.getDescription());
        }
        foundProductCategory.setFirstOrderCategory(category.isFirstOrderCategory());
        foundProductCategory.setSecondOrderCategory(category.isSecondOrderCategory());
        foundProductCategory.setThirdOrderCategory(category.isThirdOrderCategory());



        Category savedProductCategory = categoryRepository.save(foundProductCategory);
        categoryElasticRepository.save(new CategoryElasticDTO(savedProductCategory));


        if(categoryDTO != null){
            List<ParentCategory> parentCategoryList = parentCategoryRepository.findAllByCategoryId(categoryDTO.getId());
            ArrayList<Long> temp = new ArrayList<>();

            for (ParentCategory parentCategory : parentCategoryList) {
                temp.add(parentCategory.getParentCategory().getId());
            }

            temp.removeAll(categoryDTO.getParentCategoriesIds());
            ArrayList<Long> toDelete = temp;
            ArrayList<Long> toADD = new ArrayList<>();

            for(Long parentCategoryId : categoryDTO.getParentCategoriesIds()){
                List<ParentCategory> parentCategories = parentCategoryRepository.findAllByParentCategoryId(parentCategoryId);
                ParentCategory pcTemp = null;
                for (ParentCategory pc : parentCategories) {
                    if(pc.getCategory().getId() == savedProductCategory.getId()){
                        pcTemp =  pc;
                    }
                }

                if(pcTemp != null && pcTemp.getCategory().getId() == savedProductCategory.getId()){

                }else{
                    toADD.add(parentCategoryId);
                }
            }


            for (Long catId : toDelete) {
                List<ParentCategory> pcList = parentCategoryRepository.findAllByParentCategoryId(catId);
                for (ParentCategory parentCat : pcList) {
                    if(parentCat.getCategory().getId() == savedProductCategory.getId()){
                        parentCat.setDeleted(true);
                        parentCat.setDateDeleted(Calendar.getInstance().toInstant());
                        parentCategoryRepository.save(parentCat);
                    }
                }
            }

            for (Long pcId : toADD) {
                Category parentCat = categoryRepository.findById(pcId).get();
                if(parentCat == null){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CATEGORY_NOT_EXIST);
                }
                ParentCategory parentCategory = new ParentCategory();
                parentCategory.setCategory(savedProductCategory);
                parentCategory.setParentCategory(parentCat);
                ParentCategory savedParentCategory = parentCategoryRepository.save(parentCategory);
            }

        }




        return savedProductCategory;
    }

    @Override
    public Category delete(Long id) {

        Optional<Category> optionalProductCategory = categoryRepository.findOneById(id);

        if (!optionalProductCategory.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CATEGORY_NOT_EXIST);
        }

        if(optionalProductCategory.get().isFirstOrderCategory()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CANT_DELETE_FIRST_ORDER_CATEGORY);
        }

        if(optionalProductCategory.get().isSecondOrderCategory()){
            List<Long> subIds = getAllSubCategories(optionalProductCategory.get().getId());
            if(subIds.size() > 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CANT_DELETE_SUB_CATEGORIES_EXIST);
            }
        }


        productService.checkIfHasForeignKey(id, "category");

        Category foundCategory = optionalProductCategory.get();
        foundCategory.setDeleted(true);
        foundCategory.setDateDeleted(Calendar.getInstance().toInstant());

        Category savedProductCategory = categoryRepository.save(foundCategory);
        categoryElasticRepository.save(ObjectMapperUtils.map(foundCategory, CategoryElasticDTO.class));

        List<ParentCategory> parentCategoryList = parentCategoryRepository.findAllByCategoryId(id);

        for (ParentCategory parentCategoryCateg: parentCategoryList) {
            parentCategoryCateg.setDeleted(true);
            parentCategoryCateg.setDateDeleted(Calendar.getInstance().toInstant());
            parentCategoryRepository.save(parentCategoryCateg);
        }


        return savedProductCategory;
    }

    @Override
    public void uploadImage(Long id, MultipartFile[] images) throws IOException {

        for(MultipartFile uploadedFile : images) {
            String folderName = this.createDirectory().split("/")[1];
            String imageName = md5(uploadedFile.getOriginalFilename());

            byte[] bytes = uploadedFile.getBytes();

            Path path = Paths.get(documentPath + folderName + "/" + imageName  + ".png");
            Files.write(path, bytes);

            Category category = this.getOne(id);
            category.setPrimaryImageUrl("images/" + folderName + "/" + imageName + ".png");

            this.edit(category, null , id);
        }

    }

    @Override
    public List<CategoryDTO> getAllWhereNameIsParentCategory(String name) {

        Category cat = categoryRepository.findOneByName(name).get();
        List<ParentCategory> parentCategoriesList = parentCategoryRepository.findAllByParentCategoryId(cat.getId());

        List<Category> parentCategories = new ArrayList<>();
        for (ParentCategory pc : parentCategoriesList) {
            parentCategories.add(pc.getCategory());
        }

        return listToFilterDTOList(parentCategories);

    }



    @Override
    public Category findByCategoryName(String name) {
        return categoryRepository.findOneByName(name).get();
    }

    @Override
    public List<ParentCategoryDTO> getAllParentCategoriesForCategoryId(Long category_id) {
        List<ParentCategory> parentCategories = parentCategoryRepository.findAllByCategoryId(category_id);
        return ParentCategoryConverter.listToDTOList(parentCategories);
    }

    @Override
    public List<Long> getAllSubCategories(Long category_id) {
        List <Long> ids = new ArrayList<>();
        List<ParentCategory> parentCategoriesList = parentCategoryRepository.findAllByParentCategoryId(category_id);

        List<Category> parentCategories = new ArrayList<>();
        for (ParentCategory pc : parentCategoriesList) {
            parentCategories.add(pc.getCategory());
            ids.add(pc.getCategory().getId());
        }

        for (Category cat : parentCategories) {
            if(parentCategoryRepository.findAllByParentCategoryId(cat.getId()) != null){
                List<ParentCategory> list = parentCategoryRepository.findAllByParentCategoryId(cat.getId());
                for (ParentCategory parCat : list) {
                    ids.add(parCat.getCategory().getId());
                }
            }
        }
        return ids;
    }


    private String createDirectory() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH)+1;

        String folderName = "/" + month + "-" + year;

        new File(documentPath + folderName).mkdirs();

        return folderName;
    }
}
