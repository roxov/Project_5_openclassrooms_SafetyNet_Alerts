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
	private static final Logger LOGGER = LogManager.getLogger(PersonsController.class);

	@Autowired
	private PersonsService personsService;

	@GetMapping(value = "/personInfo")
	public List<PersonInfoDTO> getInhabitantsInfo(@RequestParam String firstName, String lastName) {
		LOGGER.info("Getting Info on People With The Given Last Name for personInfo Request");
		return personsService.getInhabitantsInfo(firstName, lastName);
	}

	@GetMapping(value = "/communityEmail")
	public List<String> getEmailList(@RequestParam String city) {
		LOGGER.info("Getting Emails List of the city for communityEmail Request");
		return personsService.getEmailsListOfCity(city);
	}

	@PostMapping(value = "/person")
	public void addPerson(@RequestBody Person person) {
		LOGGER.info("Adding new Person");
		personsService.addPerson(person);
	}

	@PutMapping(value = "/person")
	public void updatePerson(@RequestBody Person person) {
		LOGGER.info("Updating Person");
		personsService.updatePerson(person);
	}

	@DeleteMapping(value = "/person")
	public void deletePerson(@RequestParam String firstName, String lastName) {
		LOGGER.info("Deleting Person");
		personsService.deletePerson(firstName, lastName);
	}
}
