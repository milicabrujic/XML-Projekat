package com.main.app.service.post;

import com.main.app.domain.model.post.Location;
import com.main.app.domain.model.post.Post;
import com.main.app.domain.model.post.PostLocation;
import com.main.app.repository.post.LocationRepository;
import com.main.app.repository.post.PostLocationRepository;
import com.main.app.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLocationRepository postLocationRepository;

    @Override
    public Location addLocation(String name, Long postId) {

        Optional<Location> dbLocation = locationRepository.findOneByName(name);
        Location location = null;
        if (!dbLocation.isPresent()) {
            location = new Location();
            location.setName(name);
            location = locationRepository.save(location);
        }
        else {
            location = dbLocation.get();
        }

        Optional<Post> post = postRepository.findById(postId);

        if(!post.isPresent()) {
            return null;
        }

        PostLocation postLocation = new PostLocation();
        postLocation.setLocation(location);
        postLocation.setPost(post.get());

        postLocationRepository.save(postLocation);

        return location;
    }
}
