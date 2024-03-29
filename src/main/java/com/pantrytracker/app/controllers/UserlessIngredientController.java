package com.pantrytracker.app.controllers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pantrytracker.app.entities.IngredientDeleteStatistics;
import com.pantrytracker.app.entities.UserlessIngredient;
import com.pantrytracker.app.repositories.UserlessIngredientRepository;
import com.pantrytracker.app.services.DeleteStatisticsRatioService;

@Controller
public class UserlessIngredientController {

	@Autowired
	private UserlessIngredientRepository userlessIngredientRepository;
	
	@Autowired
	private DeleteStatisticsRatioService deleteStatisticsRatioService;
	
	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("userlessIngredient", new UserlessIngredient());
		model.addAttribute("userEmail", new String());
		model.addAttribute("ratioUsedIngredients", deleteStatisticsRatioService.getDeleteStatisticsRatio());
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
		model.addAttribute("ratioUsedIngredients", deleteStatisticsRatioService.getDeleteStatisticsRatio());
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
		model.addAttribute("userEmail", new String());
		model.addAttribute("ratioUsedIngredients", deleteStatisticsRatioService.getDeleteStatisticsRatio());
		return "index";
	}
	
	@PostMapping("/userlessingredient/delete")
	public String deleteUserlessIngredientForm(@RequestParam Long id, Model model) {
		return delete(id, model);
	}
	
	@GetMapping("/userlessingredient/delete")
	public String deleteUserlessIngredientLink(@RequestParam Long id, Model model) {
		return delete(id, model);
	}
	
	private String delete(Long id, Model model) {
		userlessIngredientRepository.deleteById(id);
		model.addAttribute("ingredientDeleteStatistics", new IngredientDeleteStatistics());
		model.addAttribute("ratioUsedIngredients", deleteStatisticsRatioService.getDeleteStatisticsRatio());
		return "ingredientdeletestatistics";
	}
}
