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
		Address address2 = new Address("address2", 44444, "Culver");
		Address address3 = new Address("address3", 44444, "Culver");
		List<Address> adressesList = new ArrayList<>();
		adressesList.add(address1);
		adressesList.add(address2);
		List<Address> adressesList2 = new ArrayList<>();
		adressesList2.add(address3);
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
		List<Person> personsList3 = new ArrayList<>();
		personsList3.add(person3);
		List<Person> personsList4 = new ArrayList<>();
		personsList4.add(person4);
		Firestation firestation = new Firestation(1, adressesList);
		Firestation firestation2 = new Firestation(2, adressesList2);
		Household household = new Household(address1, personsList);
		Household household3 = new Household(address2, personsList3);
		Household household4 = new Household(address3, personsList4);

		List<Household> householdsList = new ArrayList<>();
		householdsList.add(household);
		householdsList.add(household3);
		householdsList.add(household4);
		List<Firestation> firestationsList = new ArrayList<>();
		firestationsList.add(firestation);
		firestationsList.add(firestation2);
		Data data = new Data();
		data.setFirestationsList(firestationsList);
		data.setHouseholdsList(householdsList);
		data.setPersonsList(personsList);
		return data;
	}
}
