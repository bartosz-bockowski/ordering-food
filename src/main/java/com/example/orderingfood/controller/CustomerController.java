package com.example.orderingfood.controller;

import com.example.orderingfood.command.CustomerCommand;
import com.example.orderingfood.domain.Customer;
import com.example.orderingfood.dto.CartItemDTO;
import com.example.orderingfood.dto.CustomerDTO;
import com.example.orderingfood.dto.UserDTO;
import com.example.orderingfood.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<UserDTO> register(@Valid @RequestBody CustomerCommand customerCommand) {
        return new ResponseEntity<>(modelMapper
                .map(customerService.register(
                        modelMapper.map(customerCommand, Customer.class)), CustomerDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/cart")
    public ResponseEntity<List<CartItemDTO>> cart() {
        return new ResponseEntity<>(customerService.getCartOfLoggedCustomer().stream()
                .map(cartItem -> modelMapper.map(cartItem, CartItemDTO.class))
                .toList(), HttpStatus.OK);
    }
}
