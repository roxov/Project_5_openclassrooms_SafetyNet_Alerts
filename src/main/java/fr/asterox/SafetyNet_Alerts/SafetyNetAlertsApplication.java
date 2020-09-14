package fr.asterox.SafetyNet_Alerts;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.asterox.SafetyNet_Alerts.technical.dataCreation.Data;

@SpringBootApplication
public class SafetyNetAlertsApplication {
// Instance du logger avec en paramètre le nom donné à l'instance.
	private static final Logger logger = LogManager.getLogger(SafetyNetAlertsApplication.class);

	public static void main(String[] args) throws IOException {
		logger.info("Initializing SafetyNet_Alerts");
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

	@Bean
	public Data getData() {
		return new Data();
	}

	@Bean
	public HttpTraceRepository htttpTraceRepository() {
		return new InMemoryHttpTraceRepository();
	}
}
