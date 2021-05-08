package com.exam.blog.repository;

import com.exam.blog.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 @author Zhurenko Evgeniy
 */

public interface IngredientRepo extends JpaRepository<Ingredient, Long> {

    Ingredient getIngredientById(Long id);
}
