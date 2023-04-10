package com.example.birthdaygreeter.infra.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "friend")
public class FriendEntity {

    @Column(name = "first_name", nullable = false)
    protected String firstName;
    @Column(name = "last_name", nullable = false)
    protected String lastName;
    @Column(name = "date_of_birth", nullable = false)
    protected LocalDate dateOfBirth;
    @Id
    @Column(name = "email", nullable = false, unique = true)
    protected String email;

}