package br.com.alura.screematch;

import br.com.alura.screematch.models.Serie;
import br.com.alura.screematch.service.ConsumoAPI;
import br.com.alura.screematch.service.DataConverter;
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

        ConsumoAPI api = new ConsumoAPI();

        System.out.println("Requesting data...");

        String data = api.GetData("https://www.omdbapi.com/?t=gilmore+girls&apiKey=6585022c");

        System.out.println("Returning data...");

        DataConverter converter = new DataConverter();

        var dataSerie = converter.GetData(data, Serie.class);

        System.out.println(dataSerie);
    }
}
