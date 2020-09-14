package fr.asterox.SafetyNet_Alerts.technical.dataCreation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.Firestation;
import fr.asterox.SafetyNet_Alerts.model.Household;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
import fr.asterox.SafetyNet_Alerts.model.Person;
import fr.asterox.SafetyNet_Alerts.technical.dataCreation.dataSourceModel.PersonModel;
import fr.asterox.SafetyNet_Alerts.technical.dataCreation.dataSourceModel.RootModel;

/**
 * 
 * Load datas from data.json
 *
 */

public class Data {

	@Autowired
	private JsonReader jsonReader;

	public static List<Address> addressesList = new ArrayList<>();

	// public static List<Household> householdsList = new ArrayList<>();
	public static List<Firestation> firestationsList = new ArrayList<>();
	public static List<MedicalRecords> medicalRecordsList = new ArrayList<>();
	public static List<Person> personsList1 = new ArrayList<>();
	public static List<Person> personsList2 = new ArrayList<>();
	public static List<Person> personsList3 = new ArrayList<>();
	public static List<Person> personsList4 = new ArrayList<>();
	public static List<Address> adressesListStation1 = new ArrayList<>();
	public static List<Address> adressesListStation2 = new ArrayList<>();

	static {
//1 ROOT MODEL qui contient
		// public List<PersonModel> persons; String firstName + lastName + address =
		// street + city; int zip; String phone + email;
		// public List<FirestationModel> firestations; String address = street+ int
		// station;
		// public List<MedicalRecordsModel> medicalrecords;String firstName + lastName +
		// String birthdate + String medications + String allergies;

		// Person : Map nom/prenom, birthdate à transformer en LocalDateTime, Address,
		// phone, email, MedicalRecords;

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

		Household household1 = new Household(address1, personsList1);
		Household household2 = new Household(address2, personsList2);
		Household household3 = new Household(address3, personsList3);
		Household household4 = new Household(address4, personsList4);
	}

	public List<Address> createAddressesList() throws IOException {
		RootModel rootModel = new JsonReader().createObjectFromData();
		// Pas compris annotation pour importer les données @Bean Data étant sur main
		List<PersonModel> personsModelList = rootModel.getPersons();
		// String street;
		// private int zip;
		// private String city;
		return null;
	}

}
