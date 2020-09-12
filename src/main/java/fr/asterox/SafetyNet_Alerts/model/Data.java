package fr.asterox.SafetyNet_Alerts.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Load datas from data.json
 *
 */

public class Data {
//	public static List<Household> householdsList = new ArrayList<>();
	public static List<Firestation> firestationsList = new ArrayList<>();
	public static List<MedicalRecords> medicalRecordsList = new ArrayList<>();
	public static List<Person> personsList1 = new ArrayList<>();
	public static List<Person> personsList2 = new ArrayList<>();
	public static List<Person> personsList3 = new ArrayList<>();
	public static List<Person> personsList4 = new ArrayList<>();
	public static List<Address> adressesListStation1 = new ArrayList<>();
	public static List<Address> adressesListStation2 = new ArrayList<>();

	static {
		Address address1 = new Address("1509 Culver St", 97451, "Culver");
		Address address2 = new Address("29 15th St", 97451, "Culver");
		Address address3 = new Address("834 Binoc Ave", 97451, "Culver");
		Address address4 = new Address("644 Gershwin Cir", 97451, "Culver");

		adressesListStation1.add(address1);
		adressesListStation1.add(address2);
		adressesListStation2.add(address3);
		adressesListStation2.add(address4);

		firestationsList.add(new Firestation(adressesListStation1, 1));
		firestationsList.add(new Firestation(adressesListStation2, 2));

		personsList1.add(new Person("Marie", "Lou", "04/12/89", address1, 256845455, "singing@rain.fr",
				new MedicalRecords("aznol", "coconut")));
		personsList1.add(new Person("Happy", "New", "04/12/89", address1, 223232323, "xmas@gr",
				new MedicalRecords("aznol", "coconut")));
		personsList2.add(new Person("Kill", "Bill", "04/12/89", address2, 254545454, "cuir@yellow.com",
				new MedicalRecords("aznol", "coconut")));
		personsList3.add(new Person("Hagrid", "Legros", "04/12/89", address3, 154585858, "magic@lolo.com",
				new MedicalRecords("aznol", "coconut")));
		personsList4.add(new Person("Julie", "Cocotte", "04/12/89", address4, 25454555, "hello@moi.fr",
				new MedicalRecords("aznol", "peanut")));

		Household household1 = new Household(address1, personsList1);
		Household household2 = new Household(address2, personsList2);
		Household household3 = new Household(address3, personsList3);
		Household household4 = new Household(address4, personsList4);
	}
}
