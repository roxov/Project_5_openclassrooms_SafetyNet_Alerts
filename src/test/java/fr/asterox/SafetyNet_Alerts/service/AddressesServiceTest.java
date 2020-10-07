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

import fr.asterox.SafetyNet_Alerts.consumer.FirestationDAO;
import fr.asterox.SafetyNet_Alerts.consumer.HouseholdDAO;
import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.Firestation;
import fr.asterox.SafetyNet_Alerts.model.Household;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
import fr.asterox.SafetyNet_Alerts.model.Person;
import fr.asterox.SafetyNet_Alerts.web.DTO.ChildDTO;
import fr.asterox.SafetyNet_Alerts.web.DTO.PeopleAndStationNumberOfAddressDTO;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AddressesServiceTest {

	@Autowired
	private AddressesService addressesService;

	MedicalRecords medicalRecords;

	@MockBean
	private HouseholdDAO householdDAO;

	@MockBean
	private FirestationDAO firestationDAO;

	@BeforeEach
	private void setUpPerTest() {
		List<String> medicationsAndAllergies = new ArrayList<>();
		medicationsAndAllergies.add("medicationsAndAllergies");
		medicalRecords = new MedicalRecords(medicationsAndAllergies, medicationsAndAllergies);
	}

	@Test
	public void givenAChildAndAnAdultInHousehold_whenGetPersonsLivingInChildHousehold_thenReturnChildAndAdultInfo() {
		// GIVEN
		Address address1 = new Address("street1", 123, "city1");
		List<Person> personsList = new ArrayList<>();
		Person child1 = new Person("childname1", "lname1", "01/01/2012", address1, "phone1", "email1", medicalRecords);
		Person adult1 = new Person("adultname1", "lname1", "01/01/1980", address1, "phone2", "email2", medicalRecords);
		personsList.add(child1);
		personsList.add(adult1);
		Household household1 = new Household(address1, personsList);
		List<Household> householdsList = new ArrayList<>();
		householdsList.add(household1);

		when(householdDAO.getHouseholdsList()).thenReturn(householdsList);

		// WHEN
		List<ChildDTO> result = addressesService.getPersonsLivingInChildHousehold("street1");

		// THEN
		verify(householdDAO, Mockito.times(1)).getHouseholdsList();

		int child1Age = LocalDate.now().getYear() - 2012;
		int adult1Age = LocalDate.now().getYear() - 1980;
		assertEquals("childname1", result.get(0).getFirstName());
		assertEquals("lname1", result.get(0).getLastName());
		assertEquals(child1Age, result.get(0).getAge());
		assertEquals("adultname1", result.get(1).getFirstName());
		assertEquals("lname1", result.get(1).getLastName());
		assertEquals(adult1Age, result.get(1).getAge());

	}

	@Test
	public void givenNoChildInHousehold_whenGetPersonsLivingInChildHousehold_thenReturnEmptyObject() {
		// GIVEN
		Address address1 = new Address("street1", 123, "city1");
		List<Person> personsList = new ArrayList<>();
		Person adult1 = new Person("adultdname1", "lname1", "01/01/1980", address1, "phone1", "email1", medicalRecords);
		personsList.add(adult1);
		Household household1 = new Household(address1, personsList);
		List<Household> householdsList = new ArrayList<>();
		householdsList.add(household1);

		when(householdDAO.getHouseholdsList()).thenReturn(householdsList);

		// WHEN
		List<ChildDTO> result = addressesService.getPersonsLivingInChildHousehold("street1");

		// THEN
		verify(householdDAO, Mockito.times(1)).getHouseholdsList();
		assertEquals(null, result);
	}

	@Test
	public void givenTwoHouseholdsWithDifferentStreets_whenGetInhabitantsAndStationOfAddress_thenReturnTheCorrespondingInhabitant() {
		// GIVEN

		Address address1 = new Address("street1", 123, "city1");
		List<Person> personsList1 = new ArrayList<>();
		Person person1 = new Person("fname1", "lname1", "01/01/1980", address1, "phone1", "email1", medicalRecords);
		personsList1.add(person1);
		Household household1 = new Household(address1, personsList1);
		Address address2 = new Address("street2", 123, "city2");
		List<Person> personsList2 = new ArrayList<>();
		Person person2 = new Person("fname2", "lname2", "01/01/1990", address2, "phone2", "email2", medicalRecords);
		personsList1.add(person2);
		Household household2 = new Household(address2, personsList2);
		List<Household> householdsList = new ArrayList<>();
		householdsList.add(household1);
		householdsList.add(household2);

		List<Firestation> firestationsList = new ArrayList<>();
		List<Address> addressesList = new ArrayList<>();
		addressesList.add(address1);
		Firestation firestation1 = new Firestation(1, addressesList);
		firestationsList.add(firestation1);

		when(householdDAO.getHouseholdsList()).thenReturn(householdsList);
		when(firestationDAO.getFirestationsList()).thenReturn(firestationsList);

		// WHEN
		PeopleAndStationNumberOfAddressDTO result = addressesService.getInhabitantsAndStationOfTheAddress("street1");

		// THEN
		verify(householdDAO, Mockito.times(1)).getHouseholdsList();
		verify(firestationDAO, Mockito.times(1)).getFirestationsList();
		Integer adult1Age = LocalDate.now().getYear() - 1980;
		assertEquals("lname1", result.getPeopleOfAddress().get(0).getLastName());
		assertEquals("phone1", result.getPeopleOfAddress().get(0).getPhone());
		assertEquals(adult1Age, result.getPeopleOfAddress().get(0).getAge());
		assertEquals(medicalRecords, result.getPeopleOfAddress().get(0).getMedicalRecords());
		assertEquals(1, result.getStationNumber());
	}

	@Test
	public void givenTwoFirestations_whenGetInhabitantsAndStationOfAddress_thenReturnTheCorrespondingStationNumber() {
		// GIVEN
		Address address1 = new Address("street1", 123, "city1");
		List<Person> personsList1 = new ArrayList<>();
		Person person1 = new Person("fname1", "lname1", "01/01/1980", address1, "phone1", "email1", medicalRecords);
		personsList1.add(person1);
		Household household1 = new Household(address1, personsList1);
		List<Household> householdsList = new ArrayList<>();
		householdsList.add(household1);

		Address address2 = new Address("street2", 123, "city2");
		List<Firestation> firestationsList = new ArrayList<>();
		List<Address> addressesList = new ArrayList<>();
		addressesList.add(address1);
		Firestation firestation1 = new Firestation(1, addressesList);
		firestationsList.add(firestation1);
		List<Address> addressesList2 = new ArrayList<>();
		addressesList2.add(address2);
		Firestation firestation2 = new Firestation(2, addressesList2);
		firestationsList.add(firestation2);

		when(householdDAO.getHouseholdsList()).thenReturn(householdsList);
		when(firestationDAO.getFirestationsList()).thenReturn(firestationsList);

		// WHEN
		PeopleAndStationNumberOfAddressDTO result = addressesService.getInhabitantsAndStationOfTheAddress("street1");

		// THEN
		verify(householdDAO, Mockito.times(1)).getHouseholdsList();
		verify(firestationDAO, Mockito.times(1)).getFirestationsList();
		Integer adult1Age = LocalDate.now().getYear() - 1980;
		assertEquals("lname1", result.getPeopleOfAddress().get(0).getLastName());
		assertEquals("phone1", result.getPeopleOfAddress().get(0).getPhone());
		assertEquals(adult1Age, result.getPeopleOfAddress().get(0).getAge());
		assertEquals(medicalRecords, result.getPeopleOfAddress().get(0).getMedicalRecords());
		assertEquals(1, result.getStationNumber());
	}
}
