package com.example.orderingfood.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Customer extends User {

    @OneToMany
    private List<CartItem> cart;

}
