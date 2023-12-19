package com.example.orderingfood.controller;

import com.example.orderingfood.command.AdminCommand;
import com.example.orderingfood.domain.Admin;
import com.example.orderingfood.dto.UserDTO;
import com.example.orderingfood.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<UserDTO> register(@Valid @RequestBody AdminCommand adminCommand) {
        return new ResponseEntity<>(modelMapper.map(adminService.register(modelMapper.map(adminCommand, Admin.class)), UserDTO.class), HttpStatus.OK);
    }
}
