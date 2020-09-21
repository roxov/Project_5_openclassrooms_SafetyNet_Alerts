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

import fr.asterox.SafetyNet_Alerts.model.Person;
import fr.asterox.SafetyNet_Alerts.service.PersonsService;

/**
 * 
 * Process the requests concerning people data.
 *
 */

@RestController
public class PersonsController {

	@Autowired
	private PersonsService personsService;

	@GetMapping(value = "/personInfo")
	public MappingJacksonValue getInhabitantsInfo(@RequestParam String firstName, String lastName) {

		// TODO : tester les filtres ?
		List<Person> personsList = personsService.getInhabitantsInfo(firstName, lastName);
		// TODO : retourner l'age et non la date de naissance

		SimpleBeanPropertyFilter filterRules = SimpleBeanPropertyFilter.serializeAllExcept("firstName", "phone");
		FilterProvider filterList = new SimpleFilterProvider().addFilter("personsInfoFilter", filterRules);
		MappingJacksonValue personsFilter = new MappingJacksonValue(personsList);
		personsFilter.setFilters(filterList);
		return personsFilter;
	}

	@GetMapping(value = "/communityEmail")
	public MappingJacksonValue getEmailList(@RequestParam String city) {
		List<Person> personsList = personsService.getPersonsListOfCity(city);
		SimpleBeanPropertyFilter filterRules = SimpleBeanPropertyFilter.serializeAllExcept("firstName", "lastName",
				"birthdate", "address", "phone", "medicalRecords");
		FilterProvider filterList = new SimpleFilterProvider().addFilter("personsInfoFilter", filterRules);
		MappingJacksonValue personsFilter = new MappingJacksonValue(personsList);
		personsFilter.setFilters(filterList);
		return personsFilter;
	}

	@PostMapping(value = "/person")
	public void addPerson(@RequestBody Person person) {
		personsService.addPerson(person);
	}

	@PutMapping(value = "/person")
	public void updatePerson(@RequestBody Person person) {
		personsService.updatePerson(person);
	}

	@DeleteMapping(value = "/person")
	public void deletePerson(@RequestBody Person person) {
		personsService.deletePerson(person);
	}
}
