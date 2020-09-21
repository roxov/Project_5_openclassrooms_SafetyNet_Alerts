package fr.asterox.SafetyNet_Alerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import fr.asterox.SafetyNet_Alerts.consumer.FirestationDAO;
import fr.asterox.SafetyNet_Alerts.consumer.HouseholdDAO;
import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.Firestation;
import fr.asterox.SafetyNet_Alerts.model.Household;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
import fr.asterox.SafetyNet_Alerts.model.Person;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AddressesServiceTest {

	private static AddressesService addressesService;
	private static LocalDateTime birthdate;

	@Mock
	private static HouseholdDAO householdDAO;
	private static FirestationDAO firestationDAO;

	@Test
	public void givenTwoHouseholdsWithDifferentStreets_whenGetInhabitantsAndStationOfAddress_thenReturnTheCorrespondingInhabitant() {
		// GIVEN
		birthdate = LocalDateTime.now();
		Address address1 = new Address("street1", 123, "city1");
		List<Person> personsList1 = new ArrayList<>();
		Person person1 = new Person("fname1", "lname1", birthdate, address1, "phone1", "email1", new MedicalRecords());
		personsList1.add(person1);
		Household household1 = new Household(address1, personsList1);
		Address address2 = new Address("street2", 123, "city2");
		List<Person> personsList2 = new ArrayList<>();
		Person person2 = new Person("fname2", "lname2", birthdate, address2, "phone2", "email2", new MedicalRecords());
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
		Object[] result = addressesService.getInhabitantsAndStationOfTheAddress("street1");

		// THEN
		verify(householdDAO, Mockito.times(1)).getHouseholdsList();
		verify(firestationDAO, Mockito.times(1)).getFirestationsList();
		List<Person> inhabitantsList = new ArrayList<>();
		inhabitantsList.add(person1);
		Integer stationNumber = 1;
		Object[] objectResult = new Object[] { inhabitantsList, stationNumber };
		assertEquals(objectResult, result);
	}

	@Test
	public void givenTwoFirestations_whenGetInhabitantsAndStationOfAddress_thenReturnTheCorrespondingStationNumber() {
		// GIVEN
		birthdate = LocalDateTime.now();
		Address address1 = new Address("street1", 123, "city1");
		List<Person> personsList1 = new ArrayList<>();
		Person person1 = new Person("fname1", "lname1", birthdate, address1, "phone1", "email1", new MedicalRecords());
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
		Object[] result = addressesService.getInhabitantsAndStationOfTheAddress("street1");

		// THEN
		verify(householdDAO, Mockito.times(1)).getHouseholdsList();
		verify(firestationDAO, Mockito.times(1)).getFirestationsList();
		List<Person> inhabitantsList = new ArrayList<>();
		inhabitantsList.add(person1);
		Integer stationNumber = 1;
		Object[] objectResult = new Object[] { inhabitantsList, stationNumber };
		assertEquals(objectResult, result);
	}
}
