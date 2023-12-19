package com.example.orderingfood.controller;

import com.example.orderingfood.model.AuthenticationRequest;
import com.example.orderingfood.model.JwtTokenHolder;
import com.example.orderingfood.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("/api/v1/security")
@RestController
@RequiredArgsConstructor
public class SecurityController {

    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenHolder> login(@Valid @RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request) {
        return new ResponseEntity<>(
                new JwtTokenHolder(jwtService.generateTokenOnLogin(authenticationRequest, request)), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public HttpStatus logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return HttpStatus.OK;
    }
}
