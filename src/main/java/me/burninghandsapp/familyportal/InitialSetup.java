package me.burninghandsapp.familyportal;


import me.burninghandsapp.familyportal.models.User;
import me.burninghandsapp.familyportal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialSetup implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebSecurityConfig webSecurityConfig;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count()==0)
        {
            User initialUser  = new User("user","user","user");
            
            initialUser.setPassword(webSecurityConfig.passwordEncoder().encode("user"));

            initialUser.setRole("admin");
            initialUser.setEnabled(true);
            
            userRepository.save(initialUser);

        }
    }
}
