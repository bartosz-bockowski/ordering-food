package com.example.orderingfood.command;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class RestaurantCommand {

    private String name;

    private List<MealCommand> meals;
    
}
