package fr.asterox.SafetyNet_Alerts;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.technical.dataCreation.GenerateData;

@SpringBootApplication
public class SafetyNetAlertsApplication {
// Instance du logger avec en paramètre le nom donné à l'instance.
	private static final Logger LOGGER = LogManager.getLogger(SafetyNetAlertsApplication.class);

	public static void main(String[] args) throws IOException {
		LOGGER.info("Initializing SafetyNet_Alerts");
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

	@Bean
	public Data getData() throws IOException {
		return new GenerateData().generateData();
	}

	@Bean
	public HttpTraceRepository htttpTraceRepository() {
		return new InMemoryHttpTraceRepository();
	}
}
