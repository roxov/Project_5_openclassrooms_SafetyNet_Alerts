package fr.asterox.SafetyNet_Alerts.integration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class FirestationsControllerIT {

	@LocalServerPort
	private Integer port;

	private String baseUrl;

	@BeforeEach
	public void setUp() {
		baseUrl = "http://localhost:" + port;
	}

	@Test
	public void givenAStationNumber_whenGetInfoForPersonsServedByStation_thenReturnInhabitantsListAndCountOfAdultsAndChildren()
			throws JSONException, UnirestException {
		// GIVEN
		String addressUrl = baseUrl + "/firestation?stationNumber=1";

		// WHEN
		JSONObject jsonPeopleAndCount = Unirest.get(addressUrl).asJson().getBody().getObject();

		// THEN
		assertTrue(5 == jsonPeopleAndCount.getInt("numberOfAdults"));
		assertTrue(1 == jsonPeopleAndCount.getInt("numberOfChildren"));
		assertTrue(findPeopleServedByStation(jsonPeopleAndCount.getJSONArray("peopleOfStation"), "Reginold", "Walker",
				"908 73rd St", "841-874-8547"));
		assertTrue(findPeopleServedByStation(jsonPeopleAndCount.getJSONArray("peopleOfStation"), "Peter", "Duncan",
				"644 Gershwin Cir", "841-874-6512"));
	}

	private boolean findPeopleServedByStation(JSONArray jsonPeople, String firstName, String lastName, String street,
			String phone) throws JSONException {

		for (int i = 0; i < jsonPeople.length(); i++) {
			JSONObject person = jsonPeople.getJSONObject(i);
			if (firstName.equals(person.getString("firstName")) && lastName.equals(person.getString("lastName"))
					&& street.equals(person.getJSONObject("address").getString("street"))
					&& phone.equals(person.getString("phone"))) {
				return true;
			}
		}
		return false;
	}

	@Test
	public void givenAStationNumber_whenGetPhonesAssignedToStation_thenReturnListOfPhonesForTheStation()
			throws JSONException, UnirestException {
		// GIVEN
		String addressUrl = baseUrl + "/phoneAlert?firestation=1";

		// WHEN
		JSONArray jsonPhones = Unirest.get(addressUrl).asJson().getBody().getArray();
		System.out.println(jsonPhones);
		// THEN
		assertTrue(findPhone(jsonPhones, "841-874-6512"));
		assertTrue(findPhone(jsonPhones, "841-874-8547"));
		assertTrue(findPhone(jsonPhones, "841-874-7784"));
		assertTrue(jsonPhones.length() == 6);
	}

	private boolean findPhone(JSONArray jsonPhones, String phone) throws JSONException {
		for (int i = 0; i < jsonPhones.length(); i++) {
			if (phone.equals(jsonPhones.getString(i))) {
				return true;
			}
		}
		return false;
	}

	@Test
	public void givenTwoStationNumbers_whenHouseholdsServedByStations_thenReturnListOfHouseholds()
			throws JSONException, UnirestException {
		// GIVEN
		String addressUrl = baseUrl + "/flood/stations?stations=1,2";

		// WHEN
		JSONArray jsonHouseholds = Unirest.get(addressUrl).asJson().getBody().getArray();
		System.out.println(jsonHouseholds);
		// THEN
		assertTrue(findPeopleServedByStations(jsonHouseholds, "Marrack", "841-874-6513", 31, new ArrayList<>(),
				new ArrayList<>()));
		assertTrue(findPeopleServedByStations(jsonHouseholds, "Peters", "841-874-7462", 38, new ArrayList<>(),
				new ArrayList<>()));
		assertFalse(findPeopleServedByStations(jsonHouseholds, "Peters", "841-874-8888", 55, new ArrayList<>(),
				new ArrayList<>()));
	}

	private boolean findPeopleServedByStations(JSONArray jsonHouseholds, String lastName, String phone, int age,
			List<String> medications, List<String> allergies) throws JSONException {

		for (int i = 0; i < jsonHouseholds.length(); i++) {
			JSONObject household = jsonHouseholds.getJSONObject(i);
			JSONArray personsList = household.getJSONArray("personsListOfHousehold");
			for (int j = 0; j < personsList.length(); j++) {
				JSONObject person = personsList.getJSONObject(j);
				if (lastName.equals(person.getString("lastName")) && phone.equals(person.getString("phone"))
						&& age == person.getInt("age")
						&& compareListString(person.getJSONObject("medicalRecords").getJSONArray("medications"),
								medications)
						&& compareListString(person.getJSONObject("medicalRecords").getJSONArray("allergies"),
								allergies)) {
					return true;
				}
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

////
////	@PostMapping(value = "/firestation")
////	public void addFirestation(@RequestBody Firestation firestation) {
////		LOGGER.info("Adding new firestation");
////		firestationsService.addFirestation(firestation);
////	}
////
////	@PutMapping(value = "/firestation")
////	public void updateStationNumber(@RequestParam String street, int newStationNumber) {
////		LOGGER.info("Updating firestation");
////		firestationsService.updateFirestation(street, newStationNumber);
////	}
////
////	@DeleteMapping(value = "/firestation")
////	public void deleteFirestation(@RequestParam int stationNumber) {
////		LOGGER.info("Deleting firestation");
////		firestationsService.deleteFirestation(stationNumber);
////	}
////
////	@DeleteMapping(value = "/firestation/street")
////	public void deleteAddressFromFirestation(@RequestBody String street) {
////		LOGGER.info("Deleting firestation");
////		firestationsService.deleteAddressFromFirestation(street);
////	}
//	@Test
//	public void givenAStationNumber_whenGetInfoForPersonsServedByStation_thenReturnPeopleServedByStationAndCountOfAdultsAndChildren()
//			throws Exception {
//		mockMvc.perform(delete("/firestation").param("stationNumber", "1"));
//		verify(firestationsController).deleteFirestation(1);
//	}
}
