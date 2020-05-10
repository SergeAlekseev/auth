package com.example.auth.controller;

import com.example.auth.repository.UserRepository;
import com.example.auth.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class HomeRestAPIs {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @GetMapping("/home")
    public ResponseEntity<?> getHome(@RequestHeader("Authorization") String autorization) {
        String token = getJwt(autorization);
        String username = jwtProvider.getUserNameFromAccessJwtToken(token);

        return ResponseEntity.ok().body(String.format(" %s, hello world!", username));
    }

    private String getJwt(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.replace("Bearer ","");
        }

        return null;
    }
}
