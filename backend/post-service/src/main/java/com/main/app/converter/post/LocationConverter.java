package com.main.app.converter.post;

import com.main.app.domain.dto.album.AlbumDTO;
import com.main.app.domain.dto.post.LocationDTO;
import com.main.app.domain.model.album.Album;
import com.main.app.domain.model.post.Location;

import java.util.List;
import java.util.stream.Collectors;

public class LocationConverter {

    public static Location locationDTOtoLocationEntity(LocationDTO locationDTO){
        return Location
                .builder()
                .name(locationDTO.getName())
                .build();
    }

    public static LocationDTO locationEntityToLocationDTO(Location location){
        return LocationDTO
                .builder()
                .name(location.getName())
                .build();
    }

    public static List<LocationDTO> locationsListToLocationsDTOList(List<Location> locations) {
        return locations
                .stream()
                .map(album -> locationEntityToLocationDTO(album))
                .collect(Collectors.toList());
    }
}
