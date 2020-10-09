package fr.asterox.SafetyNet_Alerts.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.asterox.SafetyNet_Alerts.consumer.PersonDAO;
import fr.asterox.SafetyNet_Alerts.integration.config.DataTestConfig;
import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
import fr.asterox.SafetyNet_Alerts.model.Person;
import fr.asterox.SafetyNet_Alerts.service.PersonsService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonsServiceIT {

	@Autowired
	private PersonsService personsService;

	@Autowired
	private PersonDAO personDAO;

	private List<Person> personsList;
	Person person1;
	private Address address;
	private MedicalRecords medicalRecords;
	private static DataTestConfig dataTestConfig = new DataTestConfig();

	@BeforeAll
	private static void setUp() throws Exception {
		// PersonDAO.data = dataTestConfig;
	}

	@BeforeEach
	private void setUpPerTest() {
		address = new Address("street", 123, "city");
		List<String> medicationsAndAllergies = new ArrayList<>();
		medicationsAndAllergies.add("medicationsAndAllergies");
		medicalRecords = new MedicalRecords(medicationsAndAllergies, medicationsAndAllergies);

		personsList = new ArrayList<>();
		person1 = new Person("fname1", "lname1", "01/01/1980", address, "phone1", "email1", medicalRecords);
		personsList.add(person1);
	}

	@Test
	public void givenListOfOnePerson_whenAddPerson_thenListOfTwoPersons() {
		// GIVEN
		Address address2 = new Address("street", 123, "city2");
		Person person2 = new Person("fname2", "lname2", "01/01/1980", address2, "phone2", "email2", medicalRecords);

		// LISTE TEST
		when(personDAO.getPersonsList()).thenReturn(personsList);

		// WHEN
		personsService.addPerson(person2);

		// THEN
		verify(personDAO, Mockito.times(1)).getPersonsList();
		List<Person> resultList = new ArrayList<>();
		resultList.add(person1);
		resultList.add(person2);
		// assertEquals(resultList, personsService.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenUpdatePerson_thenModifyThisPersonInTheList() {
		// GIVEN
		Address address2 = new Address("street", 123, "city2");
		Person updatedPerson = new Person("fname1", "lname1", "01/01/1980", address2, "phone2", "email2",
				medicalRecords);

		// LISTE TEST
		when(personDAO.getPersonsList()).thenReturn(personsList);

		// WHEN
		personsService.updatePerson(updatedPerson);

		// THEN
		verify(personDAO, Mockito.times(1)).getPersonsList();
		List<Person> resultList = new ArrayList<>();
		resultList.add(updatedPerson);
		// assertEquals(resultList, personsService.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenDeleteThePerson_thenReturnAnEmptyList() {

		// TODO : LISTE TEST
		when(personDAO.getPersonsList()).thenReturn(personsList);

		// WHEN
		personsService.deletePerson("fname1", "lname1");

		// THEN
		verify(personDAO, Mockito.times(1)).getPersonsList();
		assertEquals(null, personsService.getPersonsList());
	}

}
