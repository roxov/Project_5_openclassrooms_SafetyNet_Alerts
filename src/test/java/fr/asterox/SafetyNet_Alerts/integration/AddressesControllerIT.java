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
public class AddressesControllerIT {

	@LocalServerPort
	private Integer port;

	private String baseUrl;

	@BeforeEach
	public void setUp() {
		baseUrl = "http://localhost:" + port;
	}

	@Test
	public void givenAStreet_whenGetPersonsLivingInChildHousehold_thenReturnInhabitantsListOfChildHouse()
			throws UnirestException, JSONException {
		// GIVEN
		String addressUrl = baseUrl + "/childAlert?address=947%20E.%20Rose%20Dr";

		// WHEN
		JSONArray jsonPersons = Unirest.get(addressUrl).asJson().getBody().getArray();

		// THEN
		assertTrue(findChildDTO(jsonPersons, "Kendrik", "Stelzer", 6));
		assertTrue(findChildDTO(jsonPersons, "Brian", "Stelzer", 44));
	}

	private boolean findChildDTO(JSONArray jsonPersons, String firstName, String lastName, int age)
			throws JSONException {
		for (int i = 0; i < jsonPersons.length(); i++) {
			JSONObject person = jsonPersons.getJSONObject(i);
			if (firstName.equals(person.getString("firstName")) && lastName.equals(person.getString("lastName"))
					&& age == person.getInt("age")) {
				return true;
			}
		}
		return false;
	}

	@Test
	public void givenAStreet_whenGetInhabitantsAndStationOfTheAddress_thenReturnInhabitantsListAndStationCorresponding()
			throws JSONException, UnirestException {
		// GIVEN
		String addressUrl = baseUrl + "/fire?address=947%20E.%20Rose%20Dr";

		// WHEN
		JSONObject jsonPeopleAndStationNumber = Unirest.get(addressUrl).asJson().getBody().getObject();

		// THEN
		assertTrue(findStationNumber(jsonPeopleAndStationNumber, 1));

		assertTrue(findPeopleAndStationNumberOfAddressDTO(jsonPeopleAndStationNumber.getJSONArray("peopleOfAddress"),
				"Stelzer", "841-874-7784", 6, List.of("noxidian:100mg", "pharmacol:2500mg"), new ArrayList<>()));

		assertTrue(findPeopleAndStationNumberOfAddressDTO(jsonPeopleAndStationNumber.getJSONArray("peopleOfAddress"),
				"Stelzer", "841-874-7784", 44, List.of("ibupurin:200mg", "hydrapermazol:400mg"),
				List.of("nillacilan")));
	}

	private boolean findPeopleAndStationNumberOfAddressDTO(JSONArray jsonPeople, String lastName, String phone, int age,
			List<String> medications, List<String> allergies) throws JSONException {

		for (int i = 0; i < jsonPeople.length(); i++) {
			JSONObject person = jsonPeople.getJSONObject(i);
			if (lastName.equals(person.getString("lastName")) && phone.equals(person.getString("phone"))
					&& age == person.getInt("age")
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

	private boolean findStationNumber(JSONObject jsonPeopleAndStationNumber, int stationNumber) throws JSONException {
		if (stationNumber == jsonPeopleAndStationNumber.getInt("stationNumber")) {
			return true;
		}
		return false;
	}
}
