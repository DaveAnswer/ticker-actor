package starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import starter.service.TrackerService;

@SpringBootApplication
public class Starter implements CommandLineRunner {

    @Autowired
    TrackerService trackerService;

    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        trackerService.getTicker();
    }
}
