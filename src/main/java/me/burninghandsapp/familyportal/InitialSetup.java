package me.burninghandsapp.familyportal;


import me.burninghandsapp.familyportal.models.User;
import me.burninghandsapp.familyportal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialSetup implements CommandLineRunner {

    final
    UserRepository userRepository;

    final
    WebSecurityConfig webSecurityConfig;

    @Autowired
    public InitialSetup(UserRepository userRepository, WebSecurityConfig webSecurityConfig) {
        this.userRepository = userRepository;
        this.webSecurityConfig = webSecurityConfig;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count()==0)
        {
            var initialUser  = new User("user","user","user");
            
            initialUser.setPassword(webSecurityConfig.passwordEncoder().encode("user"));

            initialUser.setRole("admin");
            initialUser.setEnabled(true);
            
            userRepository.save(initialUser);

        }
    }
}
