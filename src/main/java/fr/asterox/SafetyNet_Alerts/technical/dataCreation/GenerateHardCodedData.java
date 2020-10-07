package fr.asterox.SafetyNet_Alerts.technical.dataCreation;

import java.util.ArrayList;
import java.util.List;

import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.model.Firestation;
import fr.asterox.SafetyNet_Alerts.model.Household;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
import fr.asterox.SafetyNet_Alerts.model.Person;

public class GenerateHardCodedData {
	public Data generateData() {

		Address address1 = new Address("1509 Culver St", 97451, "Culver");
		List<Address> adressesList = new ArrayList<>();
		adressesList.add(address1);
		List<String> medications = new ArrayList<>();
		medications.add("medications1");
		medications.add("medications2");
		List<String> allergies = new ArrayList<>();
		allergies.add("allergies1");
		allergies.add("allergies2");
		MedicalRecords medicalRecords = new MedicalRecords(medications, allergies);
		Person person1 = new Person("adult1", "lname1", "03/06/1980", address1, "phone2", "email2", medicalRecords);
		Person person2 = new Person("child1", "lname1", "03/06/2010", address1, "phone2", "email2", medicalRecords);
		List<Person> personsList = new ArrayList<>();
		personsList.add(person1);
		personsList.add(person2);
		Firestation firestion = new Firestation(1, adressesList);
		Household household = new Household(address1, personsList);

		List<Household> householdsList = new ArrayList<>();
		householdsList.add(household);
		List<Firestation> firestationsList = new ArrayList<>();
		firestationsList.add(firestion);

		Data data = new Data();
		data.setFirestationsList(firestationsList);
		data.setHouseholdsList(householdsList);
		data.setPersonsList(personsList);
		return data;
	}
}
