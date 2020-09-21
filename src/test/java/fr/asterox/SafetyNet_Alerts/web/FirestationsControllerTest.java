package fr.asterox.SafetyNet_Alerts.web;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import fr.asterox.SafetyNet_Alerts.service.FirestationsService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FirestationsControllerTest {

	private static FirestationsController firestationsController;

	@Mock
	private static FirestationsService firestationsService;
	/*
	 * @Test public void
	 * givenAPhonesList_whenCallingService_thenReturnThePhonesList() { // ARRANGE
	 * List<String> phonesList = new ArrayList<>(); phonesList.add("0285858585");
	 * phonesList.add("0354545454");
	 * when(firestationsService.getPhonesListAssignedToFirestation(anyInt())).
	 * thenReturn(phonesList);
	 * 
	 * // ASSERT List<String> result =
	 * firestationsController.getPhonesListAssignedToFirestation(2);
	 * 
	 * // ACT assertEquals(phonesList, result); }
	 */
	// TODO : cas exception pour un numéro de caserne inexistant
}
