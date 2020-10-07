package fr.asterox.SafetyNet_Alerts.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MedicalRecordsServiceTest {

	@Autowired
	private MedicalRecordsService medicalRecordsService;

}
