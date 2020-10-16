package fr.asterox.SafetyNet_Alerts.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.model.Person;
import fr.asterox.SafetyNet_Alerts.technical.ManipulateDate;
import fr.asterox.SafetyNet_Alerts.web.DTO.PersonInfoDTO;

@Service
public class PersonsService implements IPersonsService {
	private static final Logger LOGGER = LogManager.getLogger(PersonsService.class);

	@Autowired
	private Data data;

	private List<Person> allPersonsList;
	private Map<String, Person> PersonsIdentifiedByNamesMap;

	private void getPersonsList() {
		allPersonsList = data.getPersonsList();
	}

	private void getPersonsMap() {
		getPersonsList();
		for (Person person : allPersonsList) {
			String key = person.getFirstName() + person.getLastName();
			PersonsIdentifiedByNamesMap.put(key, person);
		}
	}

	@Override
	public List<PersonInfoDTO> getInhabitantsInfo(String firstName, String lastName) {
		getPersonsList();
		List<PersonInfoDTO> personsSelectedByLastNameList = new ArrayList<>();
		for (Person person : allPersonsList) {
			if (person.getLastName().equals(lastName)) {
				LocalDate birthdate = ManipulateDate.convertStringToLocalDate(person.getBirthdate());
				int age = LocalDate.now().getYear() - birthdate.getYear();
				PersonInfoDTO personInfoDTO = new PersonInfoDTO(person.getLastName(), person.getAddress(), age,
						person.getEmail(), person.getMedicalRecords());
				personsSelectedByLastNameList.add(personInfoDTO);
			}
		}
		LOGGER.info("Response to Person Info : Getting info of the person");
		return personsSelectedByLastNameList;
	}

	@Override
	public List<String> getEmailsListOfCity(String city) {
		getPersonsList();
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
		getPersonsList();
		allPersonsList.add(person);
		LOGGER.info("Adding a person");
	}

	@Override
	public void updatePerson(Person person) {
		getPersonsMap();
		String personToUpdate = person.getFirstName() + person.getLastName();
		int index = allPersonsList.indexOf(PersonsIdentifiedByNamesMap.get(personToUpdate));
		allPersonsList.set(index, person);
		LOGGER.info("Updating a person");

	}

	@Override
	public void deletePerson(String firstName, String lastName) {
		// TODO : null
		getPersonsMap();
		String personToDelete = firstName + lastName;
		int index = allPersonsList.indexOf(PersonsIdentifiedByNamesMap.get(personToDelete));
		allPersonsList.remove(index);
		LOGGER.info("Deleting a person");

	}
}
