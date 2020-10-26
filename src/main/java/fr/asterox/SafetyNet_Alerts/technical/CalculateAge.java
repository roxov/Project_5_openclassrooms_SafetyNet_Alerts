package fr.asterox.SafetyNet_Alerts.technical;

import java.time.LocalDate;
import java.time.Period;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CalculateAge {
	private static final Logger LOGGER = LogManager.getLogger(ConvertDate.class);

	public static int calculateAge(String birthdate) {
		LocalDate date = ConvertDate.convertStringToLocalDate(birthdate);
		int age = Period.between(date, LocalDate.now()).getYears();
		LOGGER.debug("Calculating age from birthdate");
		return age;
	}
}
