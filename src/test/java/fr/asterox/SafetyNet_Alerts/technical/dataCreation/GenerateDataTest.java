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
	public void givenOriginalData_whenGenerateData_thenReturnDataObjectWithThreeLists() throws IOException {

		// WHEN
		Data result = generateData.generateData("src/test/java/resources/dataForTests.json");

		// THEN
		Address address = new Address("street1", 123, "Culver");
		assertEquals("fname1", result.getPersonsList().get(0).getFirstName());
		assertEquals("fname2", result.getPersonsList().get(1).getFirstName());
		assertEquals("lname1", result.getPersonsList().get(0).getLastName());
		assertEquals(address, result.getPersonsList().get(0).getAddress());
		assertEquals("01/01/1989", result.getPersonsList().get(0).getBirthdate());
		assertEquals("phone1", result.getPersonsList().get(0).getPhone());
		assertEquals("email1", result.getPersonsList().get(0).getEmail());

		List<Address> firestationAddresses = new ArrayList<>();
		firestationAddresses.add(address);
		assertEquals("street4", result.getFirestationsList().get(0).getAdressesList().get(0).getStreet());
		assertEquals(1, result.getFirestationsList().get(0).getStationNumber());

		List<String> medicationsList = new ArrayList<>();
		medicationsList.add("medications1");
		medicationsList.add("medications2");
		medicationsList.add("medications3");
		List<String> allergiesList = new ArrayList<>();
		MedicalRecords medicalRecords = new MedicalRecords(medicationsList, allergiesList);
		Person person = new Person("fname1", "lname1", "01/01/1989", address, "phone1", "email1", medicalRecords);
		List<Person> personsOfHousehold = new ArrayList<>();
		personsOfHousehold.add(person);
		assertEquals("street1", result.getHouseholdsList().get(2).getAddress().getStreet());
		assertEquals("fname1", result.getHouseholdsList().get(2).getPersonsList().get(0).getFirstName());

	}
}
