package com.example.orderingfood.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {

    private MealDTO meal;

    private int quantity;

}
