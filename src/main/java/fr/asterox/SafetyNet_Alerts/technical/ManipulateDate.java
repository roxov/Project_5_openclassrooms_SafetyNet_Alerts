package fr.asterox.SafetyNet_Alerts.technical;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

@Service
public class ManipulateDate {
	public static LocalDate convertStringToLocalDate(String date) {
		if (date == null) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate.minusYears(18);
	}
}
