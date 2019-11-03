package com.pantrytracker.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pantrytracker.app.entities.IngredientDeleteStatistics;
import com.pantrytracker.app.entities.UserlessIngredient;
import com.pantrytracker.app.repositories.IngredientDeleteStatisticsRepository;
import com.pantrytracker.app.services.DeleteStatisticsRatioService;

@Controller
public class IngredientDeleteStatisticsController {
	
	@Autowired
	private IngredientDeleteStatisticsRepository ingredientDeleteStatisticsRepository;
	
	@Autowired
	private DeleteStatisticsRatioService deleteStatisticsRatioService;
	
	@PostMapping("/ingredientdeletestatistics")
	public String postIngredientDeleteStatistics(@ModelAttribute IngredientDeleteStatistics ingredientDeleteStatistics, BindingResult errors, Model model) {
		IngredientDeleteStatistics saved = ingredientDeleteStatisticsRepository.save(ingredientDeleteStatistics);
		if(saved != null) {
			model.addAttribute("deleteSuccessful", true);
			model.addAttribute("userEmail", new String());
		}
		model.addAttribute("userlessIngredient", new UserlessIngredient());
		model.addAttribute("ratioUsedIngredients", deleteStatisticsRatioService.getDeleteStatisticsRatio());
		return "index";
	}
}
