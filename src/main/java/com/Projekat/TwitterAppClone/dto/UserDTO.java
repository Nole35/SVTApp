package com.Projekat.TwitterAppClone.dto;

import com.Projekat.TwitterAppClone.enums.Roles;
import com.Projekat.TwitterAppClone.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO implements Serializable {

    private int userId;

    @NotNull
    @Size(min = 6, max = 30)
    private String username;

    @NotNull
    @Min(8)
    private String password;

    @NotNull
    private String email;

    private LocalDate registrationDate;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private Roles roles;

    public UserDTO(User createdUser) {
        this.userId = createdUser.getUserId();
        this.username = createdUser.getUsername();
        this.password = createdUser.getPassword();
        this.email = createdUser.getEmail();
        this.firstName = createdUser.getFirstName();
        this.lastName = createdUser.getLastName();
        this.roles = createdUser.getRoles();
    }

}
