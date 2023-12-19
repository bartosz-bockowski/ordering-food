package com.example.orderingfood.command;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class MealCommand {

    private String name;

    private BigDecimal price;

}
