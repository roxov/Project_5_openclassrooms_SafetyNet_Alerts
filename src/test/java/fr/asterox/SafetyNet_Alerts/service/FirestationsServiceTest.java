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
import fr.asterox.SafetyNet_Alerts.web.DTO.HouseholdDTO;
import fr.asterox.SafetyNet_Alerts.web.DTO.PeopleAndCountForStationDTO;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FirestationsServiceTest {

	@Autowired
	private FirestationsService firestationsService;

	@MockBean
	private Data data;

	MedicalRecords medicalRecords;
	Address address;
	List<Address> addressesList;
	Person adult1;
	int adultAge;
	Person child1;
	int childAge;
	List<Person> personsList;
	List<Firestation> firestationsList;
	List<Household> householdsList;

	@BeforeEach
	private void setUpPerTest() {
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
	public void givenAChildAndAnAdultInHousehold_whenGetInfoOnPersonsServedByStation_thenInfoAndCountOfAdultsAndChildren() {
		// GIVEN
		personsList.add(child1);
		personsList.add(adult1);
		Household household = new Household(address, personsList);
		householdsList.add(household);
		addressesList.add(address);
		Firestation firestation = new Firestation(1, addressesList);
		firestationsList.add(firestation);

		when(data.getHouseholdsList()).thenReturn(householdsList);
		when(data.getFirestationsList()).thenReturn(firestationsList);

		// WHEN
		PeopleAndCountForStationDTO result = firestationsService.getInfoOnPersonsServedByStation(1);

		// THEN
		verify(data, Mockito.times(1)).getHouseholdsList();
		verify(data, Mockito.times(2)).getFirestationsList();
		assertEquals("childname1", result.getPeopleOfStation().get(0).getFirstName());
		assertEquals("lname1", result.getPeopleOfStation().get(0).getLastName());
		assertEquals(address, result.getPeopleOfStation().get(0).getAddress());
		assertEquals("chPhone1", result.getPeopleOfStation().get(0).getPhone());
		assertEquals("adultname1", result.getPeopleOfStation().get(1).getFirstName());
		assertEquals("lname1", result.getPeopleOfStation().get(1).getLastName());
		assertEquals(address, result.getPeopleOfStation().get(1).getAddress());
		assertEquals("adPhone1", result.getPeopleOfStation().get(1).getPhone());
		assertEquals(1, result.getNumberOfAdults());
		assertEquals(1, result.getNumberOfChildren());
	}

	@Test
	public void givenOneFirestation_whenGetInfoOnPersonsServedByInexistentStation_thenReturnEmptyList() {
		// GIVEN
		addressesList.add(address);
		Firestation firestation = new Firestation(1, addressesList);
		firestationsList.add(firestation);

		when(data.getFirestationsList()).thenReturn(firestationsList);

		// WHEN
		PeopleAndCountForStationDTO result = firestationsService.getInfoOnPersonsServedByStation(2);

		// THEN
		verify(data, Mockito.times(1)).getFirestationsList();
		assertEquals(null, result);
	}

	@Test
	public void givenListOfTwoPersonsServedByStation_whenGetPhonesListOfStation_thenReturnPhonesList() {
		// GIVEN
		personsList.add(child1);
		personsList.add(adult1);
		Household household = new Household(address, personsList);
		householdsList.add(household);
		addressesList.add(address);
		Firestation firestation = new Firestation(1, addressesList);
		firestationsList.add(firestation);

		when(data.getHouseholdsList()).thenReturn(householdsList);
		when(data.getFirestationsList()).thenReturn(firestationsList);

		// WHEN
		List<String> result = firestationsService.getPhoneOfPersonsServedByStation(1);

		// THEN
		verify(data, Mockito.times(1)).getHouseholdsList();
		verify(data, Mockito.times(2)).getFirestationsList();
		List<String> phonesListTest = new ArrayList<>();
		phonesListTest.add("chPhone1");
		phonesListTest.add("adPhone1");
		assertEquals(phonesListTest, result);

	}

	@Test
	public void givenOneFirestation_whenGetPhonesForInexistentStation_thenReturnEmptyList() {
		// GIVEN
		addressesList.add(address);
		Firestation firestation = new Firestation(1, addressesList);
		firestationsList.add(firestation);

		when(data.getFirestationsList()).thenReturn(firestationsList);

		// WHEN
		List<String> result = firestationsService.getPhoneOfPersonsServedByStation(2);

		// THEN
		verify(data, Mockito.times(1)).getFirestationsList();
		assertEquals(null, result);
	}

	@Test
	public void givenOneStationNumberAndTwoFirestations_whenGetHouseholdForStation_thenReturnHouseholdOfOneFirestation() {
		// GIVEN
		personsList.add(adult1);
		Household household1 = new Household(address, personsList);
		Address address2 = new Address("street2", 123, "city2");
		List<Person> personsList2 = new ArrayList<>();
		Person adult2 = new Person("adultname2", "lname2", "01/01/1980", address2, "phone2", "email2", medicalRecords);
		personsList2.add(adult2);
		Household household2 = new Household(address2, personsList2);
		householdsList.add(household1);
		householdsList.add(household2);

		addressesList.add(address);
		Firestation firestation1 = new Firestation(1, addressesList);
		firestationsList.add(firestation1);
		List<Address> addressesList2 = new ArrayList<>();
		addressesList2.add(address2);
		Firestation firestation2 = new Firestation(2, addressesList2);
		firestationsList.add(firestation2);

		when(data.getHouseholdsList()).thenReturn(householdsList);
		when(data.getFirestationsList()).thenReturn(firestationsList);

		List<Integer> stationNumbers = new ArrayList<>();
		stationNumbers.add(1);

		// WHEN
		List<HouseholdDTO> result = firestationsService.getHouseholdsServedByStations(stationNumbers);

		// THEN
		verify(data, Mockito.times(1)).getHouseholdsList();
		verify(data, Mockito.times(1)).getFirestationsList();
		assertEquals("lname1", result.get(0).getPersonsListOfHousehold().get(0).getLastName());
		assertEquals("adPhone1", result.get(0).getPersonsListOfHousehold().get(0).getPhone());
		assertEquals(adultAge, result.get(0).getPersonsListOfHousehold().get(0).getAge());
		assertEquals(medicalRecords, result.get(0).getPersonsListOfHousehold().get(0).getMedicalRecords());
	}

	@Test
	public void givenTwoStationNumbersCorrespondingToTwoFirestations_whenGetHouseholdForStations_thenReturnHouseholdsForEachFirestation() {
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
		Firestation firestation1 = new Firestation(1, addressesList);
		firestationsList.add(firestation1);
		List<Address> addressesList2 = new ArrayList<>();
		addressesList2.add(address2);
		Firestation firestation2 = new Firestation(2, addressesList2);
		firestationsList.add(firestation2);

		when(data.getHouseholdsList()).thenReturn(householdsList);
		when(data.getFirestationsList()).thenReturn(firestationsList);

		List<Integer> stationNumbers = new ArrayList<>();
		stationNumbers.add(1);
		stationNumbers.add(2);

		// WHEN
		List<HouseholdDTO> result = firestationsService.getHouseholdsServedByStations(stationNumbers);

		// THEN
		verify(data, Mockito.times(1)).getHouseholdsList();
		verify(data, Mockito.times(1)).getFirestationsList();
		assertEquals("lname1", result.get(0).getPersonsListOfHousehold().get(0).getLastName());
		assertEquals("adPhone1", result.get(0).getPersonsListOfHousehold().get(0).getPhone());
		assertEquals(adultAge, result.get(0).getPersonsListOfHousehold().get(0).getAge());
		assertEquals(medicalRecords, result.get(0).getPersonsListOfHousehold().get(0).getMedicalRecords());
		assertEquals("lname2", result.get(1).getPersonsListOfHousehold().get(0).getLastName());
		assertEquals("adPhone2", result.get(1).getPersonsListOfHousehold().get(0).getPhone());
		assertEquals(adultAge, result.get(1).getPersonsListOfHousehold().get(0).getAge());
		assertEquals(medicalRecords, result.get(1).getPersonsListOfHousehold().get(0).getMedicalRecords());
	}

	@Test
	public void givenOneFirestation_whenGetHouseholdForInexistentStation_thenReturnEmptyList() {
		// GIVEN
		personsList.add(adult1);
		Household household1 = new Household(address, personsList);
		householdsList.add(household1);

		addressesList.add(address);
		Firestation firestation1 = new Firestation(1, addressesList);
		firestationsList.add(firestation1);

		when(data.getHouseholdsList()).thenReturn(householdsList);
		when(data.getFirestationsList()).thenReturn(firestationsList);

		List<Integer> stationNumbers = new ArrayList<>();
		stationNumbers.add(2);

		// WHEN
		List<HouseholdDTO> result = firestationsService.getHouseholdsServedByStations(stationNumbers);

		// THEN
		verify(data, Mockito.times(1)).getHouseholdsList();
		verify(data, Mockito.times(1)).getFirestationsList();
		assertEquals(null, result);
	}

	@Test
	public void givenListOfOneFirestation_whenAddFirestation_thenReturnListOfTwoFirestations() {
		// GIVEN
		addressesList.add(address);
		Firestation firestation1 = new Firestation(1, addressesList);
		firestationsList.add(firestation1);

		Address address2 = new Address("street2", 123, "city2");
		List<Address> addressesList2 = new ArrayList<>();
		addressesList2.add(address2);
		Firestation firestation2 = new Firestation(2, addressesList2);

		when(data.getFirestationsList()).thenReturn(firestationsList);

		// WHEN
		firestationsService.addFirestation(firestation2);

		// THEN
		verify(data, Mockito.times(2)).getFirestationsList();
		firestationsList.add(firestation2);
		assertEquals(firestationsList, data.getFirestationsList());
	}

	@Test
	public void givenListOfOneFirestation_whenAddFirestationWithExistentNumber_thenReturnSameList() {
		// GIVEN
		addressesList.add(address);
		Firestation firestation1 = new Firestation(1, addressesList);
		firestationsList.add(firestation1);

		Address address2 = new Address("street2", 123, "city2");
		List<Address> addressesList2 = new ArrayList<>();
		addressesList2.add(address2);
		Firestation firestation2 = new Firestation(1, addressesList2);

		when(data.getFirestationsList()).thenReturn(firestationsList);

		// WHEN
		firestationsService.addFirestation(firestation2);

		// THEN
		verify(data, Mockito.times(1)).getFirestationsList();
		assertEquals(firestationsList, data.getFirestationsList());
	}

	@Test
	public void givenListOfOneFirestation_whenAddFirestationWithNoAddress_thenReturnSameList() {
		// GIVEN
		addressesList.add(address);
		Firestation firestation1 = new Firestation(1, addressesList);
		firestationsList.add(firestation1);

		Firestation firestation2 = new Firestation(2, null);

		when(data.getFirestationsList()).thenReturn(firestationsList);

		// WHEN
		firestationsService.addFirestation(firestation2);

		// THEN
		verify(data, Mockito.times(1)).getFirestationsList();
		assertEquals(firestationsList, data.getFirestationsList());
	}

	@Test
	public void givenListOfOneFirestation_whenUpdateFirestation_thenReturnListOfActualizedFirestation() {
		// GIVEN
		addressesList.add(address);
		Firestation firestation1 = new Firestation(1, addressesList);
		firestationsList.add(firestation1);

		when(data.getFirestationsList()).thenReturn(firestationsList);

		// WHEN
		firestationsService.updateFirestation("street", 2);

		// THEN
		verify(data, Mockito.times(1)).getFirestationsList();
		assertEquals("street", data.getFirestationsList().get(0).getAdressesList().get(0).getStreet());
		assertEquals(2, data.getFirestationsList().get(0).getStationNumber());

	}

	@Test
	public void givenListOfOneFirestation_whenUpdateFirestationWithNoStreet_thenReturnSameList() {
		// WHEN
		firestationsService.updateFirestation(null, 2);

		// THEN
		assertEquals(firestationsList, data.getFirestationsList());
	}

	@Test
	public void givenListOfOneFirestation_whenUpdateFirestationWithInexistentStreet_thenReturnSameList() {
		// GIVEN
		addressesList.add(address);
		Firestation firestation1 = new Firestation(1, addressesList);
		firestationsList.add(firestation1);

		when(data.getFirestationsList()).thenReturn(firestationsList);

		// WHEN
		firestationsService.updateFirestation("street2", 2);

		// THEN
		verify(data, Mockito.times(1)).getFirestationsList();
		assertEquals(firestationsList, data.getFirestationsList());
	}

	@Test
	public void givenListOfOneFirestation_whenDeleteFirestation_thenReturnEmptyList() {
		// GIVEN
		addressesList.add(address);
		Firestation firestation1 = new Firestation(1, addressesList);
		firestationsList.add(firestation1);

		when(data.getFirestationsList()).thenReturn(firestationsList);

		// WHEN
		firestationsService.deleteFirestation(1);

		// THEN
		verify(data, Mockito.times(2)).getFirestationsList();
		List<Firestation> resultList = new ArrayList<>();
		assertEquals(resultList, data.getFirestationsList());
	}

	@Test
	public void givenListOfOneFirestation_whenDeleteFirestationWithInexistentNumber_thenReturnSameList() {
		// GIVEN
		addressesList.add(address);
		Firestation firestation1 = new Firestation(1, addressesList);
		firestationsList.add(firestation1);

		when(data.getFirestationsList()).thenReturn(firestationsList);

		// WHEN
		firestationsService.deleteFirestation(2);

		// THEN
		verify(data, Mockito.times(1)).getFirestationsList();
		assertEquals(firestationsList, data.getFirestationsList());
	}

//	@Test
//	public void givenListOfOneFirestationWithOneAddress_whenDeleteAddress_thenReturnEmptyAddressesList() {
//		// GIVEN
//		addressesList.add(address);
//		Firestation firestation1 = new Firestation(1, addressesList);
//		firestationsList.add(firestation1);
//
//		when(data.getFirestationsList()).thenReturn(firestationsList);
//
//		// WHEN
//		firestationsService.deleteAddressFromFirestation("street");
//
//		// THEN
//		verify(data, Mockito.times(2)).getFirestationsList();
//		// List<Firestation> resultList = new ArrayList<>();
//		assertEquals(null, data.getFirestationsList().get(0).getAdressesList());
//	}
//
//	@Test
//	public void givenListOfOneFirestationWithOneAddress_whenDeleteInexistentAddress_thenReturnSameAddressesList() {
//		// GIVEN
//		addressesList.add(address);
//		Firestation firestation1 = new Firestation(1, addressesList);
//		firestationsList.add(firestation1);
//
//		when(data.getFirestationsList()).thenReturn(firestationsList);
//
//		// WHEN
//		firestationsService.deleteAddressFromFirestation("street2");
//
//		// THEN
//		verify(data, Mockito.times(2)).getFirestationsList();
//		assertEquals(firestationsList, data.getFirestationsList());
//	}

}
