package com.main.app.domain.dto.post;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDTO {

    private Long userFrom;
    private Long userTo;
    private String type;
    private String content;
}
