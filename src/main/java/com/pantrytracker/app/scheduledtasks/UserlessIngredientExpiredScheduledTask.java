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
public class UserlessIngredientExpiredScheduledTask {
	
	@Autowired
	private UserlessIngredientRepository userlessIngredientRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Scheduled(cron = "0 0 8 */2 * *")
	public void checkForExpiredIngredientsAndSendReminderEmails() {
		Iterator<UserlessIngredient> iterator = userlessIngredientRepository.findAll().iterator();
		LocalDate now = LocalDate.now();
		while(iterator.hasNext()) {
			UserlessIngredient next = iterator.next();
			if(now.until(next.getExpirationDate(), ChronoUnit.DAYS) < 0) {
				sendExpiredEmail(next);
			}
		}
	}
	
	private void sendExpiredEmail(UserlessIngredient userlessIngredient) {
		SimpleMailMessage msg = new SimpleMailMessage();
		String bodyUnformatted = "Hi %s,\n\nYour %s expired on %s. To delete this ingredient from your pantry, visit https://pantry-tracker-prototype.herokuapp.com/userlessingredient/delete?id=%d.\n\nThanks for using Pantry Tracker.\n\n--Pantry Tracker";
		
		msg.setTo(userlessIngredient.getEmail());
		msg.setSubject("Expired Ingredient");
		msg.setText(String.format(bodyUnformatted,
				userlessIngredient.getName(),
				userlessIngredient.getIngredientName(),
				userlessIngredient.getExpirationDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")).toString(),
				userlessIngredient.getId().longValue()));
		
		javaMailSender.send(msg);
	}

}
