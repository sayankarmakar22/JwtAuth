package com.sayan.AuthJwt.Controllers;

import com.sayan.AuthJwt.DTO.AuthReq;
import com.sayan.AuthJwt.DTO.CreateReq;
import com.sayan.AuthJwt.Model.AppUsers;
import com.sayan.AuthJwt.Security.JwtProvider;
import com.sayan.AuthJwt.Services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app-auth")
public class AuthController {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody AuthReq authReq){
        String username = authReq.getUsername();
        String password = authReq.getPassword();
        String token = null;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(passwordEncoder.matches(password,userDetails.getPassword())) {
            token = jwtProvider.generateToken(username);
        }
        else {
            throw new RuntimeException("user details invalid");
        }
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @PostMapping("/createUser")
    public ResponseEntity<AppUsers> createUser(@RequestBody CreateReq createReq){
        return new ResponseEntity<>(userDetailsService.createUser(createReq), HttpStatus.CREATED);
    }
}
