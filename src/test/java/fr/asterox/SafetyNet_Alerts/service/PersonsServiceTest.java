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

import fr.asterox.SafetyNet_Alerts.consumer.PersonDAO;
import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
import fr.asterox.SafetyNet_Alerts.model.Person;
import fr.asterox.SafetyNet_Alerts.web.DTO.PersonInfoDTO;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonsServiceTest {

	@Autowired
	private static PersonsService personsService;

	@MockBean
	private static PersonDAO personDAO;

	private static List<Person> personsList;
	private static Address address;
	private static MedicalRecords medicalRecords;

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
		when(personDAO.getPersonsList()).thenReturn(personsList);

		// WHEN
		List<PersonInfoDTO> result = personsService.getInhabitantsInfo("fname1", "lname1");

		// THEN
		verify(personDAO, Mockito.times(1)).getPersonsList();
		List<PersonInfoDTO> personsListTest = new ArrayList<>();
		int age = LocalDate.now().getYear() - 1980;
		personsListTest.add(new PersonInfoDTO("lname1", address, age, "email1", medicalRecords));
		personsListTest.add(new PersonInfoDTO("lname1", address, age, "email2", medicalRecords));
		assertEquals(personsListTest, result);
	}

	@Test
	public void givenAListOfTwoPersonsWithDifferentLastName_whenGetInhabitantsInfo_thenReturnTheListOfOnePerson() {
		// GIVEN
		Person person2 = new Person("fname2", "lname2", "01/01/1980", address, "phone2", "email2", medicalRecords);
		personsList.add(person2);
		when(personDAO.getPersonsList()).thenReturn(personsList);

		// WHEN
		List<PersonInfoDTO> result = personsService.getInhabitantsInfo("fname2", "lname2");

		// THEN
		verify(personDAO, Mockito.times(1)).getPersonsList();
		List<PersonInfoDTO> personsListTest = new ArrayList<>();
		int age = LocalDate.now().getYear() - 1980;
		personsListTest.add(new PersonInfoDTO("lname2", address, age, "email2", medicalRecords));
		assertEquals(personsListTest, result);
	}

	@Test
	public void givenListOfTwoPersonsFromDifferentCities_whenGetPersonsListOfCity_thenReturnAListOfOnePerson() {
		// GIVEN
		Address address2 = new Address("street", 123, "city2");
		Person person2 = new Person("fname2", "lname2", "01/01/1980", address2, "phone2", "email2", medicalRecords);
		personsList.add(person2);
		when(personDAO.getPersonsList()).thenReturn(personsList);

		// WHEN
		List<String> result = personsService.getEmailsListOfCity("city2");

		// THEN
		verify(personDAO, Mockito.times(1)).getPersonsList();
		List<String> emailsListResult = new ArrayList<>();
		emailsListResult.add("email2");
		assertEquals(emailsListResult, result);
	}
}
