package br.com.alura.screematch;

import br.com.alura.screematch.main.UserInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreematchApplication implements CommandLineRunner {

	public static void main(String[] args)
    {
		SpringApplication.run(ScreematchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting application...");

        // Create API
        UserInterface userInterface = new UserInterface();
        userInterface.showMenu();
    }
}
