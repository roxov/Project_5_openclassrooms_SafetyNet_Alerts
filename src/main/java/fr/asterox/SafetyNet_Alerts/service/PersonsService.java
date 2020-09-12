package fr.asterox.SafetyNet_Alerts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import fr.asterox.SafetyNet_Alerts.consumer.PersonDAO;
import fr.asterox.SafetyNet_Alerts.model.Person;

@Service
public class PersonsService implements IPersonsService {

	@Autowired
	private PersonDAO personDAO;

	public Person emailList(@RequestParam String address) {
		return personDAO.emailList(address);
	}

	public void addPerson(Person person) {
		// TODO : si JSON : le service va dispatcher les bonnes infos dans une new
		// Person.
		// Cela crée une personne qui sera envoyée à la DAO
		personDAO.addPerson(person);
	}

	public void updatePerson(Person person) {
		personDAO.updatePerson(person);
	}

	public void deletePerson(Person person) {
		personDAO.deletePerson(person);
	}
}
