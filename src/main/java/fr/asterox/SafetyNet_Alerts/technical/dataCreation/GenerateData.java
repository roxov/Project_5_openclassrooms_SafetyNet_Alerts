package fr.asterox.SafetyNet_Alerts.technical.dataCreation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jsoniter.JsonIterator;
import com.jsoniter.ValueType;

import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.model.Firestation;
import fr.asterox.SafetyNet_Alerts.model.Household;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
import fr.asterox.SafetyNet_Alerts.model.Person;

/**
 * 
 * Load datas from data.json
 *
 */

public class GenerateData {

	public FileReader filereader;
	JsonIterator iterator;
	private static final Logger logger = LogManager.getLogger(GenerateData.class);

	public static List<Household> householdsList = new ArrayList<>();
	public static List<Firestation> firestationsList = new ArrayList<>();
	public static List<Person> personsList = new ArrayList<>();

	public String readDataSourceFile() throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data.json"));
			// TODO : trouver une méthode qui lit tout le fichier d'un coup ?
			StringBuilder stbuilder = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				stbuilder.append(line);
				line = reader.readLine();
			}
			String allData = stbuilder.toString();
			reader.close();
			return allData;
		} catch (FileNotFoundException e) {
			logger.fatal("The data source file was not found", e);
			throw new RuntimeException("The data source file was not found", e);
		}

	}

	public void parseData() throws IOException {
		String allData = this.readDataSourceFile();
		iterator = JsonIterator.parse(allData);
		for (String field = iterator.readObject(); field != null; field = iterator.readObject()) {
			switch (field) {
			case "persons":
				this.parsePersonsData();
			case "firestations":
				this.parseFirestationsData();
			case "medicalrecords":
				this.parseMedicalRecordsData();
			default:
				iterator.skip();
			}
		}
	}

	public void parsePersonsData() throws IOException {
		String firstName = null;
		String lastName = null;
		String street = null;
		String city = null;
		int zip = 0;
		String phone = null;
		String email = null;

		for (String field = iterator.readObject(); field != null; field = iterator.readObject()) {
			switch (field) {
			case "firstName":
				if (iterator.whatIsNext() == ValueType.STRING) {
					firstName = iterator.readString();
				}
				break;
			case "lastName":
				if (iterator.whatIsNext() == ValueType.STRING) {
					lastName = iterator.readString();
				}
				break;
			case "address":
				if (iterator.whatIsNext() == ValueType.STRING) {
					street = iterator.readString();
				}
				break;
			case "city":
				if (iterator.whatIsNext() == ValueType.STRING) {
					city = iterator.readString();
				}
				break;
			case "zip":
				if (iterator.whatIsNext() == ValueType.NUMBER) {
					zip = iterator.readInt();
				}
				break;
			case "phone":
				if (iterator.whatIsNext() == ValueType.STRING) {
					phone = iterator.readString();
				}
				break;
			case "email":
				if (iterator.whatIsNext() == ValueType.STRING) {
					email = iterator.readString();
				}
				break;
			}
			Address address = new Address(street, zip, city);
			Person person = new Person(firstName, lastName, null, address, phone, email, null);
			personsList.add(person);
		}
	}

	public void parseFirestationsData() throws IOException {
		String street = null;
		int stationNumber = 0;
		Address address = null;
		List<Address> addressesListOfTheStation = new ArrayList<>();

		for (String field = iterator.readObject(); field != null; field = iterator.readObject()) {
			switch (field) {
			case "address":
				if (iterator.whatIsNext() == ValueType.STRING) {
					street = iterator.readString();
				}
				break;
			case "station":
				if (iterator.whatIsNext() == ValueType.NUMBER) {
					stationNumber = iterator.readInt();
				}
				break;
			}

			for (Person person : personsList) {
				if (street.equals(person.getAddress().getStreet())) {
					address = person.getAddress();
					break;
				}
			}

			for (Firestation firestation : firestationsList) {
				if (stationNumber == firestation.getStationNumber()) {
					addressesListOfTheStation = firestation.getAdressesList();
					addressesListOfTheStation.add(address);
					firestation.setAdressesList(addressesListOfTheStation);
				}
				addressesListOfTheStation.add(address);
				Firestation newFirestation = new Firestation(stationNumber, addressesListOfTheStation);
				firestationsList.add(newFirestation);
			}
		}
	}

	public void parseMedicalRecordsData() throws IOException {
		String firstName = null;
		String lastName = null;
		String birthdate = null;
		List<String> medicationsList = new ArrayList<>();
		List<String> allergiesList = new ArrayList<>();
		MedicalRecords medicalRecords = new MedicalRecords();

		for (String field = iterator.readObject(); field != null; field = iterator.readObject()) {
			switch (field) {
			case "firstName":
				if (iterator.whatIsNext() == ValueType.STRING) {
					firstName = iterator.readString();
				}
				break;
			case "lastName":
				if (iterator.whatIsNext() == ValueType.STRING) {
					lastName = iterator.readString();
				}
				break;
			case "birthdate":
				if (iterator.whatIsNext() == ValueType.STRING) {
					birthdate = iterator.readString();
				}
				break;
			case "medications":
				while (iterator.readArray()) {
					if (iterator.whatIsNext() == ValueType.STRING) {
						String medications = iterator.readString();
						medicationsList.add(medications);
					}
					iterator.skip();
				}
				break;
			case "allergies":
				while (iterator.readArray()) {
					if (iterator.whatIsNext() == ValueType.STRING) {
						String allergies = iterator.readString();
						allergiesList.add(allergies);
					}
					iterator.skip();
				}
				break;
			}
		}

		for (Person person : personsList) {
			if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
				person.setBirthdate(birthdate);
				person.setMedicalRecords(medicalRecords);
				break;
			}
		}
	}

	public Data generateData() throws IOException {
		Data data = new Data();
		this.parseData();

		data.setPersonsList(personsList);
		data.setFirestationsList(firestationsList);

		for (Person person : personsList) {
			for (Household household : householdsList) {
				if (person.getAddress().equals(household.getAddress())) {
					int index = householdsList.indexOf(household);
					List<Person> personsListOfFinalHousehold = household.getPersonsList();
					personsListOfFinalHousehold.add(person);
					household.setPersonsList(personsListOfFinalHousehold);
					householdsList.set(index, household);
				}
				List<Person> personsOfHousehold = new ArrayList<>();
				personsOfHousehold.add(person);
				Household newHousehold = new Household(person.getAddress(), personsOfHousehold);
				householdsList.add(newHousehold);
			}
		}
		data.setHouseholdsList(householdsList);
		return data;
	}
}
