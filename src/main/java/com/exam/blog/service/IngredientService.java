package com.exam.blog.service;

import com.exam.blog.models.Ingredient;
import com.exam.blog.repository.IngredientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 @author Zhurenko Evgeniy
 */

@Service
public class IngredientService {

    private IngredientRepo ingredientRepo;

    @Autowired
    public void setIngredientRepo(IngredientRepo ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    public Ingredient getById(Long id){
        return ingredientRepo.getIngredientById(id);
    }

    public void saveIngredient(Ingredient ingredient){
        ingredientRepo.save(ingredient);
    }

    public void deleteIngredient(Long id){
        ingredientRepo.deleteById(id);
    }

    public boolean updateIngredient(Ingredient ingredient){
        Ingredient ingredientDB = ingredientRepo.getIngredientById(ingredient.getId());
        if(ingredientDB != null){
            ingredientRepo.saveAndFlush(ingredient);
            return true;
        } else {
            return false;
        }
    }
}
