package fr.asterox.SafetyNet_Alerts.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.SafetyNet_Alerts.consumer.PersonDAO;
import fr.asterox.SafetyNet_Alerts.model.Person;
import fr.asterox.SafetyNet_Alerts.technical.ManipulateDate;

@Service
public class PersonsService implements IPersonsService {

	@Autowired
	private PersonDAO personDAO;

	@Override
	public Map<Person, String> getInhabitantsInfo(String firstName, String lastName) {
		List<Person> allPersonsList = personDAO.getPersonsList();
		Map<Person, String> personsSelectedByLastNameList = new HashMap<>();
		for (Person person : allPersonsList) {
			if (person.getLastName().equals(lastName)) {
				LocalDate birthdate = ManipulateDate.convertStringToLocalDate(person.getBirthdate());
				Integer age = LocalDate.now().getYear() - birthdate.getYear();
				String ageSt = age + " years old";
				personsSelectedByLastNameList.put(person, ageSt);
			}
		}
		return personsSelectedByLastNameList;
		// TODO : renvoie une person 2c3d...
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
