package com.Projekat.TwitterAppClone.controller;

import com.Projekat.TwitterAppClone.dto.JwtAuthenticationRequest;
import com.Projekat.TwitterAppClone.dto.ResetPasswordDTO;
import com.Projekat.TwitterAppClone.dto.UserDTO;
import com.Projekat.TwitterAppClone.dto.UserTokenState;
import com.Projekat.TwitterAppClone.enums.Roles;
import com.Projekat.TwitterAppClone.model.User;
import com.Projekat.TwitterAppClone.security.TokenUtils;
import com.Projekat.TwitterAppClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    TokenUtils tokenUtils;

    Logger logger = Logger.getLogger(UserController.class.getName());

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    };

    @GetMapping("/{username}")
    public ResponseEntity<User> getOneByUsername(@PathVariable("username") String username){
        Optional<User> users = Optional.ofNullable(userService.findOne(username));
        if(!users.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users.get(), HttpStatus.OK);
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<User> getOneById(@PathVariable("id") int id){
        User user = userService.findOneById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/onlyUser")
    public ResponseEntity<List<User>> findAllUser(){

        List<User> allUsers = userService.findAll();
        List<User> user = new ArrayList<>();
        for (User user1 : allUsers){
            if (user1.getRoles() == Roles.USER){
                user.add(user1);
            }
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    };


    @GetMapping("/onlyGroupAdmins")
    public ResponseEntity<List<User>> findAllGroupAdmins(){

        List<User> allUsers = userService.findAll();
        List<User> user = new ArrayList<>();
        for (User user1 : allUsers){
            if (user1.getRoles() == Roles.GROUPADMIN){
                user.add(user1);
            }
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    };


    @PostMapping("/create")
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO newUser){

        User createdUser = userService.createUser(newUser);

        if(createdUser == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        UserDTO userDTO = new UserDTO(createdUser);

        logger.info("USER HAS REGISTERED");

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{username}", consumes = "application/json")
    public ResponseEntity<Void> changePassword(@RequestBody @Validated ResetPasswordDTO resetPasswordDTO, @PathVariable("username") String username){
        User user = userService.findOne(username);

        if (passwordEncoder.matches(resetPasswordDTO.getOldPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
            user = userService.save(user);

            logger.info("USER HAS CHANGE HIS PASSWORD");

            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

    }

//    @PutMapping(value = "/{username}", consumes = "application/json")
//    public ResponseEntity<Void> changePassword1(@RequestBody @Validated ResetPasswordDTO resetPasswordDTO, @PathVariable("username") String username){
//        User user = userService.findOne(username);
//
//        if (passwordEncoder.matches(resetPasswordDTO.getOldPassword(), user.getPassword())){
//            if (resetPasswordDTO.getNewPassword().equals(resetPasswordDTO.getRepeatPassword())) {
//                user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
//                user = userService.save(user);
//
//                logger.info("USER HAS CHANGE HIS PASSWORD");
//
//                return new ResponseEntity<>(HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//            }
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//        }
//    }


    @DeleteMapping(value = "/changeUserToGroupAdmin/{id}")
    public ResponseEntity<UserDTO> changeUserToGroupAdmin(@PathVariable Integer id) {

        User user = userService.findOneById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setRoles(Roles.GROUPADMIN);

        user = userService.save(user);

        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

    @DeleteMapping(value = "/changeGroupAdminToUser/{id}")
    public ResponseEntity<UserDTO> changeGroupAdminToUser(@PathVariable Integer id) {

        User user = userService.findOneById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setRoles(Roles.USER);

        user = userService.save(user);

        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }



    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user);
        int expiresIn = tokenUtils.getExpiredIn();

        logger.info("USER HAS LOGGED IN");

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @PutMapping(value = "/changeData/{username}", consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable("username") String username) {
        User user = userService.findOne(username);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        user = userService.save(user);

        logger.info("USER HAS CHANGE HIS DATA ");

        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }




}
