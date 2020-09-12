package fr.asterox.SafetyNet_Alerts.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
	/*
	 * @GetMapping(value = "/communityEmail") public Person emailList(@RequestParam
	 * String address) { return personsService.emailList(address); }
	 */
	/*
	 * @GetMapping(value = "/personInfo") public Person emailList(@RequestParam
	 * String firstName, String lastName) { return firestationService(firstName,
	 * lastName); }
	 */

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
