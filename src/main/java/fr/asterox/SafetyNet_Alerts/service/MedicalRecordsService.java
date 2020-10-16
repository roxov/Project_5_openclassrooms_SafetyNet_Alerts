package fr.asterox.SafetyNet_Alerts.service;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
import fr.asterox.SafetyNet_Alerts.model.Person;

@Service
public class MedicalRecordsService implements IMedicalRecordsService {
	private static final Logger LOGGER = LogManager.getLogger(MedicalRecordsService.class);

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
	public void addMedicalRecords(String firstName, String lastName, String birthdate, MedicalRecords medicalRecords) {
		// TODO : vérifier le format birthdate
		getPersonsList();
		allPersonsList.add(new Person(firstName, lastName, birthdate, null, null, null, medicalRecords));
		LOGGER.info("Adding Medical Records");
	}

	@Override
	public void updateMedicalRecords(String firstName, String lastName, MedicalRecords medicalRecords) {
		getPersonsMap();
		Person personToUpdate = PersonsIdentifiedByNamesMap.get(firstName + lastName);
		int index = allPersonsList.indexOf(personToUpdate);
		personToUpdate.setMedicalRecords(medicalRecords);
		allPersonsList.set(index, personToUpdate);
		LOGGER.info("Updating Medical Records");
	}

	@Override
	public void deleteMedicalRecords(String firstName, String lastName) {
		getPersonsMap();
		Person personToUpdate = PersonsIdentifiedByNamesMap.get(firstName + lastName);
		int index = allPersonsList.indexOf(personToUpdate);
		personToUpdate.setMedicalRecords(null);
		allPersonsList.set(index, personToUpdate);
		LOGGER.info("Deleting Medical Records");
	}
}
