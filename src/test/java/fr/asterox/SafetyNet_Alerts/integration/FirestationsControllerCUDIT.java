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
import fr.asterox.SafetyNet_Alerts.model.Firestation;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class FirestationsControllerCUDIT {

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
	public void givenAFirestation_whenDeleteFirestation_thenReturnListWithoutTheFirestation()
			throws UnirestException, JSONException {
		// GIVEN
		Firestation firestation = new Firestation(1, new ArrayList<>());
		List<Firestation> firestationsList = new ArrayList<>();
		firestationsList.add(firestation);
		when(data.getFirestationsList()).thenReturn(firestationsList);

		String addressUrl = baseUrl + "/firestation?stationNumber=1";

		// WHEN
		Unirest.delete(addressUrl).asEmpty();

		// THEN
		verify(data, Mockito.times(2)).getFirestationsList();
		assertFalse(firestationsList.contains(firestation));
	}

	@Test
	public void givenAStreet_whenDeleteStreetOnFirestation_thenReturnListWithoutTheAddress()
			throws UnirestException, JSONException {
		// GIVEN
		Address address = new Address("1509 Culver St1", 123, "Culver");
		Firestation firestation = new Firestation(1, List.of(address));
		List<Firestation> firestationsList = new ArrayList<>();
		firestationsList.add(firestation);
		String addressUrl = baseUrl + "/firestation/street";

		// WHEN
		Unirest.delete(addressUrl).body("1509 Culver St1").asEmpty();

		// THEN
		verify(data, Mockito.times(1)).getFirestationsList();
		assertFalse(firestationsList.get(0).getAdressesList().isEmpty());
	}

//	@PostMapping(value = "/firestation")
//	public void addFirestation(@RequestBody Firestation firestation) {
//		LOGGER.info("Adding new firestation");
//		firestationsService.addFirestation(firestation);
//	}
//
//	@PutMapping(value = "/firestation")
//	public void updateStationNumber(@RequestParam String street, int newStationNumber) {
//		LOGGER.info("Updating firestation");
//		firestationsService.updateFirestation(street, newStationNumber);
//	}
}
