package space.vidsnip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import space.vidsnip.model.GraphUser;

@SpringBootApplication
public class VidsnipApplication {

    private final static Logger log = LoggerFactory.getLogger(VidsnipApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(VidsnipApplication.class, args);
	}

	@Bean
    CommandLineRunner demo(GraphUserRepository graphUserRepository){
        return args ->{
            GraphUser marijn = new GraphUser("Marijn");

            log.info("Before neo4j");
            graphUserRepository.save(marijn);

        };
    }
}
