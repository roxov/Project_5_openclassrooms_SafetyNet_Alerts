package fr.asterox.SafetyNet_Alerts.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class MedicalRecordsControllerIT {

	@LocalServerPort
	private Integer port;

	private String baseUrl;

	@BeforeEach
	public void setUpWebDriver() {
		baseUrl = "http://localhost:" + port;
	}

//	@PostMapping(value = "/medicalRecord")
//	public void addMedicalRecords(@RequestBody String firstName, String lastName, MedicalRecords medicalRecords) {
//		LOGGER.info("Adding new Medical Records");
//		medicalRecordsService.addMedicalRecords(firstName, lastName, medicalRecords);
//	}
//
//	@PutMapping(value = "/medicalRecord")
//	public void updateMedicalRecords(@RequestBody String firstName, String lastName, MedicalRecords medicalRecords) {
//		LOGGER.info("Updating Medical Records");
//		medicalRecordsService.updateMedicalRecords(firstName, lastName, medicalRecords);
//	}
//
//	@DeleteMapping(value = "/medicalRecord")
//	public void deleteMedicalRecords(@RequestParam String firstName, String lastName) {
//		LOGGER.info("Deleting Medical Records");
//		medicalRecordsService.deleteMedicalRecords(firstName, lastName);
//	}

//	@Test
//	public void givenMedicalRecords_whenAddMedicalRecordsToExistentOnes_thenReturnPreviousMEdicalRecords() {
//		// GIVEN
//		List<String> medications = new ArrayList<>();
//		medications.add("medications1");
//		List<String> allergies = new ArrayList<>();
//		allergies.add("allergies1");
//		MedicalRecords medicalRecords = new MedicalRecords(medications, allergies);
//
//		// WHEN
//		medicalRecordsController.addMedicalRecords("John", "Boyd", medicalRecords);
//
//		// THEN
//		MedicalRecords resultMedicalRecords = data.getPersonsList().get(0).getMedicalRecords();
//		List<String> resultMedications = new ArrayList<>();
//		resultMedications.add("aznol:350mg");
//		resultMedications.add("hydrapermazol:100mg");
//		List<String> resultAllergies = new ArrayList<>();
//		resultAllergies.add("nillacilan");
//		assertEquals(resultMedications, resultMedicalRecords.getMedications());
//		assertEquals(resultAllergies, resultMedicalRecords.getAllergies());
//	}
//
//	@Test
//	public void givenMedicalRecords_whenUpdateExistentMedicalRecords_thenReturnActualizedMedicalRecords() {
//		// GIVEN
//		List<String> medications = new ArrayList<>();
//		medications.add("medications1");
//		List<String> allergies = new ArrayList<>();
//		allergies.add("allergies1");
//		MedicalRecords medicalRecords = new MedicalRecords(medications, allergies);
//
//		// WHEN
//		medicalRecordsController.updateMedicalRecords("John", "Boyd", medicalRecords);
//
//		// THEN
//		MedicalRecords resultMedicalRecords = data.getPersonsList().get(0).getMedicalRecords();
//		List<String> resultMedications = new ArrayList<>();
//		resultMedications.add("medications1");
//		resultMedications.add("aznol:350mg");
//		resultMedications.add("hydrapermazol:100mg");
//		List<String> resultAllergies = new ArrayList<>();
//		resultAllergies.add("allergies1");
//		resultAllergies.add("nillacilan");
//		assertEquals(resultMedications, resultMedicalRecords.getMedications());
//		assertEquals(resultAllergies, resultMedicalRecords.getAllergies());
//	}
//
//	@Test
//	public void givenFirstNameAndLastName_whenDeleteMedicalRecords_thenReturnNullMedicalRecords() {
//		// WHEN
//		medicalRecordsController.deleteMedicalRecords("John", "Boyd");
//
//		// THEN
//		MedicalRecords resultMedicalRecords = data.getPersonsList().get(0).getMedicalRecords();
//		assertEquals(null, resultMedicalRecords.getMedications());
//		assertEquals(null, resultMedicalRecords.getAllergies());
//	}
}
