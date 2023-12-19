package com.example.orderingfood.service;

import com.example.orderingfood.domain.CartItem;
import com.example.orderingfood.domain.Customer;
import com.example.orderingfood.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;

    private final UserService userService;

    private final CustomerService customerService;

    public List<CartItem> putItemInCustomersCart(CartItem cartItem) {
        Customer customer = (Customer) userService.getLoggedUser();
        customer
                .getCart()
                .add(cartItemRepository.save(cartItem));
        return customerService.update(customer).getCart();
    }

    public void clearCart() {
        Customer customer = (Customer) userService.getLoggedUser();
        List<Long> cartItemsIds = customer.getCart().stream()
                .map(CartItem::getId)
                .toList();
        customer.setCart(new ArrayList<>());
        customerService.save(customer);
        cartItemRepository.deleteAllById(cartItemsIds);
    }

}
