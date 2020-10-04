package fr.asterox.SafetyNet_Alerts.technical.dataCreation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.model.Firestation;
import fr.asterox.SafetyNet_Alerts.model.Household;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
import fr.asterox.SafetyNet_Alerts.model.Person;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GenerateDataTest {

	@Autowired
	private GenerateData generateData;

	@MockBean
	private GenerateData parseData;

	@MockBean
	private Data result;

	@MockBean
	private Data dataResult;

	@Test
	public void givenRawData_whenGenerateData_thenReturnDataObjectWithThreeLists() throws IOException {
		// GIVEN
		String rawData = "{\"persons\": [{ \"firstName\":\"fname\", \"lastName\":\"lname\", \"address\":\"street\", \"city\":\"city\", \"zip\":\"123\", \"phone\":\"phone\", \"email\":\"email\" }],"
				+ " \"firestations\":[ { \"address\":\"street\", \"station\":\"1\" }],"
				+ "  \"medicalrecords\": [{ \"firstName\":\"fname\", \"lastName\":\"lname\", \"birthdate\":\"birthdate\", \"medications\":[\"medications1\", \"medications2\"], \"allergies\":[\"allergies1\", \"allergies2\"] } ]}";

		when(generateData.readDataSourceFile()).thenReturn(rawData);

		// WHEN
		Data result = generateData.generateData();

		// THEN
		verify(generateData, Mockito.times(1)).readDataSourceFile();
		verify(generateData, Mockito.times(1)).parseData();
		verify(generateData, Mockito.times(1)).parsePersonsData();
		verify(generateData, Mockito.times(1)).parseFirestationsData();
		verify(generateData, Mockito.times(1)).parseMedicalRecordsData();

		Address addressResult = new Address("street", 123, "city");
		List<String> medicationsResult = new ArrayList<>();
		medicationsResult.add("medications1");
		medicationsResult.add("medications2");
		List<String> allergiesResult = new ArrayList<>();
		allergiesResult.add("allergies1");
		allergiesResult.add("allergies2");
		MedicalRecords medicalRecordsResult = new MedicalRecords(medicationsResult, allergiesResult);
		Person personResult = new Person("fname", "lname", "birthdate", addressResult, "phone", "email",
				medicalRecordsResult);
		List<Person> personsListResult = new ArrayList<>();
		personsListResult.add(personResult);
		List<Firestation> firestationsListResult = new ArrayList<>();
		List<Address> addressListResult = new ArrayList<>();
		addressListResult.add(addressResult);
		firestationsListResult.add(new Firestation(1, addressListResult));
		List<Household> householdsListResult = new ArrayList<>();
		Household household = new Household(addressResult, personsListResult);
		householdsListResult.add(household);
		Data dataResult = new Data(householdsListResult, firestationsListResult, personsListResult);
		assertEquals(dataResult, result);
	}

}
