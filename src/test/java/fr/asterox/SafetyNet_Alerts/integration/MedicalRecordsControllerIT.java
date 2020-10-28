package fr.asterox.SafetyNet_Alerts.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
import fr.asterox.SafetyNet_Alerts.model.Person;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class MedicalRecordsControllerIT {

	@LocalServerPort
	private Integer port;

	@MockBean
	private Data data;

	private String baseUrl;

	@BeforeEach
	public void setUp() {
		baseUrl = "http://localhost:" + port;
	}

	@Test
	public void givenAPerson_whenDeleteMedicalRecords_thenReturnListWithoutMedicalRecordsForThisPerson()
			throws UnirestException, JSONException {
		// GIVEN
		List<Person> personsList = new ArrayList<>();
		Person John = new Person("John", "Boyd", "03/06/1984", new Address("1509 Culver St", 97451, "Culver"),
				"841-874-6512", "jaboyd@email.com",
				new MedicalRecords(List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan")));
		personsList.add(John);
		when(data.getPersonsList()).thenReturn(personsList);

		String addressUrl = baseUrl + "/medicalRecord?firstName=John&lastName=Boyd";

		// WHEN
		Unirest.delete(addressUrl).asEmpty();

		// THEN
		Person EmptyJohn = new Person("John", "Boyd", "03/06/1984", new Address("1509 Culver St", 97451, "Culver"),
				"841-874-6512", "jaboyd@email.com", new MedicalRecords(new ArrayList<>(), new ArrayList<>()));
		verify(data, Mockito.times(1)).getPersonsList();
		assertTrue(personsList.contains(EmptyJohn));
	}

//	@PostMapping(value = "/medicalRecord")
//	public void addMedicalRecords(@RequestBody String firstName, String lastName, MedicalRecords medicalRecords) {
//		LOGGER.info("Adding new Medical Records");
//		medicalRecordsService.addMedicalRecords(firstName, lastName, medicalRecords);
//	}
//
//	@PutMapping(value = "/medicalRecord")
//	public void updateMedicalRecords(@RequestBody String firstName, String lastName, MedicalRecords medicalRecords) {
//		LOGGER.info("Updating Medical Records");
//		medicalRecordsService.updateMedicalRecords(firstName, lastName, medicalRecords);
//	}

}
