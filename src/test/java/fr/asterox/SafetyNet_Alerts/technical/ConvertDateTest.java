package fr.asterox.SafetyNet_Alerts.technical;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class ConvertDateTest {

	@Test
	public void givenNullBirthdate_whenCalculateAge_thenReturnNull() throws IOException {
		// GIVEN
		String birthdate = null;

		// WHEN
		LocalDate result = ConvertDate.convertStringToLocalDate(birthdate);

		// THEN
		assertNull(result);
	}
}
