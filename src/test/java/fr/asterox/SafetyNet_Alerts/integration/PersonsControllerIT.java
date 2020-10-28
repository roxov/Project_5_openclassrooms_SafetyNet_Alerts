package fr.asterox.SafetyNet_Alerts.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
		assertTrue(jsonPersons.length() == 1);
	}

	@Test
	public void givenLastNameUsedByMultiplePersons_whenGetInhabitantsInfo_thenReturnInformationOnPersons()
			throws UnirestException, JSONException {
		// GIVEN
		String addressUrl = baseUrl + "/personInfo?firstName=Tony&lastName=Cooper";

		// WHEN
		JSONArray jsonPersons = Unirest.get(addressUrl).asJson().getBody().getArray();

		// THEN
		assertTrue(findInfoOnPerson(jsonPersons, "Cooper", "112 Steppes Pl", 97451, 26, "tcoop@ymail.com",
				List.of("hydrapermazol:300mg", "dodoxadin:30mg"), List.of("shellfish")));
		assertTrue(findInfoOnPerson(jsonPersons, "Cooper", "489 Manchester St", 97451, 26, "lily@email.com",
				new ArrayList<>(), new ArrayList<>()));
		assertTrue(jsonPersons.length() == 2);
	}

	private boolean findInfoOnPerson(JSONArray jsonPersons, String lastName, String street, int zip, int age,
			String email, List<String> medications, List<String> allergies) throws JSONException {
		for (int i = 0; i < jsonPersons.length(); i++) {
			JSONObject person = jsonPersons.getJSONObject(i);
			if (lastName.equals(person.getString("lastName"))
					&& street.equals(person.getJSONObject("address").getString("street"))
					&& zip == person.getJSONObject("address").getInt("zip") && age == person.getInt("age")
					&& email.equals(person.getString("email"))
					&& compareListString(person.getJSONObject("medicalRecords").getJSONArray("medications"),
							medications)
					&& compareListString(person.getJSONObject("medicalRecords").getJSONArray("allergies"), allergies)) {
				return true;
			}
		}
		return false;
	}

	private boolean compareListString(JSONArray jsonList, List<String> list) throws JSONException {
		Set<String> listElements = new HashSet<>();
		for (int i = 0; i < jsonList.length(); i++) {
			listElements.add(jsonList.getString(i));
		}
		if (jsonList.length() == list.size() && listElements.containsAll(list)) {
			return true;
		}
		return false;
	}

	@Test
	public void givenCulver_whenEmailList_thenReturnEmailsOfCommunity() throws UnirestException, JSONException {
		// GIVEN
		String addressUrl = baseUrl + "/communityEmail?city=Culver";

		// WHEN
		JSONArray jsonEmails = Unirest.get(addressUrl).asJson().getBody().getArray();

		// THEN
		assertTrue(findEmail(jsonEmails, "jaboyd@email.com"));
		assertTrue(findEmail(jsonEmails, "drk@email.com"));
		assertTrue(findEmail(jsonEmails, "gramps@email.com"));
		assertTrue(jsonEmails.length() == 23);
	}

	private boolean findEmail(JSONArray jsonEmails, String email) throws JSONException {
		for (int i = 0; i < jsonEmails.length(); i++) {
			if (email.equals(jsonEmails.getString(i))) {
				return true;
			}
		}
		return false;
	}

}
