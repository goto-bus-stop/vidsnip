package space.vidsnip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.neo4j.NodeEntityScan;

@SpringBootApplication
public class VidsnipApplication {
	public static void main(String[] args) {
		SpringApplication.run(VidsnipApplication.class, args);
	}


}

