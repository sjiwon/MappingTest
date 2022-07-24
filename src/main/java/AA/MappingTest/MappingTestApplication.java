package AA.MappingTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class MappingTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MappingTestApplication.class, args);
	}

}
