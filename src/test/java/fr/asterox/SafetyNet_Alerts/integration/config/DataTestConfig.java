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

		Address address1 = new Address("address1", 123, "Culver");
		Address address2 = new Address("address2", 123, "Culver");
		Address address3 = new Address("address3", 123, "Culver");

		List<String> medications = new ArrayList<>();
		medications.add("medications1");
		medications.add("medications2");
		List<String> allergies = new ArrayList<>();
		allergies.add("allergies1");
		allergies.add("allergies2");
		MedicalRecords medicalRecords = new MedicalRecords(medications, allergies);

		Person person1 = new Person("adult1", "lname1", "03/06/1980", address1, "phone2", "email2", medicalRecords);
		Person person2 = new Person("child1", "lname1", "03/06/2010", address1, "phonechild", "emailchild",
				medicalRecords);
		Person person3 = new Person("adult2", "lname2", "03/06/1980", address2, "phone2", "email2", medicalRecords);
		Person person4 = new Person("adult3", "lname3", "03/06/1980", address3, "phone3", "email3", medicalRecords);
		List<Person> personsList = new ArrayList<>();
		personsList.add(person1);
		personsList.add(person2);
		personsList.add(person3);
		personsList.add(person4);
		data.setPersonsList(personsList);

		List<Firestation> firestationsList = new ArrayList<>();
		List<Address> adressesList = new ArrayList<>();
		adressesList.add(address1);
		adressesList.add(address2);
		List<Address> adressesList2 = new ArrayList<>();
		adressesList2.add(address3);
		firestationsList.add(new Firestation(1, adressesList));
		firestationsList.add(new Firestation(2, adressesList2));
		data.setFirestationsList(firestationsList);

		List<Household> householdsList = new ArrayList<>();
		List<Person> personsList1 = new ArrayList<>();
		personsList1.add(person1);
		personsList1.add(person2);
		List<Person> personsList2 = new ArrayList<>();
		personsList2.add(person3);
		List<Person> personsList3 = new ArrayList<>();
		personsList3.add(person4);
		householdsList.add(new Household(address1, personsList1));
		householdsList.add(new Household(address2, personsList2));
		householdsList.add(new Household(address3, personsList3));
		data.setHouseholdsList(householdsList);

		return data;
	}

}
