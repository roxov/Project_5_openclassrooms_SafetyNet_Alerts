package fr.asterox.SafetyNet_Alerts.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.asterox.SafetyNet_Alerts.service.FirestationsService;
import fr.asterox.SafetyNet_Alerts.web.FirestationsController;

@WebMvcTest(controllers = { FirestationsController.class, FirestationsService.class })
@ExtendWith(SpringExtension.class)
public class FirestationsControllerIT {

	@LocalServerPort
	private Integer port;

	private String baseUrl;

	@BeforeEach
	public void setUpWebDriver() {
		baseUrl = "http://localhost:" + port;
	}

//	@GetMapping(value = "/firestation")
//	public PeopleAndCountForStationDTO getInfoForPersonsServedByStation(@RequestParam int stationNumber) {
//		LOGGER.info("Getting People and Count of Children and Adults of the Station for Firestation Request");
//		return firestationsService.getInfoOnPersonsServedByStation(stationNumber);
//	}
//
//	@GetMapping(value = "/phoneAlert")
//	public List<String> getPhonesListAssignedToFirestation(@RequestParam int firestation) {
//		LOGGER.info("Getting Phones List Assigned To The Station for phoneAlert Request");
//		return firestationsService.getPhoneOfPersonsServedByStation(firestation);
//	}
//
//	@GetMapping(value = "/flood/stations")
//	public List<HouseholdDTO> getHouseholdsServedByStations(@RequestParam List<Integer> stations) {
//		LOGGER.info("Getting People in Households Served By Station for Flood Request");
//		return firestationsService.getHouseholdsServedByStations(stations);
//	}
//
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
//
//	@DeleteMapping(value = "/firestation")
//	public void deleteFirestation(@RequestParam int stationNumber) {
//		LOGGER.info("Deleting firestation");
//		firestationsService.deleteFirestation(stationNumber);
//	}
//
//	@DeleteMapping(value = "/firestation/street")
//	public void deleteAddressFromFirestation(@RequestBody String street) {
//		LOGGER.info("Deleting firestation");
//		firestationsService.deleteAddressFromFirestation(street);
//	}

}
