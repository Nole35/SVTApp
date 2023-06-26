package com.Projekat.TwitterAppClone.security;

import com.Projekat.TwitterAppClone.model.User;
import com.Projekat.TwitterAppClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebSecurity {

    @Autowired
    private UserService userService;

    public boolean checkId(Authentication authentication, HttpServletRequest request, int id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findOne(userDetails.getUsername());
        if(id == user.getUserId()) {
            return true;
        }
        return false;
    }



}
