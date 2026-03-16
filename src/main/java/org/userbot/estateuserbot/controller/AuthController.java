package org.userbot.estateuserbot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.userbot.estateuserbot.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/code/{code}")
    public ResponseEntity<?> submitCode(@PathVariable String code){
        try{
            authService.submitCode(code);
        }catch (IllegalStateException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok().build();
    };

    @PostMapping("/password/{password}")
    public ResponseEntity<?> submitPassword(@PathVariable String password){
        try{
            authService.submitPassword(password);
        }catch (IllegalStateException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok().build();
    };
}
