package fr.asterox.SafetyNet_Alerts.service;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public void addMedicalRecords(String firstName, String lastName, MedicalRecords medicalRecords) {
		List<Person> allPersonsList = getPersonsList();

		if (firstName == null || lastName == null || medicalRecords == null) {
			LOGGER.error("Impossible to add medical records : missing fields");
			return;
		}
		for (Person personToUpdate : allPersonsList) {
			if (firstName.equals(personToUpdate.getFirstName()) && lastName.equals(personToUpdate.getLastName())) {
				if (personToUpdate.getMedicalRecords() == null) {
					int index = allPersonsList.indexOf(personToUpdate);
					personToUpdate.setMedicalRecords(medicalRecords);
					allPersonsList.set(index, personToUpdate);
					LOGGER.info("Adding Medical Records");
					break;
				} else {
					LOGGER.error("Impossible to add medical records : existing medical records");
				}
			} else {
				LOGGER.error("Impossible to add medical records : no match found for these names");
			}
		}
	}

	@Override
	public void updateMedicalRecords(String firstName, String lastName, MedicalRecords medicalRecords) {
		List<Person> allPersonsList = getPersonsList();

		if (firstName == null || lastName == null || medicalRecords == null) {
			LOGGER.error("Impossible to update medical records : missing fields");
			return;
		}
		for (Person personToUpdate : allPersonsList) {
			if (firstName.equals(personToUpdate.getFirstName()) && lastName.equals(personToUpdate.getLastName())) {
				List<String> newMedications = medicalRecords.getMedications();
				List<String> newAllergies = medicalRecords.getAllergies();
				List<String> existingMedications = personToUpdate.getMedicalRecords().getMedications();
				List<String> existingAllergies = personToUpdate.getMedicalRecords().getAllergies();
				newMedications.addAll(existingMedications);
				newAllergies.addAll(existingAllergies);
				int index = allPersonsList.indexOf(personToUpdate);
				personToUpdate.setMedicalRecords(new MedicalRecords(newMedications, newAllergies));
				allPersonsList.set(index, personToUpdate);
				LOGGER.info("Updating Medical Records");
				break;
			} else {
				LOGGER.error("Impossible to update medical records : no match found for these names");
			}

		}
	}

	@Override
	public void deleteMedicalRecords(String firstName, String lastName) {
		List<Person> allPersonsList = getPersonsList();

		if (firstName == null || lastName == null) {
			LOGGER.error("Impossible to delete medical records : missing first name or last name");
			return;
		}
		for (Person personToUpdate : allPersonsList) {
			if (firstName.equals(personToUpdate.getFirstName()) && lastName.equals(personToUpdate.getLastName())) {
				int index = allPersonsList.indexOf(personToUpdate);
				personToUpdate.setMedicalRecords(new MedicalRecords(new ArrayList<>(), new ArrayList<>()));
				allPersonsList.set(index, personToUpdate);
				LOGGER.info("Deleting Medical Records");
				break;
			} else {
				LOGGER.error("Impossible to delete medical records : no match found for these names");
			}
		}
	}

	private List<Person> getPersonsList() {
		return data.getPersonsList();
	}
}
