package com.main.app.service.album;

import com.main.app.converter.album.AlbumConverter;
import com.main.app.domain.dto.album.AlbumDTO;
import com.main.app.domain.model.album.Album;
import com.main.app.domain.model.post.Post;
import com.main.app.domain.model.user.User;
import com.main.app.repository.album.AlbumRepository;
import com.main.app.service.user.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public Album add(AlbumDTO albumDTO) {
        Optional<User> user = currentUserService.getCurrentUser();

        if(!user.isPresent()) {
            return null;
        }

        Album album = AlbumConverter.albumDTOtoAlbumEntity(albumDTO);
        album.setUser(user.get());

        return albumRepository.save(album);
    }

    @Override
    public Boolean deletePost(Long id) {
        Optional<Album> post = albumRepository.findById(id);

        if(!post.isPresent()) {
            return false;
        }

        Album albumToDelete = post.get();
        albumToDelete.setDeleted(true);

        albumRepository.save(albumToDelete);
        return true;
    }
}
