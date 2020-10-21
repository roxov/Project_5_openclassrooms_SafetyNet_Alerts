package fr.asterox.SafetyNet_Alerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import fr.asterox.SafetyNet_Alerts.web.DTO.PersonInfoDTO;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonsServiceTest {

	@Autowired
	private PersonsService personsService;

	@MockBean
	private Data data;

	private List<Person> personsList;
	private Address address;
	private MedicalRecords medicalRecords;

	@BeforeEach
	private void setUpPerTest() {
		address = new Address("street", 123, "city");
		List<String> medicationsAndAllergies = new ArrayList<>();
		medicationsAndAllergies.add("medicationsAndAllergies");
		medicalRecords = new MedicalRecords(medicationsAndAllergies, medicationsAndAllergies);

		personsList = new ArrayList<>();
		Person person1 = new Person("fname1", "lname1", "01/01/1980", address, "phone1", "email1", medicalRecords);
		personsList.add(person1);
	}

	@Test
	public void givenAListOfPersonsWithSameLastName_whenGetInhabitantsInfo_thenReturnTheListOfPersons() {
		// GIVEN
		Person person2 = new Person("fname2", "lname1", "01/01/1980", address, "phone2", "email2", medicalRecords);
		personsList.add(person2);
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		List<PersonInfoDTO> result = personsService.getInhabitantsInfo("fname1", "lname1");

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		int age = LocalDate.now().getYear() - 1980;
		assertEquals("lname1", result.get(0).getLastName());
		assertEquals(address, result.get(0).getAddress());
		assertEquals(age, result.get(0).getAge());
		assertEquals("email1", result.get(0).getEmail());
		assertEquals(medicalRecords, result.get(0).getMedicalRecords());
		assertEquals("lname1", result.get(1).getLastName());
		assertEquals(address, result.get(1).getAddress());
		assertEquals(age, result.get(1).getAge());
		assertEquals("email2", result.get(1).getEmail());
		assertEquals(medicalRecords, result.get(1).getMedicalRecords());
	}

	@Test
	public void givenAListOfTwoPersonsWithDifferentLastName_whenGetInhabitantsInfo_thenReturnTheListOfOnePerson() {
		// GIVEN
		Person person2 = new Person("fname2", "lname2", "01/01/1980", address, "phone2", "email2", medicalRecords);
		personsList.add(person2);
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		List<PersonInfoDTO> result = personsService.getInhabitantsInfo("fname2", "lname2");

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		int age = LocalDate.now().getYear() - 1980;
		assertEquals("lname2", result.get(0).getLastName());
		assertEquals(address, result.get(0).getAddress());
		assertEquals(age, result.get(0).getAge());
		assertEquals("email2", result.get(0).getEmail());
		assertEquals(medicalRecords, result.get(0).getMedicalRecords());
	}

	@Test
	public void givenMissingInformation_whenGetInhabitantsInfo_thenReturnEmptyList() {
		// WHEN
		List<PersonInfoDTO> result = personsService.getInhabitantsInfo(null, "lname1");

		// THEN
		assertEquals(null, result);
	}

	@Test
	public void givenAListOfOnePerson_whenGetInhabitantsInfoWithNoExistentPerson_thenReturnEmptyList() {
		// GIVEN
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		List<PersonInfoDTO> result = personsService.getInhabitantsInfo("fname", "lname");

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		List<PersonInfoDTO> emptyList = new ArrayList<>();
		assertEquals(emptyList, result);
	}

	@Test
	public void givenListOfTwoPersonsFromDifferentCities_whenGetEmailsListOfCity_thenReturnEmailOfOnePerson() {
		// GIVEN
		Address address2 = new Address("street", 123, "city2");
		Person person2 = new Person("fname2", "lname2", "01/01/1980", address2, "phone2", "email2", medicalRecords);
		personsList.add(person2);
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		List<String> result = personsService.getEmailsListOfCity("city2");

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		List<String> emailsListResult = new ArrayList<>();
		emailsListResult.add("email2");
		assertEquals(emailsListResult, result);
	}

	@Test
	public void givenListOfOnePerson_whenAddPerson_thenReturnListOfTwoPersons() {
		// GIVEN
		Address address2 = new Address("street", 123, "city2");
		Person person2 = new Person("fname2", "lname2", "01/01/1980", address2, "phone2", "email2", medicalRecords);
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		personsService.addPerson(person2);

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		personsList.add(person2);
		assertEquals(personsList, data.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenAddPersonWithMissingField_thenReturnSameList() {
		// GIVEN
		Address address2 = new Address("street", 123, "city2");
		Person person2 = new Person("fname2", "lname2", null, address2, "phone2", "email2", medicalRecords);
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		personsService.addPerson(person2);

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		assertEquals(personsList, data.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenUpdatePerson_thenReturnActualizedList() {
		// GIVEN
		Person updatedPerson = new Person("fname1", "lname1", "01/01/1980", address, "phone2", "email2",
				medicalRecords);
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		personsService.updatePerson(updatedPerson);

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		List<Person> resultList = new ArrayList<>();
		resultList.add(updatedPerson);
		assertEquals(resultList, data.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenUpdateInexistentPerson_thenReturnSameList() {
		// GIVEN
		Person updatedPerson = new Person("fname2", "lname1", "01/01/1980", address, "phone2", "email2",
				medicalRecords);
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		personsService.updatePerson(updatedPerson);

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		assertEquals(personsList, data.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenDeleteThePerson_thenReturnAnEmptyList() {
		// GIVEN
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		personsService.deletePerson("fname1", "lname1");

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		List<Person> resultList = new ArrayList<>();
		assertEquals(resultList, data.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenDeleteNullPerson_thenReturnSameList() {
		// GIVEN
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		personsService.deletePerson(null, "lname1");

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		assertEquals(personsList, data.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenDeleteInexistentPerson_thenReturnSameList() {
		// GIVEN
		when(data.getPersonsList()).thenReturn(personsList);

		// WHEN
		personsService.deletePerson("fname2", "lname1");

		// THEN
		verify(data, Mockito.times(1)).getPersonsList();
		assertEquals(personsList, data.getPersonsList());
	}

}
