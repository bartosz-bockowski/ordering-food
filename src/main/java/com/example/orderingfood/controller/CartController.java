package com.example.orderingfood.controller;

import com.example.orderingfood.command.CartItemCommand;
import com.example.orderingfood.domain.CartItem;
import com.example.orderingfood.dto.CartItemDTO;
import com.example.orderingfood.service.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final ModelMapper modelMapper;

    @PutMapping("/putItem")
    public ResponseEntity<List<CartItemDTO>> putItem(@Valid @RequestBody CartItemCommand cartItemCommand) {
        return new ResponseEntity<>(cartService.putItemInCustomersCart(modelMapper
                        .map(cartItemCommand, CartItem.class)).stream()
                .map(cartItem ->
                        modelMapper.map(cartItem, CartItemDTO.class)).toList(), HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public HttpStatus clear() {
        cartService.clearCart();
        return HttpStatus.OK;
    }
}
