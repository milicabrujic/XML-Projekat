package com.main.app.converter.album;

import com.main.app.domain.dto.album.AlbumDTO;
import com.main.app.domain.model.album.Album;

import java.util.List;
import java.util.stream.Collectors;

public class AlbumConverter {

    public static Album albumDTOtoAlbumEntity(AlbumDTO postDTO){
        return Album
                .builder()
                .name(postDTO.getName())
                .build();
    }

    public static AlbumDTO albumEntityToAlbumDTO(Album album){
        return AlbumDTO
                .builder()
                .name(album.getName())
                .build();
    }

    public static List<AlbumDTO> albumsListToAlbumsDTOList(List<Album> albums) {
        return albums
                .stream()
                .map(album -> albumEntityToAlbumDTO(album))
                .collect(Collectors.toList());
    }
}