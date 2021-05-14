package me.burninghandsapp.familyportal;

import me.burninghandsapp.familyportal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.web.servlet.oauth2.client.OAuth2ClientSecurityMarker;

@SpringBootApplication
public class FamilyportalApplication {


	public FamilyportalApplication() {
	}

	public static void main(String[] args) {
		SpringApplication.run(FamilyportalApplication.class, args);

	}



}
