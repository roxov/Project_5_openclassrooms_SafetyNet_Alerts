package fr.asterox.SafetyNet_Alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.SafetyNet_Alerts.consumer.PersonDAO;
import fr.asterox.SafetyNet_Alerts.model.Person;

@Service
public class PersonsService implements IPersonsService {

	@Autowired
	private PersonDAO personDAO;

	@Override
	public List<Person> getInhabitantsInfo(String firstName, String lastName) {
		List<Person> allPersonsList = personDAO.getPersonsList();
		List<Person> personsSelectedByLastNameList = new ArrayList<>();
		for (Person person : allPersonsList) {
			if (person.getLastName().equals(lastName)) {
				personsSelectedByLastNameList.add(person);
			}
		}
		return personDAO.getPersonsList();
	}

	@Override
	public List<Person> getPersonsListOfCity(String city) {
		List<Person> allPersonsList = personDAO.getPersonsList();
		List<Person> personsListOfTheCity = new ArrayList<>();
		for (Person person : allPersonsList) {
			if (person.getAddress().getCity().equals(city)) {
				personsListOfTheCity.add(person);
			}
		}
		return personsListOfTheCity;
	}

	@Override
	public void addPerson(Person person) {
		personDAO.addPerson(person);
	}

	@Override
	public void updatePerson(Person person) {
		personDAO.updatePerson(person);
	}

	@Override
	public void deletePerson(Person person) {
		personDAO.deletePerson(person);
	}
}
