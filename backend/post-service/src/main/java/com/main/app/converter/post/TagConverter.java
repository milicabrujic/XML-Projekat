package com.main.app.converter.post;

import com.main.app.domain.dto.album.AlbumDTO;
import com.main.app.domain.dto.post.TagDTO;
import com.main.app.domain.model.album.Album;
import com.main.app.domain.model.post.Tag;

import java.util.List;
import java.util.stream.Collectors;

public class TagConverter {

    public static Tag tagDTOtoTagEntity(TagDTO tagDTO){
        return Tag
                .builder()
                .name(tagDTO.getName())
                .build();
    }

    public static TagDTO tagEntityToTagDTO(Tag tag){
        return TagDTO
                .builder()
                .name(tag.getName())
                .build();
    }

    public static List<TagDTO> tagsListToTagsDTOList(List<Tag> tags) {
        return tags
                .stream()
                .map(tag -> tagEntityToTagDTO(tag))
                .collect(Collectors.toList());
    }
}
