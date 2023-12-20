package com.example.orderingfood.command;

import com.example.orderingfood.dto.MealDTO;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class CartItemCommand {

    private MealDTO meal;

    @Min(value = 1, message = "quantity cannot be lower than 1")
    private int quantity;

}
