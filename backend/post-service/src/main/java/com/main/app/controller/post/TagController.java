package com.main.app.controller.post;

import com.main.app.converter.post.LocationConverter;
import com.main.app.converter.post.TagConverter;
import com.main.app.domain.dto.post.LocationDTO;
import com.main.app.domain.dto.post.TagDTO;
import com.main.app.service.post.LocationService;
import com.main.app.service.post.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TagController {

    private final TagService tagService;

    @PostMapping(path = "/{postId}")
    public ResponseEntity<TagDTO> add(@PathVariable Long postId, @RequestBody TagDTO tagDTO) {
        return new ResponseEntity<>(TagConverter.tagEntityToTagDTO(tagService.addTag(tagDTO.getName(), postId)), HttpStatus.OK);
    }
}
