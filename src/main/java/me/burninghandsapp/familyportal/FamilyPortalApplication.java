package me.burninghandsapp.familyportal;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class FamilyPortalApplication {



	public static void main(String[] args) {
		SpringApplication.run(FamilyPortalApplication.class, args);

	}







}
