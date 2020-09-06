package fr.asterox.SafetyNet_Alerts.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.asterox.SafetyNet_Alerts.dao.PersonDAO;
import fr.asterox.SafetyNet_Alerts.model.Person;

@RestController
public class EndpointsController {

	@Autowired
	private PersonDAO personDAO;

	@PostMapping(value = "/person")
	public void addPerson(@RequestBody Person person) {
		personDAO.save(person);
	}

	@GetMapping(value = "/firestation/{stationNumber}")
	public List<Person> personsAssociatedToFirestation(@PathVariable int stationNumber) {
		// TODO décompte adulte/enfant. Boucle pour comparer stationNumber et ajouter à
		// la liste les bonnes personnes
		// TODO ajouter des filtres dynamiques ?
		return personDAO.personFindByFirestaton(stationNumber);
	}
}
