package com.pantrytracker.app.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pantrytracker.app.entities.UserlessIngredient;

@Controller
public class WebController {
	@Value("${spring.application.name}")
	String appName;
	
	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("appName", appName);
		model.addAttribute("userlessIngredient", new UserlessIngredient());
		return "home";
	}
}
