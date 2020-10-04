package fr.asterox.SafetyNet_Alerts.web;

import java.util.List;

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

	@Autowired
	private FirestationsService firestationsService;

	@GetMapping(value = "/firestation")
	public PeopleAndCountForStationDTO getInfoForPersonsServedByStation(@RequestParam int stationNumber) {
		return firestationsService.getInfoOnPersonsServedByStation(stationNumber);
	}

	@GetMapping(value = "/phoneAlert")
	public List<String> getPhonesListAssignedToFirestation(@RequestParam int firestation) {
		return firestationsService.getPhoneOfPersonsServedByStation(firestation);
	}

	@GetMapping(value = "/flood/stations")
	public List<HouseholdDTO> getHouseholdsServedByStations(@RequestParam List<Integer> stations) {
		return firestationsService.getHouseholdsServedByStations(stations);
	}

	@PostMapping(value = "/firestation")
	public void addFirestation(@RequestBody Firestation firestation) {
		firestationsService.addFirestation(firestation);
	}

	@PutMapping(value = "/firestation")
	public void updateFirestation(@RequestBody Firestation firestation) {
		firestationsService.updateFirestation(firestation);
	}

	@DeleteMapping(value = "/firestation")
	public void deleteFirestation(@RequestBody Firestation firestation) {
		firestationsService.deleteFirestation(firestation);
	}

}
