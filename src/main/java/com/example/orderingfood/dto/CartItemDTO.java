package com.example.orderingfood.dto;

import lombok.Data;

@Data
public class CartItemDTO {

    private MealDTO meal;

    private int quantity;

}
