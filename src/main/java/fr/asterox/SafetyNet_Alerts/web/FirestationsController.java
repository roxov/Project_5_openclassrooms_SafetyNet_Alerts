package fr.asterox.SafetyNet_Alerts.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.asterox.SafetyNet_Alerts.model.Firestation;
import fr.asterox.SafetyNet_Alerts.service.FirestationsService;
import fr.asterox.SafetyNet_Alerts.web.DTO.HouseholdDTO;
import fr.asterox.SafetyNet_Alerts.web.DTO.PeopleAndCountForStationDTO;

/**
 * 
 * Process the requests concerning firestations.
 *
 */

@RestController
public class FirestationsController {
	private static final Logger LOGGER = LogManager.getLogger(FirestationsController.class);

	@Autowired
	private FirestationsService firestationsService;

	@GetMapping(value = "/firestation")
	public PeopleAndCountForStationDTO getInfoForPersonsServedByStation(@RequestParam int stationNumber) {
		LOGGER.info("Getting People and Count of Children and Adults of the Station for Firestation Request");
		return firestationsService.getInfoOnPersonsServedByStation(stationNumber);
	}

	@GetMapping(value = "/phoneAlert")
	public List<String> getPhonesListAssignedToFirestation(@RequestParam int firestation) {
		LOGGER.info("Getting Phones List Assigned To The Station for phoneAlert Request");
		return firestationsService.getPhoneOfPersonsServedByStation(firestation);
	}

	@GetMapping(value = "/flood/stations")
	public List<HouseholdDTO> getHouseholdsServedByStations(@RequestParam List<Integer> stations) {
		LOGGER.info("Getting People in Households Served By Station for Flood Request");
		return firestationsService.getHouseholdsServedByStations(stations);
	}

	@PostMapping(value = "/firestation")
	public void addFirestation(@RequestBody Firestation firestation) {
		LOGGER.info("Adding new firestation");
		firestationsService.addFirestation(firestation);
	}

	@PutMapping(value = "/firestation")
	public void updateStationNumber(@RequestParam String street, int newStationNumber) {
		LOGGER.info("Updating firestation");
		firestationsService.updateFirestation(street, newStationNumber);
	}

	@DeleteMapping(value = "/firestation")
	public void deleteFirestation(@RequestParam int stationNumber) {
		LOGGER.info("Deleting firestation");
		firestationsService.deleteFirestation(stationNumber);
	}

}
