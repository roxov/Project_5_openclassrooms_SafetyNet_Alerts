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
import fr.asterox.SafetyNet_Alerts.web.DTO.FireAndFloodPersonDTO;
import fr.asterox.SafetyNet_Alerts.web.DTO.HouseholdDTO;
import fr.asterox.SafetyNet_Alerts.web.DTO.PeopleAndCountForStationDTO;
import fr.asterox.SafetyNet_Alerts.web.DTO.PersonOfStationDTO;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FirestationsServiceTest {

	@Autowired
	private static FirestationsService firestationsService;

	MedicalRecords medicalRecords;

	@MockBean
	private static FirestationDAO firestationDAO;

	@MockBean
	private static HouseholdDAO householdDAO;

	@BeforeEach
	private void setUpPerTest() {
		List<String> medicationsAndAllergies = new ArrayList<>();
		medicationsAndAllergies.add("medicationsAndAllergies");
		medicalRecords = new MedicalRecords(medicationsAndAllergies, medicationsAndAllergies);
	}

	@Test
	public void givenAChildAndAnAdultInHousehold_whenGetInfoOnPersonsServedByStation_thenInfoAndCountOfAdultsAndChildren() {
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

		List<Firestation> firestationsList = new ArrayList<>();
		List<Address> addressesList = new ArrayList<>();
		addressesList.add(address1);
		Firestation firestation1 = new Firestation(1, addressesList);
		firestationsList.add(firestation1);

		when(householdDAO.getHouseholdsList()).thenReturn(householdsList);
		when(firestationDAO.getFirestationsList()).thenReturn(firestationsList);

		// WHEN
		PeopleAndCountForStationDTO result = firestationsService.getInfoOnPersonsServedByStation(1);

		// THEN
		verify(householdDAO, Mockito.times(1)).getHouseholdsList();
		verify(firestationDAO, Mockito.times(1)).getFirestationsList();
		List<PersonOfStationDTO> peopleOfStation = new ArrayList<>();
		peopleOfStation.add(new PersonOfStationDTO("childname1", "lname1", address1, "phone1"));
		peopleOfStation.add(new PersonOfStationDTO("adultname1", "lname1", address1, "phone2"));
		PeopleAndCountForStationDTO testResult = new PeopleAndCountForStationDTO(peopleOfStation, 1, 1);
		assertEquals(testResult, result);
	}

	@Test
	public void givenTwoHouseholdsWithOnlyOneServedByStation_whenGetPersonsServedByStation_thenReturnPhonesOfOneHousehold() {
		// GIVEN
		Address address1 = new Address("street1", 123, "city1");
		List<Person> personsList1 = new ArrayList<>();
		Person person1 = new Person("fname1", "lname1", "01/01/1980", address1, "phone1", "email1", medicalRecords);
		personsList1.add(person1);
		Household household1 = new Household(address1, personsList1);
		Address address2 = new Address("street2", 123, "city2");
		List<Person> personsList2 = new ArrayList<>();
		Person person2 = new Person("fname2", "lname2", "01/01/1980", address2, "phone2", "email2", medicalRecords);
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
		List<PersonOfStationDTO> peopleOfStation = new ArrayList<>();
		peopleOfStation.add(new PersonOfStationDTO("fname1", "lname1", address1, "phone1"));
		PeopleAndCountForStationDTO testResult = new PeopleAndCountForStationDTO(peopleOfStation, 1, 0);
		assertEquals(testResult, result);
	}

	@Test
	public void givenOneStationNumberAndTwoFirestations_whenGetHouseholdForStation_thenReturnHouseholdOfOneFirestation() {
		// GIVEN
		Address address1 = new Address("street1", 123, "city1");
		List<Person> personsList1 = new ArrayList<>();
		Person person1 = new Person("fname1", "lname1", "01/01/1980", address1, "phone1", "email1", medicalRecords);
		personsList1.add(person1);
		Household household1 = new Household(address1, personsList1);
		Address address2 = new Address("street2", 123, "city2");
		List<Person> personsList2 = new ArrayList<>();
		Person person2 = new Person("fname2", "lname2", "01/01/1980", address2, "phone2", "email2", medicalRecords);
		personsList2.add(person2);
		Household household2 = new Household(address2, personsList2);
		List<Household> householdsList = new ArrayList<>();
		householdsList.add(household1);
		householdsList.add(household2);

		List<Firestation> firestationsList = new ArrayList<>();
		List<Address> addressesList1 = new ArrayList<>();
		addressesList1.add(address1);
		Firestation firestation1 = new Firestation(1, addressesList1);
		firestationsList.add(firestation1);
		List<Address> addressesList2 = new ArrayList<>();
		addressesList1.add(address2);
		Firestation firestation2 = new Firestation(2, addressesList2);
		firestationsList.add(firestation2);

		when(householdDAO.getHouseholdsList()).thenReturn(householdsList);
		when(firestationDAO.getFirestationsList()).thenReturn(firestationsList);

		List<Integer> stationNumbers = new ArrayList<>();
		stationNumbers.add(1);

		// WHEN
		List<HouseholdDTO> result = firestationsService.getHouseholdsServedByStations(stationNumbers);

		// THEN
		verify(householdDAO, Mockito.times(1)).getHouseholdsList();
		verify(firestationDAO, Mockito.times(1)).getFirestationsList();
		List<FireAndFloodPersonDTO> householdPersonsList = new ArrayList<>();
		int age1 = LocalDate.now().getYear() - 1980;
		householdPersonsList.add(new FireAndFloodPersonDTO("lname1", "phone1", age1, medicalRecords));
		List<HouseholdDTO> householdsListResult = new ArrayList<>();
		householdsListResult.add(new HouseholdDTO(householdPersonsList));
		assertEquals(householdsListResult, result);
	}

	@Test
	public void givenTwoStationNumbersCorrespondingToTwoFirestations_whenGetHouseholdForStations_thenReturnHouseholdsForEachFirestation() {
		// GIVEN
		Address address1 = new Address("street1", 123, "city1");
		List<Person> personsList1 = new ArrayList<>();
		Person person1 = new Person("fname1", "lname1", "01/01/1980", address1, "phone1", "email1", medicalRecords);
		personsList1.add(person1);
		Household household1 = new Household(address1, personsList1);
		Address address2 = new Address("street2", 123, "city2");
		List<Person> personsList2 = new ArrayList<>();
		Person person2 = new Person("fname2", "lname2", "01/01/1980", address2, "phone2", "email2", medicalRecords);
		personsList2.add(person2);
		Household household2 = new Household(address2, personsList2);
		List<Household> householdsList = new ArrayList<>();
		householdsList.add(household1);
		householdsList.add(household2);

		List<Firestation> firestationsList = new ArrayList<>();
		List<Address> addressesList1 = new ArrayList<>();
		addressesList1.add(address1);
		Firestation firestation1 = new Firestation(1, addressesList1);
		firestationsList.add(firestation1);
		List<Address> addressesList2 = new ArrayList<>();
		addressesList1.add(address2);
		Firestation firestation2 = new Firestation(2, addressesList2);
		firestationsList.add(firestation2);

		when(householdDAO.getHouseholdsList()).thenReturn(householdsList);
		when(firestationDAO.getFirestationsList()).thenReturn(firestationsList);

		List<Integer> stationNumbers = new ArrayList<>();
		stationNumbers.add(1);
		stationNumbers.add(2);

		// WHEN
		List<HouseholdDTO> result = firestationsService.getHouseholdsServedByStations(stationNumbers);

		// THEN
		verify(householdDAO, Mockito.times(1)).getHouseholdsList();
		verify(firestationDAO, Mockito.times(1)).getFirestationsList();
		List<HouseholdDTO> householdsListResult = new ArrayList<>();
		int age = LocalDate.now().getYear() - 1980;
		List<FireAndFloodPersonDTO> household1PersonsList = new ArrayList<>();
		household1PersonsList.add(new FireAndFloodPersonDTO("lname1", "phone1", age, medicalRecords));
		householdsListResult.add(new HouseholdDTO(household1PersonsList));
		List<FireAndFloodPersonDTO> household2PersonsList = new ArrayList<>();
		household2PersonsList.add(new FireAndFloodPersonDTO("lname2", "phone2", age, medicalRecords));
		householdsListResult.add(new HouseholdDTO(household2PersonsList));
		assertEquals(householdsListResult, result);
	}

}
