package com.main.app.controller.album;

import com.main.app.converter.album.AlbumConverter;
import com.main.app.domain.dto.album.AlbumDTO;
import com.main.app.service.album.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping()
    public ResponseEntity<AlbumDTO> addAlbum(@RequestBody AlbumDTO albumDTO) {
        return new ResponseEntity<>(AlbumConverter.albumEntityToAlbumDTO(albumService.add(albumDTO)), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
