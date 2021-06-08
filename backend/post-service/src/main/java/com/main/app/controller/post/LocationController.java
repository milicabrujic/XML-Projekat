package com.main.app.controller.post;

import com.main.app.converter.post.LocationConverter;
import com.main.app.domain.dto.post.LocationDTO;
import com.main.app.service.post.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LocationController {

    private final LocationService locationService;

    @PostMapping(path = "/{postId}")
    public ResponseEntity<LocationDTO> add(@PathVariable Long postId, @RequestBody LocationDTO locationDTO) {
        return new ResponseEntity<>(LocationConverter.locationEntityToLocationDTO(locationService.addLocation(locationDTO.getName(), postId)), HttpStatus.OK);
    }
}
