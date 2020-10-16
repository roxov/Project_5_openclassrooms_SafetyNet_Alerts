package fr.asterox.SafetyNet_Alerts.technical.dataCreation;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
	Map<Integer, List<Address>> firestationsMap = new HashMap<>();
	private static final Logger LOGGER = LogManager.getLogger(GenerateData.class);

	public static List<Household> householdsList = new ArrayList<>();
	public static List<Firestation> firestationsList = new ArrayList<>();
	public static List<Person> personsList = new ArrayList<>();

	public void parseData() throws IOException {
		String allData = FilePath.readDataSourceFile();
		iterator = JsonIterator.parse(allData);
		for (String field = iterator.readObject(); field != null; field = iterator.readObject()) {
			switch (field) {
			case "persons":
				this.parsePersonsData();
				break;
			case "firestations":
				this.parseFirestationsData();
				break;
			case "medicalrecords":
				this.parseMedicalRecordsData();
				break;
			default:
				iterator.skip();
			}
		}
		LOGGER.debug("Parsing datas from json file");
	}

	public void parsePersonsData() throws IOException {
		String firstName = null;
		String lastName = null;
		String street = null;
		String city = null;
		int zip = 0;
		String phone = null;
		String email = null;
		while (iterator.readArray()) {
			for (String field = iterator.readObject(); field != null; field = iterator.readObject()) {

				switch (field) {
				case "firstName":
					if (iterator.whatIsNext() == ValueType.STRING) {
						firstName = iterator.readString();
					} else {
						LOGGER.error("Illegal type for firstName data");
						iterator.skip();
					}
					break;
				case "lastName":
					if (iterator.whatIsNext() == ValueType.STRING) {
						lastName = iterator.readString();
					} else {
						LOGGER.error("Illegal type for lastName data");
						iterator.skip();
					}
					break;
				case "address":
					if (iterator.whatIsNext() == ValueType.STRING) {
						street = iterator.readString();
					} else {
						LOGGER.error("Illegal type for address data");
						iterator.skip();
					}
					break;
				case "city":
					if (iterator.whatIsNext() == ValueType.STRING) {
						city = iterator.readString();
					} else {
						LOGGER.error("Illegal type for city data");
						iterator.skip();
					}
					break;
				case "zip":
					if (iterator.whatIsNext() == ValueType.STRING) {
						zip = Integer.parseInt(iterator.readString());
					} else {
						LOGGER.error("Illegal type for zip data");
						iterator.skip();
					}
					break;
				case "phone":
					if (iterator.whatIsNext() == ValueType.STRING) {
						phone = iterator.readString();
					} else {
						LOGGER.error("Illegal type for phone data");
						iterator.skip();
					}
					break;
				case "email":
					if (iterator.whatIsNext() == ValueType.STRING) {
						email = iterator.readString();
					} else {
						LOGGER.error("Illegal type for email data");
						iterator.skip();
					}
					break;
				default:
					iterator.skip();
				}
			}
			Address address = new Address(street, zip, city);
			Person person = new Person(firstName, lastName, null, address, phone, email, null);
			personsList.add(person);
		}
	}

	public void parseFirestationsData() throws IOException {
		String street = null;
		Integer stationNumber = 0;
		Address address = null;

		while (iterator.readArray()) {
			for (String field = iterator.readObject(); field != null; field = iterator.readObject()) {
				switch (field) {
				case "address":
					if (iterator.whatIsNext() == ValueType.STRING) {
						street = iterator.readString();
					} else {
						LOGGER.error("Illegal type for address data");
						iterator.skip();
					}
					break;
				case "station":
					if (iterator.whatIsNext() == ValueType.STRING) {
						stationNumber = Integer.parseInt(iterator.readString());
					} else {
						LOGGER.error("Illegal type for station number data");
						iterator.skip();
					}
					break;
				default:
					iterator.skip();
				}

				for (Person person : personsList) {
					if (street.equals(person.getAddress().getStreet())) {
						address = person.getAddress();
						break;
					}
				}
			}
			firestationsMap.put(stationNumber, firestationsMap.getOrDefault(stationNumber, new ArrayList<Address>()));
			List<Address> currentFirestationAddressesList = firestationsMap.get(stationNumber);
			currentFirestationAddressesList.add(address);
			firestationsMap.put(stationNumber, currentFirestationAddressesList);
		}
	}

	public void parseMedicalRecordsData() throws IOException {
		String firstName = null;
		String lastName = null;
		String birthdate = null;
		List<String> medicationsList = new ArrayList<>();
		List<String> allergiesList = new ArrayList<>();

		while (iterator.readArray()) {
			for (String field = iterator.readObject(); field != null; field = iterator.readObject()) {
				switch (field) {
				case "firstName":
					if (iterator.whatIsNext() == ValueType.STRING) {
						firstName = iterator.readString();
					} else {
						LOGGER.error("Illegal type for firstName data");
						iterator.skip();
					}
					break;
				case "lastName":
					if (iterator.whatIsNext() == ValueType.STRING) {
						lastName = iterator.readString();
					} else {
						LOGGER.error("Illegal type for lastName data");
						iterator.skip();
					}
					break;
				case "birthdate":
					if (iterator.whatIsNext() == ValueType.STRING) {
						birthdate = iterator.readString();
					} else {
						LOGGER.error("Illegal type for birthdate data");
						iterator.skip();
					}
					break;
				case "medications":
					while (iterator.readArray()) {
						if (iterator.whatIsNext() == ValueType.STRING) {
							String medications = iterator.readString();
							medicationsList.add(medications);
						} else {
							LOGGER.error("Illegal type for medications data");
							iterator.skip();
						}
					}
					break;
				case "allergies":
					while (iterator.readArray()) {
						if (iterator.whatIsNext() == ValueType.STRING) {
							String allergies = iterator.readString();
							allergiesList.add(allergies);
						} else {
							LOGGER.error("Illegal type for allergies data");
							iterator.skip();
						}
					}
					break;
				default:
					iterator.skip();
				}
			}

			for (Person person : personsList) {
				if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
					person.setBirthdate(birthdate);
					person.setMedicalRecords(new MedicalRecords(medicationsList, allergiesList));
					break;
				}
			}
		}
	}

	public Data generateData() throws IOException {
		Data data = new Data();
		this.parseData();

		data.setPersonsList(personsList);

		Set<Entry<Integer, List<Address>>> setFirestationsEntry = firestationsMap.entrySet();
		for (Entry<Integer, List<Address>> fEntry : setFirestationsEntry) {
			firestationsList.add(new Firestation(fEntry.getKey(), fEntry.getValue()));
		}
		data.setFirestationsList(firestationsList);
//
		Map<Address, List<Person>> householdsMap = new HashMap<>();
		for (Person person : personsList) {
			if (householdsMap.get(person.getAddress()) == null) {
				List<Person> personsInHousehold = new ArrayList<>();
				personsInHousehold.add(person);
				householdsMap.put(person.getAddress(), personsInHousehold);
			} else {

				List<Person> personsInHousehold = householdsMap.get(person.getAddress());
				personsInHousehold.add(person);
				householdsMap.put(person.getAddress(), personsInHousehold);
			}

		}

		Set<Entry<Address, List<Person>>> setHouseholdsEntry = householdsMap.entrySet();
		for (Entry<Address, List<Person>> hEntry : setHouseholdsEntry) {
			householdsList.add(new Household(hEntry.getKey(), hEntry.getValue()));
		}
		data.setHouseholdsList(householdsList);
		LOGGER.debug("Generating a filled Data");
		return data;
	}
}
