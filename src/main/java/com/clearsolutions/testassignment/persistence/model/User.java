package com.clearsolutions.testassignment.persistence.model;

import com.clearsolutions.testassignment.web.validation.MinimumAge;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", nullable = false)
    @NotBlank(message = "{message.email.notblank}")
    @Email(message = "{message.email.format}")
    private String email;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "{message.firstname.notblank}")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "{message.lastname.notblank}")
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    @NotNull(message = "{message.birthdate.notnull}")
    @Past(message = "{message.birthdate.past}")
    @MinimumAge(message = "{message.minimum.age.years}")
    private LocalDate birthDate;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;
}

