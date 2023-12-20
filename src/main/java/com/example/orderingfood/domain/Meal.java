package com.example.orderingfood.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "name cannot be empty")
    private String name;

    @DecimalMin(value = "0", message = "price cannot be negative")
    private BigDecimal price;

    @ToString.Exclude
    @ManyToOne
    private Restaurant restaurant;

}
