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
import fr.asterox.SafetyNet_Alerts.web.MedicalRecordsController;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MedicalRecordsControllerIT {

	@Autowired
	private MedicalRecordsController medicalRecordsController;

	@Autowired
	private PersonDAO personDAO;

	private MedicalRecords newMedicalRecords;
	private Address address1;
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
		newMedicalRecords = new MedicalRecords(medications, allergies);

		address1 = new Address("street1", 123, "Culver");
	}

	@Test
	public void givenListOfOnePerson_whenAddMedicalRecordsToNonExistentPerson_thenReturnTheListWithNewPersonWithMedicalRecords() {
		// WHEN
		medicalRecordsController.addMedicalRecords("fname2", "lname2", "01/01/1980", newMedicalRecords);

		// THEN
		List<String> medications = new ArrayList<>();
		medications.add("medications1");
		medications.add("medications2");
		List<String> allergies = new ArrayList<>();
		allergies.add("allergies1");
		allergies.add("allergies2");
		MedicalRecords medicalRecords = new MedicalRecords(medications, allergies);

		Person person1 = new Person("adult1", "lname1", "03/06/1980", address1, "phone1", "email1", medicalRecords);
		List<Person> resultList = new ArrayList<>();
		resultList.add(person1);

		Person person2 = new Person("fname2", "lname2", "01/01/1980", null, null, null, newMedicalRecords);
		resultList.add(person2);

		assertEquals(resultList, personDAO.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenUpdateMedicalRecordsOfThisPerson_thenModifyThisPersonInTheList() {

		// WHEN
		medicalRecordsController.updateMedicalRecords("adult1", "lname1", newMedicalRecords);

		// THEN
		Person person1 = new Person("adult1", "lname1", "03/06/1980", address1, "phone1", "email1", newMedicalRecords);
		List<Person> resultList = new ArrayList<>();
		resultList.add(person1);
		assertEquals(resultList, personDAO.getPersonsList());
	}

	@Test
	public void givenListOfOnePerson_whenDeleteThePerson_thenReturnAnEmptyList() {

		// WHEN
		medicalRecordsController.deleteMedicalRecords("adult1", "lname1");

		// THEN
		Person person1 = new Person("adult1", "lname1", "03/06/1980", address1, "phone1", "email1", null);
		List<Person> resultList = new ArrayList<>();
		resultList.add(person1);
		assertEquals(resultList, personDAO.getPersonsList());
	}

}
