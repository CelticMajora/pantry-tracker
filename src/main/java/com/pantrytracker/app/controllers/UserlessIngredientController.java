package com.pantrytracker.app.controllers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.pantrytracker.app.entities.UserlessIngredient;
import com.pantrytracker.app.repositories.UserlessIngredientRepository;

@Controller
public class UserlessIngredientController {

	@Autowired
	private UserlessIngredientRepository userlessIngredientRepository;
	
	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("userlessIngredient", new UserlessIngredient());
		model.addAttribute("userEmail", new String()); 
		return "index";
	}
	
	@GetMapping("/userlessingredient")
	public String getUserlessIngredients(@RequestParam String userEmail, Model model) {
		List<UserlessIngredient> toDisplay = new LinkedList<UserlessIngredient>();
		Iterator<UserlessIngredient> iterator = userlessIngredientRepository.findAll().iterator();
		while(iterator.hasNext()) {
			UserlessIngredient next = iterator.next();
			if(next.getEmail().equals(userEmail)) {
				toDisplay.add(next);
			}
		}
		model.addAttribute("userlessIngredient", new UserlessIngredient());
		model.addAttribute("userEmail", userEmail);
		model.addAttribute("ingredientList", toDisplay);
		return "index";
	}
	
	@PostMapping("/userlessingredient")
	public String postUserlessIngredient(@ModelAttribute UserlessIngredient userlessIngredient, BindingResult errors, Model model) {
		UserlessIngredient ingredient = userlessIngredientRepository.save(userlessIngredient);
		if(ingredient != null) {
			model.addAttribute("saveSuccessful", true);
			model.addAttribute("userlessIngredient", new UserlessIngredient());
		}		
		return "index";
	}
	
	@PostMapping("/userlessingredient/delete")
	public String deleteUserlessIngredient(@RequestParam Long id, Model model) {
		userlessIngredientRepository.deleteById(id);
		model.addAttribute("userlessIngredient", new UserlessIngredient());
		model.addAttribute("userEmail", new String());
		return "index";
	}
}
