package fr.asterox.SafetyNet_Alerts.technical;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ManipulateDate {
	private static final Logger LOGGER = LogManager.getLogger(ManipulateDate.class);

	public static LocalDate convertStringToLocalDate(String date) {
		if (date == null) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate localDate = LocalDate.parse(date, formatter);
		LOGGER.debug("Converting String date to LocalDateTime");
		return localDate;
	}
}
