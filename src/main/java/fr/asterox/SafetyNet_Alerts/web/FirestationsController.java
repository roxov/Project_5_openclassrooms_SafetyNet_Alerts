package fr.asterox.SafetyNet_Alerts.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import fr.asterox.SafetyNet_Alerts.model.Firestation;
import fr.asterox.SafetyNet_Alerts.model.Household;
import fr.asterox.SafetyNet_Alerts.service.FirestationsService;
import fr.asterox.SafetyNet_Alerts.service.HouseholdsService;

/**
 * 
 * Process the requests concerning firestations.
 *
 */

@RestController
public class FirestationsController {
	// TODO ajouter des filtres dynamiques ?

	@Autowired
	private FirestationsService firestationsService;
	@Autowired
	private HouseholdsService householdsService;

//	@GetMapping(value = "/phoneAlert")
//	public List<String> getPhonesListAssignedToFirestation(@RequestParam int firestation) {
//		return firestationsService.getPhonesListAssignedToFirestation(firestation);
//	}

	/*
	 * @GetMapping(value = "/firestation") public Person emailList(@RequestParam int
	 * stationNumber) { return firestationService(stationNumber); }
	 */

	/*
	 * @GetMapping(value = "/firestation/stations") public Person
	 * emailList(@RequestParam int firestation) { return
	 * firestationService(firestation); }
	 */

	@GetMapping(value = "/flood/stations")
	public MappingJacksonValue getHouseholdsServedByStations(@RequestParam List<Integer> stations) {
		List<Household> householdsList = householdsService.getHouseholdsServedByStations(stations);
		SimpleBeanPropertyFilter filterRules = SimpleBeanPropertyFilter.serializeAllExcept("firstName", "address",
				"email");
		FilterProvider filterList = new SimpleFilterProvider().addFilter("personsInfoFilter", filterRules);
		MappingJacksonValue personsFilter = new MappingJacksonValue(householdsList);
		personsFilter.setFilters(filterList);
		return personsFilter;
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
