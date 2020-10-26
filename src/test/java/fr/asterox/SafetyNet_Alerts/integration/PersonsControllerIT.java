package fr.asterox.SafetyNet_Alerts.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@SpringBootTest()
@ExtendWith(SpringExtension.class)
public class PersonsControllerIT {

	@LocalServerPort
	private Integer port;

	private String baseUrl;

	@BeforeEach
	public void setUp() {
		baseUrl = "http://localhost:" + port;
	}

	@Test
	public void givenFirstNameAndUniqueLastName_whenGetInhabitantsInfo_thenReturnInformationOnPerson()
			throws UnirestException, JSONException {
		// GIVEN
		String addressUrl = baseUrl + "/personInfo?firstName=Jonanathan&lastName=Marrack";

		// WHEN
		JSONArray jsonPersons = Unirest.get(addressUrl).asJson().getBody().getArray();
		System.out.println(jsonPersons);

		// THEN
		assertTrue(findInfoOnPerson(jsonPersons, "Marrack", "29 15th St", 97451, 31, "drk@email.com", new ArrayList<>(),
				new ArrayList<>()));
	}

	// (String lastName, Address address, int age, String email, MedicalRecords
	// medicalRecords) {
	private boolean findInfoOnPerson(JSONArray jsonPersons, String lastName, String street, int zip, int age,
			String email, List<String> medications, List<String> allergies) throws JSONException {
		for (int i = 0; i < jsonPersons.length(); i++) {
			JSONObject person = jsonPersons.getJSONObject(i);
			if (lastName.equals(person.getString("lastName")) && street.equals(person.getString("address"))
					&& age == person.getInt("age") && email.equals(person.getString("email"))
//					&& compareListString(person.getJSONObject("medicalRecords").getJSONArray("medications"),
//							medications)
//					&& compareListString(person.getJSONObject("medicalRecords").getJSONArray("allergies"), allergies)
			) {
				return true;
			}
		}
		return false;
	}
//	@GetMapping(value = "/personInfo")
//	public List<PersonInfoDTO> getInhabitantsInfo(@RequestParam String firstName, String lastName) {
//		LOGGER.info("Getting Info on People With The Given Last Name for personInfo Request");
//		return personsService.getInhabitantsInfo(firstName, lastName);
//	}
//
//	@GetMapping(value = "/communityEmail")
//	public List<String> getEmailList(@RequestParam String city) {
//		LOGGER.info("Getting Emails List of the city for communityEmail Request");
//		return personsService.getEmailsListOfCity(city);
//	}
//
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
//
//	@DeleteMapping(value = "/person")
//	public void deletePerson(@RequestParam String firstName, String lastName) {
//		LOGGER.info("Deleting Person");
//		personsService.deletePerson(firstName, lastName);
//	}

//
//	@Test
//	public void givenFirstNameAndLastName_whenGetInhabitantsInfo_thenReturnNullMedicalRecords() {
//		// WHEN
//		List<PersonInfoDTO> result = personsController.getInhabitantsInfo("John", "Boyd");
//
//		// THEN
//		assertEquals("Boyd", result.get(0).getLastName());
//		assertEquals("drk@email.com", result.get(1).getEmail());
//		assertEquals("1509 Culver St", result.get(2).getAddress().getStreet());
//	}
//
//	@Test
//	public void givenACity_whenGetEmailList_thenReturnEmailsList() {
//		// WHEN
//		List<String> result = personsController.getEmailList("Culver");
//
//		// THEN
//		assertEquals("jaboyd@email.com", result.get(0));
//		assertEquals("drk@email.com", result.get(1));
//		assertEquals("tenz@email.com", result.get(2));
//	}

}
