package fr.asterox.SafetyNet_Alerts.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.asterox.SafetyNet_Alerts.consumer.PersonDAO;
import fr.asterox.SafetyNet_Alerts.integration.config.DataTestConfig;
import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
import fr.asterox.SafetyNet_Alerts.model.Person;
import fr.asterox.SafetyNet_Alerts.web.PersonsController;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonsControllerIT {

	@Autowired
	private PersonsController personsController;

	@Autowired
	private PersonDAO personDAO;

	private MedicalRecords medicalRecords;
	private static DataTestConfig dataTestConfig = new DataTestConfig();

	@BeforeAll
	private static void setUp() throws Exception {
		// PersonDAO.data = dataTestConfig;
	}

	@BeforeEach
	private void setUpPerTest() {

		// TODO : renvoyer vers les données contenues dans dataTestConfig au lieu de
		// generateData.
		List<String> medications = new ArrayList<>();
		medications.add("medications1");
		medications.add("medications2");
		List<String> allergies = new ArrayList<>();
		allergies.add("allergies1");
		allergies.add("allergies2");
		medicalRecords = new MedicalRecords(medications, allergies);
	}

	@Test
	public void givenListOfOnePerson_whenAddPerson_thenReturnAListOfTwoPersons() {
		// GIVEN
		Address address2 = new Address("street2", 123, "city2");
		Person person2 = new Person("fname2", "lname2", "01/01/1980", address2, "phone2", "email2", medicalRecords);

		// WHEN
		personsController.addPerson(person2);

		// THEN
		Address address1 = new Address("street1", 123, "Culver");
		Person person1 = new Person("adult1", "lname1", "03/06/1980", address1, "phone1", "email1", medicalRecords);
		List<Person> resultList = new ArrayList<>();
		resultList.add(person1);
		resultList.add(person2);
		assertEquals(resultList, personDAO.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenUpdatePerson_thenModifyThisPersonInTheList() {
		// GIVEN
		Address address2 = new Address("street2", 123, "city2");
		Person updatedPerson = new Person("adult1", "lname1", "01/01/1980", address2, "phone2", "email2",
				medicalRecords);

		// WHEN
		personsController.updatePerson(updatedPerson);

		// THEN
		List<Person> resultList = new ArrayList<>();
		resultList.add(updatedPerson);
		assertEquals(resultList, personDAO.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenDeleteThePerson_thenReturnAnEmptyList() {

		// WHEN
		personsController.deletePerson("adult1", "lname1");

		// THEN
		assertEquals(null, personDAO.getPersonsList());
	}

}
