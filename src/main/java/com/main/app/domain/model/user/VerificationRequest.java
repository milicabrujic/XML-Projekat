package com.main.app.domain.model.user;

import com.main.app.domain.model.AbstractEntity;
import com.main.app.enums.Gender;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class VerificationRequest extends AbstractEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "category")
    private String category;

    @Column(name = "document")
    private String document;

    @Enumerated(EnumType.STRING)
    private VerificationRequestStatus status;

    @ManyToOne
    private User user;
}
