package ch.wiss.wiss_quiz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class WissQuizApplication {

	 @Value("#{systemEnvironment['ENV_VAR'] ?: '$ENV_VAR has not been set'}")
	 private String envVar;
	
	public static void main(String[] args) {
		SpringApplication.run(ch.wiss.wiss_quiz.WissQuizApplication.class, args);
	}

	@GetMapping("/")
	public String index() {
		return "Spring Boot Wiss-Quiz REST API! " + envVar;
	}
}
