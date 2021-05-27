package com.constock.api.resources;

import com.constock.api.security.TokenAuthenticationService;
import com.constock.api.models.User;
import com.constock.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserResources {
    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "/user/signup")
    @ResponseBody
    public String CreateUser(@RequestBody User user, HttpServletResponse response) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        TokenAuthenticationService.addAuthentication(response, user.getEmail());
        return "User created successfully";
    }

    @PutMapping(value = "/user")
    @ResponseBody
    public String UpdateUser(@RequestBody User user,
                                      @RequestHeader(value = "Authorization") String token,
                                      HttpServletResponse response) {

        String email = TokenAuthenticationService.getEmailByToken(token);
        User userInfo = userRepository.findByEmail(email);
        userInfo.setName(user.getName() != null ? user.getName() : userInfo.getName());
        userInfo.setEmail(user.getEmail() != null ? user.getEmail() : userInfo.getEmail());
        userInfo.setPassword(user.getPassword() != null ?
                new BCryptPasswordEncoder().encode(user.getPassword()) : userInfo.getPassword());
        userRepository.save(userInfo);
        TokenAuthenticationService.addAuthentication(response, userInfo.getEmail());
        return "User updated successfully";
    }

    @DeleteMapping(value = "/user")
    @ResponseBody
    public String deleteUser(@RequestHeader(value = "Authorization") String token) {
        String email = TokenAuthenticationService.getEmailByToken(token);
        long id = userRepository.findByEmail(email).getId();
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    @GetMapping(value = "/user")
    @ResponseBody
    public Map<String, Object> getUseiData(@RequestHeader(value = "Authorization") String token) {
        String email = TokenAuthenticationService.getEmailByToken(token);
        User user = userRepository.findByEmail(email);
        Map<String, Object> userInformation = new HashMap<>();
        userInformation.put("id", user.getId());
        userInformation.put("email", user.getEmail());
        userInformation.put("name", user.getName());
        userInformation.put("updatedAt", user.getUpdatedAt());
        return userInformation;
    }
}
