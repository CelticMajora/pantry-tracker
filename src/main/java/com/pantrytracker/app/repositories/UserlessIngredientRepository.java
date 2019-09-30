package com.pantrytracker.app.repositories;

import org.springframework.data.repository.CrudRepository;

import com.pantrytracker.app.entities.UserlessIngredient;

public interface UserlessIngredientRepository extends CrudRepository<UserlessIngredient, Long> {

}
