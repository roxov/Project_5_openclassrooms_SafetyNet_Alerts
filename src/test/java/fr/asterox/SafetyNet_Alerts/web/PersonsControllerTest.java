package fr.asterox.SafetyNet_Alerts.web;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import fr.asterox.SafetyNet_Alerts.service.PersonsService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonsControllerTest {

	private static PersonsController personsController;

	@Mock
	private static PersonsService personsService;

}
