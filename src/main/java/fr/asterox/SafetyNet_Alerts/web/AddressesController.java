package fr.asterox.SafetyNet_Alerts.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import fr.asterox.SafetyNet_Alerts.service.AddressesService;

/**
 * 
 * Process the requests concerning addresses.
 *
 */

@RestController
public class AddressesController {

	@Autowired
	private AddressesService addressesService;

	@GetMapping(value = "/childAlert")
	public MappingJacksonValue getPersonsLivingInChildHousehold(@RequestParam String address) {
		Object[] personsLivingInChildHousehold = addressesService.getPersonsLivingInChildHousehold(address);
		SimpleBeanPropertyFilter filterRules = SimpleBeanPropertyFilter.serializeAllExcept("firstName", "address",
				"phone", "email");
		FilterProvider filterList = new SimpleFilterProvider().addFilter("personsInfoFilter", filterRules);
		MappingJacksonValue personsFilter = new MappingJacksonValue(personsLivingInChildHousehold);
		personsFilter.setFilters(filterList);
		return personsFilter;
	}

	@GetMapping(value = "/fire")
	public MappingJacksonValue getInhabitantsAndStationOfTheAddress(@RequestParam String address) {
		Object[] householdsList = addressesService.getInhabitantsAndStationOfTheAddress(address);
		SimpleBeanPropertyFilter filterRules = SimpleBeanPropertyFilter.serializeAllExcept("firstName", "birthdate",
				"address", "phone", "email");
		FilterProvider filterList = new SimpleFilterProvider().addFilter("personsInfoFilter", filterRules);
		MappingJacksonValue personsFilter = new MappingJacksonValue(householdsList);
		personsFilter.setFilters(filterList);
		return personsFilter;
	}
}
