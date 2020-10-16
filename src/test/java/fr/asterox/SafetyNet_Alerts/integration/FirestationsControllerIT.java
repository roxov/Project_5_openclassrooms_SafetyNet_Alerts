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

import fr.asterox.SafetyNet_Alerts.integration.config.DataTestConfig;
import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.model.Firestation;
import fr.asterox.SafetyNet_Alerts.web.FirestationsController;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FirestationsControllerIT {

	@Autowired
	private FirestationsController firestationsController;

	@Autowired
	private Data data;

	private static DataTestConfig dataTestConfig = new DataTestConfig();

	@BeforeAll
	private static void setUp() throws Exception {
		// FirestationDAO.data = dataTestConfig;
	}

	@BeforeEach
	private void setUpPerTest() {

		// TODO : renvoyer vers les données contenues dans dataTestConfig au lieu de
		// generateData.
	}

	@Test
	public void givenListOfOneFirestation_whenAddFirestation_thenReturnAListOfTwoFirestations() {
		// GIVEN
		Address address2 = new Address("street2", 123, "city2");
		List<Address> addressesList2 = new ArrayList<>();
		addressesList2.add(address2);
		Firestation firestation2 = new Firestation(2, addressesList2);

		// WHEN
		firestationsController.addFirestation(firestation2);

		// THEN
		List<Firestation> resultList = new ArrayList<>();
		Address address1 = new Address("street1", 123, "Culver");

		List<Address> addressesList1 = new ArrayList<>();
		addressesList1.add(address1);
		resultList.add(new Firestation(1, addressesList1));
		resultList.add(firestation2);
		assertEquals(resultList, data.getFirestationsList());
	}

	@Test
	public void givenANewStationNumberForAnAddress_whenUpdateStationNumber_thenModifyTheNumberOfStation() {

		// WHEN
		firestationsController.updateStationNumber("street1", 2);

		// THEN
		List<Firestation> resultList = new ArrayList<>();
		Address address1 = new Address("street1", 123, "Culver");
		List<Address> addressesList1 = new ArrayList<>();
		addressesList1.add(address1);
		resultList.add(new Firestation(2, addressesList1));
		assertEquals(resultList, data.getFirestationsList());
	}

	@Test
	public void givenListOfOneFirestation_whenDeleteFirestation_thenReturnAnEmptyList() {

		// WHEN
		firestationsController.deleteFirestation(1);

		// THEN
		assertEquals(null, data.getFirestationsList());
	}

}
