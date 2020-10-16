package fr.asterox.SafetyNet_Alerts.technical.dataCreation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
import fr.asterox.SafetyNet_Alerts.model.Person;

@ExtendWith(MockitoExtension.class)
public class GenerateDataTest {

	private GenerateData generateData = new GenerateData();

	@Test
	public void givenOridinalData_whenGenerateData_thenReturnDataObjectWithThreeLists() throws IOException {

		// WHEN
		Data result = generateData.generateData();

		// THEN
		Address address = new Address("street", 123, "city");
		assertEquals("fname", result.getPersonsList().get(0).getFirstName());
		assertEquals("lname", result.getPersonsList().get(0).getLastName());
		assertEquals(address, result.getPersonsList().get(0).getAddress());
		assertEquals("birthdate", result.getPersonsList().get(0).getBirthdate());
		assertEquals("phone", result.getPersonsList().get(0).getPhone());
		assertEquals("email", result.getPersonsList().get(0).getEmail());

		List<Address> firestationAddresses = new ArrayList<>();
		firestationAddresses.add(address);
		assertEquals("street", result.getFirestationsList().get(0).getAdressesList());
		assertEquals(1, result.getFirestationsList().get(0).getStationNumber());

		List<String> medicationsList = new ArrayList<>();
		medicationsList.add("medications1");
		medicationsList.add("medications2");
		List<String> allergiesList = new ArrayList<>();
		allergiesList.add("allergies1");
		allergiesList.add("allergies2");
		MedicalRecords medicalRecords = new MedicalRecords(medicationsList, allergiesList);
		Person person = new Person("fname", "lname", "birthdate", address, "phone", "email", medicalRecords);
		List<Person> personsOfHousehold = new ArrayList<>();
		personsOfHousehold.add(person);
		assertEquals(address, result.getHouseholdsList().get(0).getAddress());
		assertEquals(person, result.getHouseholdsList().get(0).getPersonsList());

	}
//	@Test
//	public void givenRawData_whenGenerateData_thenReturnDataObjectWithThreeLists() throws IOException {
//		// GIVEN
//		String rawData = "{\"persons\": [{ \"firstName\":\"fname\", \"lastName\":\"lname\", \"address\":\"street\", \"city\":\"city\", \"zip\":\"123\", \"phone\":\"phone\", \"email\":\"email\" }],"
//				+ " \"firestations\":[ { \"address\":\"street\", \"station\":\"1\" }],"
//				+ "  \"medicalrecords\": [{ \"firstName\":\"fname\", \"lastName\":\"lname\", \"birthdate\":\"birthdate\", \"medications\":[\"medications1\", \"medications2\"], \"allergies\":[\"allergies1\", \"allergies2\"] } ]}";
//
//		when(filepath.readDataSourceFile()).thenReturn(rawData);
//
//		// WHEN
//		Data result = generateData.generateData();
//
//		// THEN
//		verify(filepath, Mockito.times(1)).readDataSourceFile();
//		Address address = new Address("street", 123, "city");
//		assertEquals("fname", result.getPersonsList().get(0).getFirstName());
//		assertEquals("lname", result.getPersonsList().get(0).getLastName());
//		assertEquals(address, result.getPersonsList().get(0).getAddress());
//		assertEquals("birthdate", result.getPersonsList().get(0).getBirthdate());
//		assertEquals("phone", result.getPersonsList().get(0).getPhone());
//		assertEquals("email", result.getPersonsList().get(0).getEmail());
//
//		List<Address> firestationAddresses = new ArrayList<>();
//		firestationAddresses.add(address);
//		assertEquals("street", result.getFirestationsList().get(0).getAdressesList());
//		assertEquals(1, result.getFirestationsList().get(0).getStationNumber());
//
//		List<String> medicationsList = new ArrayList<>();
//		medicationsList.add("medications1");
//		medicationsList.add("medications2");
//		List<String> allergiesList = new ArrayList<>();
//		allergiesList.add("allergies1");
//		allergiesList.add("allergies2");
//		MedicalRecords medicalRecords = new MedicalRecords(medicationsList, allergiesList);
//		Person person = new Person("fname", "lname", "birthdate", address, "phone", "email", medicalRecords);
//		List<Person> personsOfHousehold = new ArrayList<>();
//		personsOfHousehold.add(person);
//		assertEquals(address, result.getHouseholdsList().get(0).getAddress());
//		assertEquals(person, result.getHouseholdsList().get(0).getPersonsList());
//
//	}
}
