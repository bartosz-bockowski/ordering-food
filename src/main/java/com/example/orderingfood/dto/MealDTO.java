package com.example.orderingfood.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class MealDTO {

    private Long id;

    private String name;

    private BigDecimal price;

}
