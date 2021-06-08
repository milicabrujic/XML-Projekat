package com.main.app.service.album;

import com.main.app.domain.dto.album.AlbumDTO;
import com.main.app.domain.model.album.Album;

public interface AlbumService {

    Album add(AlbumDTO albumDTO);
    Boolean deletePost(Long id);
}
