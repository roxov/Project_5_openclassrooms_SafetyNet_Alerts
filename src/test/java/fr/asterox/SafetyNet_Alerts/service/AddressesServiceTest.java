
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

	@MockBean
	private Data data;

	private MedicalRecords medicalRecords;
	private Address address;
	private List<Address> addressesList;
	private Person adult1;
	private int adultAge;
	private Person child1;
	private int childAge;
	private List<Person> personsList;
	private List<Firestation> firestationsList;
	private List<Household> householdsList;

	@BeforeEach
	public void setUpPerTest() {
		List<String> medicationsAndAllergies = new ArrayList<>();
		medicationsAndAllergies.add("medicationsAndAllergies");
		medicalRecords = new MedicalRecords(medicationsAndAllergies, medicationsAndAllergies);

		address = new Address("street", 123, "city");
		addressesList = new ArrayList<>();
		adult1 = new Person("adultname1", "lname1", "01/01/1980", address, "adPhone1", "adEmail1", medicalRecords);
		adultAge = LocalDate.now().getYear() - 1980;
		child1 = new Person("childname1", "lname1", "01/01/2010", address, "chPhone1", "chEmail1", medicalRecords);
		childAge = LocalDate.now().getYear() - 2010;
		personsList = new ArrayList<>();
		firestationsList = new ArrayList<>();
		householdsList = new ArrayList<>();
	}

	@Test
	public void givenAChildAndAnAdultInHousehold_whenGetPersonsLivingInChildHousehold_thenReturnChildAndAdultInfo() {
		// GIVEN
		personsList.add(child1);
		personsList.add(adult1);
		Household household1 = new Household(address, personsList);
		householdsList.add(household1);

		when(data.getHouseholdsList()).thenReturn(householdsList);

		// WHEN
		List<ChildDTO> result = addressesService.getPersonsLivingInChildHousehold("street");

		// THEN
		verify(data, Mockito.times(1)).getHouseholdsList();
		assertEquals("childname1", result.get(0).getFirstName());
		assertEquals("lname1", result.get(0).getLastName());
		assertEquals(childAge, result.get(0).getAge());
		assertEquals("adultname1", result.get(1).getFirstName());
		assertEquals("lname1", result.get(1).getLastName());
		assertEquals(adultAge, result.get(1).getAge());

	}

	@Test
	public void givenNoChildInHousehold_whenGetPersonsLivingInChildHousehold_thenReturnEmptyObject() {
		// GIVEN
		personsList.add(adult1);
		Household household1 = new Household(address, personsList);
		householdsList.add(household1);

		when(data.getHouseholdsList()).thenReturn(householdsList);

		// WHEN
		List<ChildDTO> result = addressesService.getPersonsLivingInChildHousehold("street");

		// THEN
		verify(data, Mockito.times(1)).getHouseholdsList();
		assertEquals(null, result);
	}

	@Test
	public void givenTwoHouseholdsWithDifferentStreets_whenGetInhabitantsAndStationOfAddress_thenReturnTheCorrespondingInhabitant() {
		// GIVEN
		personsList.add(adult1);
		Household household1 = new Household(address, personsList);
		Address address2 = new Address("street2", 123, "city2");
		List<Person> personsList2 = new ArrayList<>();
		Person person2 = new Person("adultname2", "lname2", "01/01/1980", address2, "adPhone2", "email2",
				medicalRecords);
		personsList2.add(person2);
		Household household2 = new Household(address2, personsList2);
		householdsList.add(household1);
		householdsList.add(household2);

		addressesList.add(address);
		Firestation firestation = new Firestation(1, addressesList);
		firestationsList.add(firestation);

		when(data.getHouseholdsList()).thenReturn(householdsList);
		when(data.getFirestationsList()).thenReturn(firestationsList);

		// WHEN
		PeopleAndStationNumberOfAddressDTO result = addressesService.getInhabitantsAndStationOfTheAddress("street");

		// THEN
		verify(data, Mockito.times(1)).getHouseholdsList();
		verify(data, Mockito.times(1)).getFirestationsList();
		assertEquals("lname1", result.getPeopleOfAddress().get(0).getLastName());
		assertEquals("adPhone1", result.getPeopleOfAddress().get(0).getPhone());
		assertEquals(adultAge, result.getPeopleOfAddress().get(0).getAge());
		assertEquals(medicalRecords, result.getPeopleOfAddress().get(0).getMedicalRecords());
		assertEquals(1, result.getStationNumber());
	}

	@Test
	public void givenTwoFirestations_whenGetInhabitantsAndStationOfAddress_thenReturnTheCorrespondingStationNumber() {
		// GIVEN
		personsList.add(adult1);
		Household household = new Household(address, personsList);
		householdsList.add(household);

		addressesList.add(address);
		Firestation firestation1 = new Firestation(1, addressesList);
		firestationsList.add(firestation1);
		List<Address> addressesList2 = new ArrayList<>();
		Address address2 = new Address("street2", 123, "city2");
		addressesList2.add(address2);
		Firestation firestation2 = new Firestation(2, addressesList2);
		firestationsList.add(firestation2);

		when(data.getHouseholdsList()).thenReturn(householdsList);
		when(data.getFirestationsList()).thenReturn(firestationsList);

		// WHEN
		PeopleAndStationNumberOfAddressDTO result = addressesService.getInhabitantsAndStationOfTheAddress("street");

		// THEN
		verify(data, Mockito.times(1)).getHouseholdsList();
		verify(data, Mockito.times(1)).getFirestationsList();
		assertEquals("lname1", result.getPeopleOfAddress().get(0).getLastName());
		assertEquals("adPhone1", result.getPeopleOfAddress().get(0).getPhone());
		assertEquals(adultAge, result.getPeopleOfAddress().get(0).getAge());
		assertEquals(medicalRecords, result.getPeopleOfAddress().get(0).getMedicalRecords());
		assertEquals(1, result.getStationNumber());
	}
}
