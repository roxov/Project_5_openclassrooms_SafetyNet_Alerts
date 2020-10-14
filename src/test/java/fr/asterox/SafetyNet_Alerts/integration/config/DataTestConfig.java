package fr.asterox.SafetyNet_Alerts.integration.config;

import java.util.ArrayList;
import java.util.List;

import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.model.Firestation;
import fr.asterox.SafetyNet_Alerts.model.Household;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
import fr.asterox.SafetyNet_Alerts.model.Person;

public class DataTestConfig {

	public Data generateData() {
		Data data = new Data();

		Address address1 = new Address("street1", 123, "Culver");

		List<String> medications = new ArrayList<>();
		medications.add("medications1");
		medications.add("medications2");
		List<String> allergies = new ArrayList<>();
		allergies.add("allergies1");
		allergies.add("allergies2");
		MedicalRecords medicalRecords = new MedicalRecords(medications, allergies);

		Person person1 = new Person("adult1", "lname1", "03/06/1980", address1, "phone1", "email1", medicalRecords);
		List<Person> personsList = new ArrayList<>();
		personsList.add(person1);
		data.setPersonsList(personsList);

		List<Firestation> firestationsList = new ArrayList<>();
		List<Address> addressesList = new ArrayList<>();
		addressesList.add(address1);
		firestationsList.add(new Firestation(1, addressesList));
		data.setFirestationsList(firestationsList);

		List<Household> householdsList = new ArrayList<>();
		List<Person> personsList1 = new ArrayList<>();
		personsList1.add(person1);
		householdsList.add(new Household(address1, personsList1));
		data.setHouseholdsList(householdsList);

		return data;
	}

}
