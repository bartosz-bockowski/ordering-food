package com.example.orderingfood.command;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class MealCommand {

    @NotEmpty(message = "meal name cannot be empty")
    private String name;

    @DecimalMin(value = "0", message = "price cannot be negative")
    private BigDecimal price;

}
