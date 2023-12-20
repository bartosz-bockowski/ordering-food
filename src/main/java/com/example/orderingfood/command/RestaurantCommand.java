package com.example.orderingfood.command;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class RestaurantCommand {

    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotEmpty(message = "entered restaurant must have at least one meal")
    private List<MealCommand> meals;

}
