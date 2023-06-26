package com.Projekat.TwitterAppClone.service.implementation;

import com.Projekat.TwitterAppClone.dto.UserDTO;
import com.Projekat.TwitterAppClone.enums.Roles;
import com.Projekat.TwitterAppClone.model.User;
import com.Projekat.TwitterAppClone.repository.UserRepository;
import com.Projekat.TwitterAppClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User findOne(String username) {
        Optional<User> user = userRepository.findFirstByUsername(username);
        if (!user.isEmpty()) {
            return user.get();
        }
        return null;
    }

    @Override
    public User findOneById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }

        return null;
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {

        try{
            return userRepository.save(user);
        }catch (IllegalArgumentException e){
            return null;
        }
    }

    @Override
    public User createUser(UserDTO userDTO) {
        Optional<User> user = userRepository.findFirstByUsername(userDTO.getUsername());

        if(user.isPresent()){
            return null;
        }

        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setEmail(userDTO.getEmail());
        newUser.setRegistrationDate(LocalDate.now());
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setRoles(Roles.USER);
        newUser.setActive("true");
        newUser = userRepository.save(newUser);

        return newUser;
    }

    @Override
    public void delete(int id) {

    }
}
