package fr.asterox.SafetyNet_Alerts.technical;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ManipulateDate {
	public static LocalDate convertStringToLocalDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate.minusYears(18);
	}
}
