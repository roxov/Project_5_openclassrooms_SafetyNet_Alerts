package fr.asterox.SafetyNet_Alerts.integration;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
public class PersonsControllerCUDIT {

	@LocalServerPort
	private Integer port;

	@MockBean
	private Data data;

	private String baseUrl;

	@BeforeEach
	public void setUp() {
		baseUrl = "http://localhost:" + port;
	}

//	@Test
//	public void givenNewPerson_whenAddPerson_thenReturnDataWithThePerson() throws UnirestException, JSONException {
//		// GIVEN
//		String addressUrl = baseUrl + "/person";
//
//		// WHEN
//		Person Marie = new Person("Marie", "Boyd", "03/06/2019", new Address("1509 Culver St", 97451, "Culver"),
//				"841-874-6512", "jaboyd@email.com", new MedicalRecords(new ArrayList<>(), new ArrayList<>()));
//		Unirest.post(addressUrl)
//				.body(new Person("Marie", "Boyd", "03/06/2019", new Address("1509 Culver St", 97451, "Culver"),
//						"841-874-6512", "jaboyd@email.com", new MedicalRecords(new ArrayList<>(), new ArrayList<>())))
//				.asEmpty();
//
//		// {"firstName":"Marie","lastName":"Boyd","birthdate":"03/06/2019","address":{"street":"1509
//		// Culver St",
//		// "zip":97451,"city":"Culver"},"phone":"841-874-6512","email":"jaboyd@email.com",
//		// "medicalRecords":{"medications":[],"allergies":[]}}
//
//		// THEN
//		List<Person> personsList = data.getPersonsList();
//
//		assertTrue(personsList.contains(Marie));
//	}

	@Test
	public void givenAPerson_whenDeletePerson_thenReturnListWithoutThePerson() throws UnirestException, JSONException {
		// GIVEN
		String addressUrl = baseUrl + "/person?firstName=John&lastName=Boyd";
		List<Person> personsList = new ArrayList<>();
		Person John = new Person("John", "Boyd", "03/06/1984", new Address("1509 Culver St", 97451, "Culver"),
				"841-874-6512", "jaboyd@email.com",
				new MedicalRecords(List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan")));
		personsList.add(John);
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		Unirest.delete(addressUrl).asEmpty();

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		assertFalse(personsList.contains(John));
	}

//	@PostMapping(value = "/person")
//	public void addPerson(@RequestBody Person person) {
//		LOGGER.info("Adding new Person");
//		personsService.addPerson(person);
//	}
//
//	@PutMapping(value = "/person")
//	public void updatePerson(@RequestBody Person person) {
//		LOGGER.info("Updating Person");
//		personsService.updatePerson(person);
//	}
}
