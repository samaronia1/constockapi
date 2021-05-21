package com.constock.api.resources;

import com.constock.api.models.User;
import com.constock.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserResources {
    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "/user")
    String CreateUser(@RequestBody User user) {
        userRepository.save(user);
        return "User created successfully";
    }

    @PutMapping(value = "/user")
    String UpdateUser(@RequestBody User user) {
        userRepository.save(user);
        return "User updated successfully";
    }

    @DeleteMapping(value = "/user/{id}")
    public void deleteUser(@PathVariable(value = "id") long id) {
        userRepository.deleteById(id);
    }

    //TODO: Remove this method for final solution.
    @GetMapping(value = "/users")
    public List<User> ListUsers() {
        return userRepository.findAll();
    }
}
