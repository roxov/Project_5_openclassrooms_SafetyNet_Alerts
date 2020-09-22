package fr.asterox.SafetyNet_Alerts.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import fr.asterox.SafetyNet_Alerts.consumer.HouseholdDAO;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class HouseholdsServiceTest {

	private static HouseholdsService householdsService;

	@Mock
	private static HouseholdDAO householdDAO;

}
