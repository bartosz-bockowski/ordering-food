package com.example.orderingfood.service;

import com.example.orderingfood.domain.Restaurant;
import com.example.orderingfood.exception.NotFoundException;
import com.example.orderingfood.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final MealService mealService;

    public Restaurant save(Restaurant restaurant) {
        Restaurant restaurantSaved = restaurantRepository.save(restaurant);
        mealService.saveAll(restaurant.getMeals().stream()
                .peek(meal -> meal.setRestaurant(restaurantSaved))
                .toList());
        return restaurantSaved;
    }

    public List<Restaurant> listAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant findById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant not found!"));
    }
}
