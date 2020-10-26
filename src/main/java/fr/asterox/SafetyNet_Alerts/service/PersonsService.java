package fr.asterox.SafetyNet_Alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.model.Person;
import fr.asterox.SafetyNet_Alerts.technical.CalculateAge;
import fr.asterox.SafetyNet_Alerts.web.DTO.PersonInfoDTO;

@Service
public class PersonsService implements IPersonsService {
	private static final Logger LOGGER = LogManager.getLogger(PersonsService.class);

	@Autowired
	private Data data;

	@Override
	public List<PersonInfoDTO> getInhabitantsInfo(String firstName, String lastName) {
		if (firstName == null || lastName == null) {
			LOGGER.error("Impossible to get information : empty first name or last name");
			return null;
		}

		List<Person> allPersonsList = getPersonsList();
		List<PersonInfoDTO> personsSelectedByLastNameList = new ArrayList<>();

		for (Person person : allPersonsList) {
			if (person.getLastName().equals(lastName)) {
				int age = CalculateAge.calculateAge(person.getBirthdate());
				PersonInfoDTO personInfoDTO = new PersonInfoDTO(person.getLastName(), person.getAddress(), age,
						person.getEmail(), person.getMedicalRecords());
				personsSelectedByLastNameList.add(personInfoDTO);
				LOGGER.info("Response to Person Info : Getting info of the person");
			}

			LOGGER.error("Impossible to get information : no match found");

		}
		return personsSelectedByLastNameList;
	}

	@Override
	public List<String> getEmailsListOfCity(String city) {
		List<Person> allPersonsList = getPersonsList();
		List<String> emailsListOfTheCity = new ArrayList<>();
		for (Person person : allPersonsList) {
			if (person.getAddress().getCity().equals(city)) {
				emailsListOfTheCity.add(person.getEmail());
			}
		}
		LOGGER.info("Response to Community Email Request : Getting emails list of the city");
		return emailsListOfTheCity;
	}

	@Override
	public void addPerson(Person person) {
		List<Person> allPersonsList = getPersonsList();

		if (person.getFirstName() == null || person.getLastName() == null || person.getBirthdate() == null
				|| person.getAddress() == null || person.getEmail() == null || person.getPhone() == null) {
			LOGGER.error("Impossible to add person : missing fields");
			return;
		}
		allPersonsList.add(person);
		LOGGER.info("Adding a person");

	}

	@Override
	public void updatePerson(Person person) {
		List<Person> allPersonsList = getPersonsList();

		if (person.getFirstName() == null || person.getLastName() == null || person.getBirthdate() == null
				|| person.getAddress() == null || person.getEmail() == null || person.getPhone() == null) {
			LOGGER.error("Impossible to update person : missing fields");
			return;
		}
		for (Person personInList : allPersonsList) {
			if (person.getFirstName().equals(personInList.getFirstName())
					&& person.getLastName().equals(personInList.getLastName())) {
				int index = allPersonsList.indexOf(personInList);
				allPersonsList.set(index, person);
				LOGGER.info("Updating a person");
				break;
			} else {
				LOGGER.error("Impossible to update person : no match found");
			}
		}
	}

	@Override
	public void deletePerson(String firstName, String lastName) {
		List<Person> allPersonsList = getPersonsList();

		if (firstName == null || lastName == null) {
			LOGGER.error("Impossible to delete person : empty first name or last name");
			return;
		}
		for (Person personInList : allPersonsList) {
			if (firstName.equals(personInList.getFirstName()) && lastName.equals(personInList.getLastName())) {
				int index = allPersonsList.indexOf(personInList);
				allPersonsList.remove(index);
				LOGGER.info("Deleting a person");
				break;
			} else {
				LOGGER.error("Impossible to delete person : no match found");
			}

		}
	}

	private List<Person> getPersonsList() {
		return data.getPersonsList();
	}
}
