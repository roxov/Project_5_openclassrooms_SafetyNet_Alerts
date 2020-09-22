package fr.asterox.SafetyNet_Alerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class FirestationsServiceTest {

	private static FirestationsService firestationsService;

	@Mock
	private static FirestationDAO firestationDAO;
	@Mock
	private static HouseholdDAO householdDAO;

	@Test
	public void givenAChildAndAnAdultInHousehold_whenGetInfoOnPersonsServedByStation_thenInfoAndCountOfAdultsAndChildren() {
		// GIVEN
		Address address1 = new Address("street1", 123, "city1");
		List<Person> personsList = new ArrayList<>();
		Person person1 = new Person("childname1", "lname1", "01/01/2012", address1, "phone1", "email1",
				new MedicalRecords());
		Person person2 = new Person("adultname2", "lname2", "01/01/1980", address1, "phone2", "email2",
				new MedicalRecords());
		personsList.add(person1);
		personsList.add(person2);
		Household household1 = new Household(address1, personsList);
		List<Household> householdsList = new ArrayList<>();
		householdsList.add(household1);

		List<Firestation> firestationsList = new ArrayList<>();
		List<Address> addressesList = new ArrayList<>();
		addressesList.add(address1);
		Firestation firestation1 = new Firestation(1, addressesList);
		firestationsList.add(firestation1);

		when(householdDAO.getHouseholdsList()).thenReturn(householdsList);
		when(firestationDAO.getFirestationsList()).thenReturn(firestationsList);

		// WHEN
		Object[] result = firestationsService.getInfoOnPersonsServedByStation(1);

		// THEN
		verify(householdDAO, Mockito.times(1)).getHouseholdsList();
		Map<String, Integer> childrenAndAdultCount = new HashMap<>();
		childrenAndAdultCount.put("Number of adults", 1);
		childrenAndAdultCount.put("Number of children", 1);
		Object[] objectResult = new Object[] { personsList, childrenAndAdultCount };
		assertEquals(objectResult, result);
	}

	@Test
	public void givenTwoHouseholdsWithOnlyOneServedByStation_whenGetPersonsServedByStation_thenReturnPhonesOfOneHousehold() {
		// GIVEN
		Address address1 = new Address("street1", 123, "city1");
		List<Person> personsList1 = new ArrayList<>();
		Person person1 = new Person("fname1", "lname1", "01/01/1990", address1, "phone1", "email1",
				new MedicalRecords());
		personsList1.add(person1);
		Household household1 = new Household(address1, personsList1);
		Address address2 = new Address("street2", 123, "city2");
		List<Person> personsList2 = new ArrayList<>();
		Person person2 = new Person("fname2", "lname2", "01/01/1990", address2, "phone2", "email2",
				new MedicalRecords());
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
		List<Person> result = firestationsService.getPersonsServedByStation(1);

		// THEN
		verify(householdDAO, Mockito.times(1)).getHouseholdsList();
		verify(firestationDAO, Mockito.times(1)).getFirestationsList();
		List<Person> personsServedByStationList = new ArrayList<>();
		personsServedByStationList.add(person1);
		assertEquals(personsServedByStationList, result);
	}

	@Test
	public void givenAStationNumber_whenGetPersonsListOfCity_thenReturnAListOfOnePerson() {
		// GIVEN
		Address address1 = new Address("street1", 123, "city1");
		List<Person> personsList1 = new ArrayList<>();
		Person person1 = new Person("fname1", "lname1", "01/01/1990", address1, "phone1", "email1",
				new MedicalRecords());
		personsList1.add(person1);
		Household household1 = new Household(address1, personsList1);
		Address address2 = new Address("street2", 123, "city2");
		List<Person> personsList2 = new ArrayList<>();
		Person person2 = new Person("fname2", "lname2", "01/01/1990", address2, "phone2", "email2",
				new MedicalRecords());
		personsList1.add(person2);
		Household household2 = new Household(address2, personsList2);
		List<Household> householdsList = new ArrayList<>();
		householdsList.add(household1);
		householdsList.add(household2);
		when(householdDAO.getHouseholdsList()).thenReturn(householdsList);

		List<Integer> stationNumbers = new ArrayList<>();
		stationNumbers.add(1);

		// WHEN
		List<Household> result = firestationsService.getHouseholdsServedByStations(stationNumbers);

		// THEN
		verify(householdDAO, Mockito.times(1)).getHouseholdsList();
		List<Household> householdsListResult = new ArrayList<>();
		householdsListResult.add(household1);
		assertEquals(householdsListResult, result);
	}

}
