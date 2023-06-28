package com.Projekat.TwitterAppClone.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResetPasswordDTO {

    private String oldPassword;
    private String newPassword;
//    private String repeatPassword;
}
