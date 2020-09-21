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
import fr.asterox.SafetyNet_Alerts.model.Household;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
import fr.asterox.SafetyNet_Alerts.model.Person;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class HouseholdsServiceTest {

	private static HouseholdsService householdsService;
	private static LocalDateTime birthdate;

	@Mock
	private static HouseholdDAO householdDAO;
	private static FirestationDAO firestationDAO;

	@Test
	public void givenAStationNumber_whenGetPersonsListOfCity_thenReturnAListOfOnePerson() {
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
		when(householdDAO.getHouseholdsList()).thenReturn(householdsList);

		List<Integer> stationNumbers = new ArrayList<>();
		stationNumbers.add(1);

		// WHEN
		List<Household> result = householdsService.getHouseholdsServedByStations(stationNumbers);

		// THEN
		verify(householdDAO, Mockito.times(1)).getHouseholdsList();
		List<Household> householdsListResult = new ArrayList<>();
		householdsListResult.add(household1);
		assertEquals(householdsListResult, result);
	}

}
