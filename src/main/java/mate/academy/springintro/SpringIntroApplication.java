package mate.academy.springintro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringIntroApplication {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("adminMate"));
        SpringApplication.run(SpringIntroApplication.class, args);
    }
}
