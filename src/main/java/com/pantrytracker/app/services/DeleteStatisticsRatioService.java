package com.pantrytracker.app.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pantrytracker.app.entities.IngredientDeleteStatistics;
import com.pantrytracker.app.repositories.IngredientDeleteStatisticsRepository;

@Service
public class DeleteStatisticsRatioService {
	
	@Autowired
	private IngredientDeleteStatisticsRepository ingredientDeleteStatisticsRepository;
	
	public String getDeleteStatisticsRatio() {
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
