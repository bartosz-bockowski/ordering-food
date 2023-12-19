package com.example.orderingfood.service;

import com.example.orderingfood.domain.CartItem;
import com.example.orderingfood.domain.Customer;
import com.example.orderingfood.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final UserService userService;

    public Customer register(Customer customer) {
        customer.setPassword(BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt()));
        return customerRepository.save(customer);
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer update(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<CartItem> getCartOfLoggedCustomer() {
        return ((Customer) userService.getLoggedUser()).getCart();
    }

}
