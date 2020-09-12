package fr.asterox.SafetyNet_Alerts.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import fr.asterox.SafetyNet_Alerts.consumer.FirestationDAO;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FirestationsServiceTest {

	private static FirestationsService firestationsService;

	@Mock
	private static FirestationDAO firestationDAO;
	/*
	 * public void givenAPhonesList_whenCallingService_thenReturnThePhonesList() {
	 * List<String> phonesList = new ArrayList<>(); phonesList.add("0285858585");
	 * phonesList.add("0354545454");
	 * when(firestationDAO.getPhonesListAssignedToFirestation(anyInt())).thenReturn(
	 * phonesList); List<String> result =
	 * firestationsService.getPhonesListAssignedToFirestation(1);
	 * assertEquals(phonesList, result); }
	 */

}
