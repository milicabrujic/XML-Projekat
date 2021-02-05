package com.main.app.service.attribute;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.model.attribute.Attribute;
import com.main.app.domain.model.attribute_value.AttributeValue;
import com.main.app.elastic.dto.attribute.AttributeElasticDTO;
import com.main.app.elastic.repository.attribute.AttributeElasticRepository;
import com.main.app.elastic.repository.attribute.AttributeElasticRepositoryBuilder;
import com.main.app.elastic.repository.attribute_value.AttributeValueElasticRepository;
import com.main.app.elastic.repository.attribute_value.AttributeValueElasticRepositoryBuilder;
import com.main.app.repository.attribute.AttributeRepository;
import com.main.app.repository.attribute_value.AttributeValueRepository;
import com.main.app.service.attribute_value.AttributeValueService;
import com.main.app.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static com.main.app.converter.attribute.AttributeConverter.listToDTOList;
import static com.main.app.static_data.Messages.*;
import static com.main.app.util.Util.attributesToIds;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AttributeServiceImpl implements AttributeService {

    private final AttributeRepository attributeRepository;

    private final AttributeElasticRepository attributeElasticRepository;

    private final AttributeElasticRepositoryBuilder attributeElasticRepositoryBuilder;

    private final AttributeValueRepository attributeValueRepository;

    private final AttributeValueService attributeValueService;


    @Override
    public Entities getAll() {
        List<Attribute> attributeList = attributeRepository.findAll();

        Entities entities = new Entities();
        entities.setEntities(listToDTOList(attributeList));
        entities.setTotal(attributeList.size());

        return entities;
    }

    @Override
    public Entities getAllBySearchParam(String searchParam, Pageable pageable) {
        Page<AttributeElasticDTO> pagedAttributes = attributeElasticRepository.search(attributeElasticRepositoryBuilder.generateQuery(searchParam), pageable);

        List<Long> ids = attributesToIds(pagedAttributes);

        Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        List<Attribute> attributes = attributeRepository.findAllByIdIn(ids, mySqlPaging).getContent();

        Entities entities = new Entities();
        entities.setEntities(listToDTOList(attributes));
        entities.setTotal(pagedAttributes.getTotalElements());

        return entities;
    }

    @Override
    public Attribute getOne(Long id) {
        return attributeRepository.getOne(id);
    }


    @Override
    public Attribute save(Attribute attribute) {
        Optional<Attribute> oneAttribute = attributeRepository.findOneByName(attribute.getName());

        if(oneAttribute.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_WITH_NAME_ALREADY_EXIST);
        }

        if(attribute.getName() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_NAME_CANT_BE_NULL);
        }

        Attribute savedAttribute = attributeRepository.save(attribute);
        attributeElasticRepository.save(new AttributeElasticDTO(savedAttribute));

        return savedAttribute;
    }

    @Override
    public Attribute edit(Attribute attribute, Long id) {
        Optional<Attribute> oneAttribute = attributeRepository.findOneByName(attribute.getName());

        if(oneAttribute.isPresent() && !id.equals(oneAttribute.get().getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_WITH_NAME_ALREADY_EXIST);
        }

        Optional<Attribute> optionalAttribute = attributeRepository.findOneById(id);

        if(!optionalAttribute.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_NOT_EXIST);
        }

        Attribute foundAttribute = optionalAttribute.get();

        if(attribute.getName() != null){
            foundAttribute.setName(attribute.getName());
        }

        Attribute savedAttribute = attributeRepository.save(foundAttribute);
        attributeElasticRepository.save(new AttributeElasticDTO(savedAttribute));

        return savedAttribute;
    }

    @Override
    public Attribute delete(Long id) {
        Optional<Attribute> optionalAttribute = attributeRepository.findOneById(id);

        if (!optionalAttribute.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_NOT_EXIST);
        }

        //TODO Check if has foregin key
        List<AttributeValue> attrValueList = attributeValueRepository.findAllByAttributeId(id);
        for (int i=0;i<attrValueList.size();i++) {
            attributeValueService.delete(attrValueList.get(i).getId());
        }

        Attribute foundAttribute = optionalAttribute.get();
        foundAttribute.setDeleted(true);
        foundAttribute.setDateDeleted(Calendar.getInstance().toInstant());

        Attribute savedAttribute = attributeRepository.save(foundAttribute);
        attributeElasticRepository.save(ObjectMapperUtils.map(foundAttribute, AttributeElasticDTO.class));

        return savedAttribute;
    }

}
