package com.example.orderingfood.controller;

import com.example.orderingfood.command.RestaurantCommand;
import com.example.orderingfood.domain.Restaurant;
import com.example.orderingfood.dto.MealDTO;
import com.example.orderingfood.dto.RestaurantDTO;
import com.example.orderingfood.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<RestaurantDTO> save(@Valid @RequestBody RestaurantCommand restaurantCommand) {
        return new ResponseEntity<>(modelMapper
                .map(restaurantService.save(
                        modelMapper.map(restaurantCommand, Restaurant.class)), RestaurantDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RestaurantDTO>> all() {
        return new ResponseEntity<>(restaurantService.listAll().stream()
                .map(restaurant -> modelMapper.map(restaurant, RestaurantDTO.class))
                .toList(), HttpStatus.OK);
    }

    @GetMapping("/{restaurantId}/meals")
    public ResponseEntity<List<MealDTO>> getMealsOfRestaurant(@PathVariable Long restaurantId) {
        return new ResponseEntity<>(restaurantService.findById(restaurantId).getMeals().stream()
                .map(meal -> modelMapper.map(meal, MealDTO.class))
                .toList(), HttpStatus.OK);
    }
}
