package com.main.app.domain.dto.album;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumDTO {

    private Long id;
    private String name;
}