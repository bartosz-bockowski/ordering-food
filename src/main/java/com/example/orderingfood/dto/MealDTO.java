package com.example.orderingfood.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MealDTO {

    private Long id;

    private String name;

    private BigDecimal price;

}
