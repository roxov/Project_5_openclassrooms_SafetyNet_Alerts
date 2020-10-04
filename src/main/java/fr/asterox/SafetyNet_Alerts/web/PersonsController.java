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

import fr.asterox.SafetyNet_Alerts.model.Person;
import fr.asterox.SafetyNet_Alerts.service.PersonsService;
import fr.asterox.SafetyNet_Alerts.web.DTO.PersonInfoDTO;

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
	public List<PersonInfoDTO> getInhabitantsInfo(@RequestParam String firstName, String lastName) {
		return personsService.getInhabitantsInfo(firstName, lastName);
	}

	@GetMapping(value = "/communityEmail")
	public List<String> getEmailList(@RequestParam String city) {
		return personsService.getEmailsListOfCity(city);
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
