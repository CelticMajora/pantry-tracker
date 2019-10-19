package com.pantrytracker.app.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pantrytracker.app.entities.IngredientDeleteStatistics;
import com.pantrytracker.app.entities.UserlessIngredient;
import com.pantrytracker.app.repositories.IngredientDeleteStatisticsRepository;

@Controller
public class IngredientDeleteStatisticsController {
	
	@Autowired
	private IngredientDeleteStatisticsRepository ingredientDeleteStatisticsRepository;
	
	@PostMapping("/ingredientdeletestatistics")
	public String postIngredientDeleteStatistics(@ModelAttribute IngredientDeleteStatistics ingredientDeleteStatistics, BindingResult errors, Model model) {
		IngredientDeleteStatistics saved = ingredientDeleteStatisticsRepository.save(ingredientDeleteStatistics);
		if(saved != null) {
			model.addAttribute("deleteSuccessful", true);
			model.addAttribute("userEmail", new String());
		}
		model.addAttribute("userlessIngredient", new UserlessIngredient());
		model.addAttribute("ratioUsedIngredients", getDeleteStatisticsRatio());
		return "index";
	}
	
	private String getDeleteStatisticsRatio() {
		long totalDeletedIngredients = this.ingredientDeleteStatisticsRepository.count();
		List<IngredientDeleteStatistics> usedUpIngredientDeleteStatistics = new LinkedList<IngredientDeleteStatistics>();
		this.ingredientDeleteStatisticsRepository.findAll().forEach((IngredientDeleteStatistics ingredientDeleteStatistics) -> {
			if(!ingredientDeleteStatistics.getWasTossed()) {
				usedUpIngredientDeleteStatistics.add(ingredientDeleteStatistics);
			}
		});
		long totalUsedUpIngredients = usedUpIngredientDeleteStatistics.size();
		return String.format("%d/%d", totalUsedUpIngredients, totalDeletedIngredients);
	}

}
