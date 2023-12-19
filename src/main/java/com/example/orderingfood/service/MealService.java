package com.example.orderingfood.service;

import com.example.orderingfood.domain.Meal;
import com.example.orderingfood.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;

    public List<Meal> saveAll(List<Meal> meals) {
        return mealRepository.saveAll(meals);
    }

}
