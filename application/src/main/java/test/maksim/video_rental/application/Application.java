package test.maksim.video_rental.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "test.maksim.video_rental")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
