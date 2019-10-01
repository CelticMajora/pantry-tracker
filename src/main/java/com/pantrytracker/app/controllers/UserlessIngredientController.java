package com.pantrytracker.app.controllers;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pantrytracker.app.entities.UserlessIngredient;
import com.pantrytracker.app.repositories.UserlessIngredientRepository;

@RestController
public class UserlessIngredientController {

	@Autowired
	private UserlessIngredientRepository userlessIngredientRepository;
	
	@RequestMapping(value = "/userlessingredient", method = RequestMethod.GET)
	public @ResponseBody List<UserlessIngredient> getUserlessIngredients(@RequestParam String userEmail) {
		List<UserlessIngredient> toReturn = new LinkedList<UserlessIngredient>();
		Iterator<UserlessIngredient> iterator = userlessIngredientRepository.findAll().iterator();
		while(iterator.hasNext()) {
			UserlessIngredient next = iterator.next();
			if(next.getEmail().equals(userEmail)) {
				toReturn.add(next);
			}
		}
		return toReturn;
	}
	
	@RequestMapping(value = "/userlessingredient", method = RequestMethod.POST)
	public @ResponseBody UserlessIngredient postUserlessIngredient(@RequestParam String email,
			@RequestParam String name,
			@RequestParam String ingredientName,
			@RequestParam String expirationYear,
			@RequestParam String expirationMonth,
			@RequestParam String expirationDayOfMonth) {
		UserlessIngredient toStore = new UserlessIngredient();
		toStore.setEmail(email);
		toStore.setName(name);
		toStore.setIngredientName(ingredientName);
		toStore.setExpirationDate(LocalDate.of(Integer.parseInt(expirationYear),
				Integer.parseInt(expirationMonth),
				Integer.parseInt(expirationDayOfMonth)));
		userlessIngredientRepository.save(toStore);
		return toStore;
	}
	
	@RequestMapping(value = "/userlessingredient", method = RequestMethod.DELETE)
	public void deleteUserlessIngredient(@RequestParam String id) {
		userlessIngredientRepository.deleteById(Long.parseLong(id));
	}
}
