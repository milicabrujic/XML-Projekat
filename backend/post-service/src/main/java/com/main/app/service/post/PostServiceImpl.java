package com.main.app.service.post;

import com.main.app.converter.post.PostConverter;
import com.main.app.converter.user.UserConverter;
import com.main.app.domain.dto.post.PostDTO;
import com.main.app.domain.model.album.Album;
import com.main.app.domain.model.post.*;
import com.main.app.domain.model.user.User;
import com.main.app.enums.PostType;
import com.main.app.repository.album.AlbumRepository;
import com.main.app.repository.post.*;
import com.main.app.service.user.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private PostLocationRepository postLocationRepository;

    @Autowired
    private PostTagRepository postTagRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    @Override
    public Post add(PostDTO postDTO, PostType postType) {

        User user = currentUserService.getCurrentUser();

        if(user == null) {
            return null;
        }

        Post post = PostConverter.postDTOtoPostEntity(postDTO);
        post.setPostType(postType);
        post.setUserId(user.getId());
        post.setHiglight(postDTO.isHiglight());
        Album album  =albumRepository.getOne(postDTO.getAlbumId());
        post.setAlbum(album);

        Post savedPost =  postRepository.save(post);

        if(postDTO.getLocation() != null && !postDTO.getLocation().equals("")) {

            Optional<Location> location = locationRepository.findOneByName(postDTO.getLocation());

            if(location.isPresent()) {

                PostLocation postLocation = new PostLocation();
                postLocation.setPost(savedPost);
                postLocation.setLocation(location.get());

                postLocationRepository.save(postLocation);
            }
        }

        if(postDTO.getTag() != null && !postDTO.getTag().equals("")) {

            Optional<Tag> tag = tagRepository.findOneByName(postDTO.getLocation());

            if(tag.isPresent()) {

                PostTag postTag = new PostTag();
                postTag.setPost(savedPost);
                postTag.setTag(tag.get());

                postTagRepository.save(postTag);
            }
        }

        return savedPost;
    }

    @Override
    public List<Post> findAllByPostType(PostType postType, String search) {

        User user = currentUserService.getCurrentUser();

        if(user == null && (search == null || search.equals(""))) {
            return postRepository.findAllByPublicPostTrueAndPostType(postType);
        }

        Optional<Location> location = locationRepository.findOneByName(search);
        Optional<Tag> tag = tagRepository.findOneByName(search);

        List<Post> result = new ArrayList<>();

        if(location.isPresent()) {

            List<PostLocation> postLocations = postLocationRepository.findAllByLocationId(location.get().getId());

            for(PostLocation postLocation: postLocations) {

                if(user == null && !postLocation.getPost().isPublicPost()) {
                    continue;
                }

                if(isPostInList(result, postLocation.getPost())) {
                    continue;
                }

                result.add(postLocation.getPost());
            }
        }

        if(tag.isPresent()) {
            List<PostTag> postTags = postTagRepository.findAllByTagId(tag.get().getId());

            for(PostTag postTag: postTags) {

                if(user == null && !postTag.getPost().isPublicPost()) {
                    continue;
                }

                if(isPostInList(result, postTag.getPost())) {
                    continue;
                }

                result.add(postTag.getPost());
            }
        }

        return postRepository.findAllByPublicPostTrueAndPostType(postType);
    }

    @Override
    public List<Post> findAllByUser(Long id, PostType postType) {
        return postRepository.findAllByUserIdAndPostType(id, postType);
    }

    @Override
    public Boolean deletePost(Long id) {

        Optional<Post> post = postRepository.findById(id);

        if(!post.isPresent()) {
            return false;
        }

        Post postToDelete = post.get();
        postToDelete.setDeleted(true);

        postRepository.save(postToDelete);
        return true;
    }

    @Override
    public List<Post> getWithReaction() {

        List<Reaction> reactions = reactionRepository.findAllByUserId(currentUserService.getCurrentUser().getId());

        List<Post> posts = new ArrayList<>();

        for(Reaction r: reactions) {
            posts.add(r.getPost());
        }

        return posts;
    }

    private boolean isPostInList(List<Post> posts, Post post) {

        for(Post p:posts) {
            if(p.getId().equals(post.getId())) {
                return true;
            }
        }

        return false;
    }
}
