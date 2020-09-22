package fr.asterox.SafetyNet_Alerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import fr.asterox.SafetyNet_Alerts.consumer.PersonDAO;
import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
import fr.asterox.SafetyNet_Alerts.model.Person;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonsServiceTest {

	private static PersonsService personsService;
	private static List<Person> personsList;
	private static Address address;
	private static MedicalRecords medicalRecords;

	@Mock
	private static PersonDAO personDAO;

	@BeforeEach
	private void setUpPerTest() {
		address = new Address("street", 123, "city");
		List<String> medications = new ArrayList<>();
		medications.add("med1");
		medications.add("med2");
		List<String> allergies = new ArrayList<>();
		allergies.add("all1");
		allergies.add("all2");
		medicalRecords = new MedicalRecords(medications, allergies);

		personsList = new ArrayList<>();
		Person person1 = new Person("fname1", "lname1", "01/01/1990", address, "phone1", "email1", medicalRecords);
		personsList.add(person1);
	}

	@Test
	public void givenAListOfPersonsWithSameLastName_whenGetInhabitantsInfo_thenReturnTheListOfPersons() {
		// GIVEN
		Person person2 = new Person("fname2", "lname1", "01/01/1990", address, "phone2", "email2", medicalRecords);
		personsList.add(person2);
		when(personDAO.getPersonsList()).thenReturn(personsList);

		// WHEN
		List<Person> result = personsService.getInhabitantsInfo("fname1", "lname1");

		// THEN
		verify(personDAO, Mockito.times(1)).getPersonsList();
		assertEquals(personsList, result);
	}

	@Test
	public void givenAListOfTwoPersonsWithDifferentLastName_whenGetInhabitantsInfo_thenReturnTheListOfOnePerson() {
		// GIVEN
		Person person2 = new Person("fname2", "lname2", "01/01/1990", address, "phone2", "email2", medicalRecords);
		personsList.add(person2);
		when(personDAO.getPersonsList()).thenReturn(personsList);

		// WHEN
		List<Person> result = personsService.getInhabitantsInfo("fname2", "lname2");

		// THEN
		verify(personDAO, Mockito.times(1)).getPersonsList();
		List<Person> personsListResult = new ArrayList<>();
		personsListResult.add(person2);
		assertEquals(personsListResult, result);
	}

	@Test
	public void givenListOfTwoPersonsFromDifferentCities_whenGetPersonsListOfCity_thenReturnAListOfOnePerson() {
		// GIVEN
		Address address2 = new Address("street", 123, "city2");
		Person person2 = new Person("fname2", "lname2", "01/01/1990", address2, "phone2", "email2", medicalRecords);
		personsList.add(person2);
		when(personDAO.getPersonsList()).thenReturn(personsList);

		// WHEN
		List<Person> result = personsService.getPersonsListOfCity("city2");

		// THEN
		verify(personDAO, Mockito.times(1)).getPersonsList();
		List<Person> personsListResult = new ArrayList<>();
		personsListResult.add(person2);
		assertEquals(personsListResult, result);
	}

}
