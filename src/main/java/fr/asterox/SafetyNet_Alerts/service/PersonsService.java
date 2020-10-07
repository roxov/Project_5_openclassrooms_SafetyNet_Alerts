package fr.asterox.SafetyNet_Alerts.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.SafetyNet_Alerts.consumer.PersonDAO;
import fr.asterox.SafetyNet_Alerts.model.Person;
import fr.asterox.SafetyNet_Alerts.technical.ManipulateDate;
import fr.asterox.SafetyNet_Alerts.web.DTO.PersonInfoDTO;

@Service
public class PersonsService implements IPersonsService {
	private static final Logger LOGGER = LogManager.getLogger(PersonsService.class);

	@Autowired
	private PersonDAO personDAO;

	@Override
	public List<PersonInfoDTO> getInhabitantsInfo(String firstName, String lastName) {
		List<Person> allPersonsList = personDAO.getPersonsList();
		List<PersonInfoDTO> personsSelectedByLastNameList = new ArrayList<>();
		for (Person person : allPersonsList) {
			if (person.getLastName().equals(lastName) && person.getFirstName().equals(firstName)) {
				LocalDate birthdate = ManipulateDate.convertStringToLocalDate(person.getBirthdate());
				int age = LocalDate.now().getYear() - birthdate.getYear();
				PersonInfoDTO personInfoDTO = new PersonInfoDTO(person.getLastName(), person.getAddress(), age,
						person.getEmail(), person.getMedicalRecords());
				personsSelectedByLastNameList.add(personInfoDTO);
			}
		}
		return personsSelectedByLastNameList;
	}

	@Override
	public List<String> getEmailsListOfCity(String city) {
		List<Person> allPersonsList = personDAO.getPersonsList();
		List<String> emailsListOfTheCity = new ArrayList<>();
		for (Person person : allPersonsList) {
			if (person.getAddress().getCity().equals(city)) {
				emailsListOfTheCity.add(person.getEmail());
			}
		}
		return emailsListOfTheCity;
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
