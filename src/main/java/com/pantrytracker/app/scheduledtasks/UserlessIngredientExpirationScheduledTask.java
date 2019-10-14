package com.pantrytracker.app.scheduledtasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pantrytracker.app.entities.UserlessIngredient;
import com.pantrytracker.app.repositories.UserlessIngredientRepository;

@Component
public class UserlessIngredientExpirationScheduledTask {
	
	@Autowired
	private UserlessIngredientRepository userlessIngredientRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Scheduled(cron = "0 0 8 * * *")
	public void checkForExpiringIngredientsAndSendReminderEmails() {
		Iterator<UserlessIngredient> iterator = userlessIngredientRepository.findAll().iterator();
		LocalDate now = LocalDate.now();
		while(iterator.hasNext()) {
			UserlessIngredient next = iterator.next();
			if(now.until(next.getExpirationDate(), ChronoUnit.DAYS) <= 2) {
				sendEmail(next);
			}
		}
	}
	
	private void sendEmail(UserlessIngredient userlessIngredient) {
		SimpleMailMessage msg = new SimpleMailMessage();
		String bodyUnformatted = "Hi %s,\n\nYour %s is/are expiring on %s.\n\nThanks for using Pantry Tracker.\n\n--Pantry Tracker";
		
		msg.setTo(userlessIngredient.getEmail());
		msg.setFrom("noreply@pantry-tracker-prototype.herokuapp.com");
		msg.setSubject("Expiring Ingredient");
		msg.setText(String.format(bodyUnformatted,
				userlessIngredient.getName(),
				userlessIngredient.getIngredientName(),
				userlessIngredient.getExpirationDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")).toString()));
		
		javaMailSender.send(msg);
	}

}
