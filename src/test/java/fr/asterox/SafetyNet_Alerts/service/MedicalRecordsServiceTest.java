package fr.asterox.SafetyNet_Alerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
import fr.asterox.SafetyNet_Alerts.model.Person;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MedicalRecordsServiceTest {

	@Autowired
	private MedicalRecordsService medicalRecordsService;

	@MockBean
	private Data data;

	private List<Person> personsList;
	private Address address;
	private MedicalRecords medicalRecords;

	@BeforeEach
	private void setUpPerTest() {
		address = new Address("street", 123, "city");
		List<String> medications = new ArrayList<>();
		medications.add("medications1");
		List<String> allergies = new ArrayList<>();
		allergies.add("allergies1");
		medicalRecords = new MedicalRecords(medications, allergies);

		personsList = new ArrayList<>();
		Person person1 = new Person("fname1", "lname1", "01/01/1980", address, "phone1", "email1", medicalRecords);
		personsList.add(person1);
	}

	@Test
	public void givenListOfOnePersonWithNoMedicalRecords_whenAddMedicalRecords_thenReturnThePersonWithMedicalRecords() {
		// GIVEN
		List<Person> personsWithoutMedicalsRecordsList = new ArrayList<>();
		Person person = new Person("fname", "lname", "01/01/1980", address, "phone1", "email1", null);
		personsWithoutMedicalsRecordsList.add(person);
		when(data.getPersonsList()).thenReturn(personsWithoutMedicalsRecordsList);

		// WHEN
		medicalRecordsService.addMedicalRecords("fname", "lname", medicalRecords);

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		List<Person> result = data.getPersonsList();
		assertEquals("fname", result.get(0).getFirstName());
		assertEquals("lname", result.get(0).getLastName());
		assertEquals("01/01/1980", result.get(0).getBirthdate());
		assertEquals(address, result.get(0).getAddress());
		assertEquals("phone1", result.get(0).getPhone());
		assertEquals("email1", result.get(0).getEmail());
		assertEquals(medicalRecords, result.get(0).getMedicalRecords());
	}

	@Test
	public void givenListOfOnePersonWithExistingMedicalRecords_whenAddMedicalRecords_thenReturnTheSameList() {
		// GIVEN
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		medicalRecordsService.addMedicalRecords("fname1", "lname1", medicalRecords);

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		assertEquals(personsList, data.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenAddMedicalRecordsWithMissingInformation_thenReturnTheSameList() {
		// GIVEN
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		medicalRecordsService.addMedicalRecords("fname1", "lname1", medicalRecords);

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		assertEquals(personsList, data.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenAddMedicalRecordsToInexistentPerson_thenReturnTheSameList() {
		// GIVEN
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		medicalRecordsService.addMedicalRecords("fname2", "lname1", medicalRecords);

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		assertEquals(personsList, data.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenUpdateMedicalRecords_thenReturnActualizedList() {
		// GIVEN
		List<String> medications2 = new ArrayList<>();
		medications2.add("medications2");
		List<String> allergies2 = new ArrayList<>();
		allergies2.add("allergies2");
		MedicalRecords medicalRecords2 = new MedicalRecords(medications2, allergies2);
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		medicalRecordsService.updateMedicalRecords("fname1", "lname1", medicalRecords2);

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		List<Person> resultList = new ArrayList<>();
		List<String> resultMedications = new ArrayList<>();
		resultMedications.add("medications2");
		resultMedications.add("medications1");
		List<String> resultAllergies = new ArrayList<>();
		resultAllergies.add("allergies2");
		resultAllergies.add("allergies1");
		MedicalRecords resultMedicalRecords = new MedicalRecords(resultMedications, resultAllergies);
		resultList.add(new Person("fname1", "lname1", "01/01/1980", address, "phone1", "email1", resultMedicalRecords));

		assertEquals(resultList, data.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenUpdateMedicalRecordsForInexistentPerson_thenReturnTheSameList() {
		// GIVEN
		List<String> medications2 = new ArrayList<>();
		medications2.add("medications2");
		List<String> allergies2 = new ArrayList<>();
		allergies2.add("allergies2");
		MedicalRecords medicalRecords2 = new MedicalRecords(medications2, allergies2);
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		medicalRecordsService.updateMedicalRecords("fname2", "lname1", medicalRecords2);

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		assertEquals(personsList, data.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenGiveMissingInformationToUpdate_thenReturnTheSameList() {
		// GIVEN
		List<String> medications2 = new ArrayList<>();
		medications2.add("medications2");
		List<String> allergies2 = new ArrayList<>();
		allergies2.add("allergies2");
		MedicalRecords medicalRecords2 = new MedicalRecords(medications2, allergies2);
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		medicalRecordsService.updateMedicalRecords(null, "lname1", medicalRecords2);

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		assertEquals(personsList, data.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenDeleteMedicalRecordsOfThePerson_thenReturnThePersonWithoutMedicalRecords() {
		// GIVEN
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		medicalRecordsService.deleteMedicalRecords("fname1", "lname1");

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		List<Person> resultList = new ArrayList<>();
		resultList.add(new Person("fname1", "lname1", "01/01/1980", address, "phone1", "email1", null));
		assertEquals(resultList, data.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenDeleteMedicalRecordsOfInexistentPerson_thenReturnTheSameList() {
		// GIVEN
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		medicalRecordsService.deleteMedicalRecords("fname2", "lname1");

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		assertEquals(personsList, data.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenGiveMissingInformationToDelete_thenReturnTheSameList() {
		// GIVEN
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		medicalRecordsService.deleteMedicalRecords(null, "lname1");

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		assertEquals(personsList, data.getPersonsList());
	}

}
