package fr.asterox.SafetyNet_Alerts.technical;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CalculateAgeTest {

	@Test
	public void givenBirthdateEarlierInTheYear_whenCalculateAge_thenAge() throws IOException {
		// GIVEN
		String birthdate = "01/01/1980";

		// WHEN
		int result = CalculateAge.calculateAge(birthdate);

		// THEN
		assertEquals(40, result);
	}

	@Test
	public void givenBirthdateLasterInTheYear_whenCalculateAge_thenAge() throws IOException {
		// GIVEN
		String birthdate = "12/31/1980";

		// WHEN
		int result = CalculateAge.calculateAge(birthdate);

		// THEN
		assertEquals(39, result);
	}
}
