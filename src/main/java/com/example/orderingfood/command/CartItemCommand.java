package com.example.orderingfood.command;

import com.example.orderingfood.dto.MealDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartItemCommand {

    private MealDTO meal;

    private int quantity;

}
