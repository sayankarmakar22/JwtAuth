package com.sayan.AuthJwt.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class MainControllers {
    @GetMapping("/free")
    public ResponseEntity<String> availableToAll(){
        return new ResponseEntity<>("available to all", HttpStatus.OK);
    }
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin")
    public ResponseEntity<String> onlyToAdmin(){
        return new ResponseEntity<>("only to admin",HttpStatus.OK);
    }
    @Secured({"ROLE_HR"})
    @GetMapping("/hr")
    public ResponseEntity<String> onlyToHr(){
        return new ResponseEntity<>("only to hr",HttpStatus.OK);
    }
}
