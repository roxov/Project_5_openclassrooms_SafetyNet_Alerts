package fr.asterox.SafetyNet_Alerts.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
import fr.asterox.SafetyNet_Alerts.service.MedicalRecordsService;

/**
 * 
 * Process the requests concerning medical records.
 *
 */

@RestController
public class MedicalRecordsController {
	private static final Logger LOGGER = LogManager.getLogger(MedicalRecordsController.class);

	@Autowired
	private MedicalRecordsService medicalRecordsService;

	@PostMapping(value = "/medicalRecord")
	public void addMedicalRecords(@RequestBody String firstName, String lastName, String birthdate,
			MedicalRecords medicalRecords) {
		LOGGER.info("Adding new Medical Records");
		medicalRecordsService.addMedicalRecords(firstName, lastName, birthdate, medicalRecords);
	}

	@PutMapping(value = "/medicalRecord")
	public void updateMedicalRecords(@RequestBody String firstName, String lastName, MedicalRecords medicalRecords) {
		LOGGER.info("Updating Medical Records");
		medicalRecordsService.updateMedicalRecords(firstName, lastName, medicalRecords);
	}

	@DeleteMapping(value = "/medicalRecord")
	public void deleteMedicalRecords(@RequestParam String firstName, String lastName) {
		LOGGER.info("Deleting Medical Records");
		medicalRecordsService.deleteMedicalRecords(firstName, lastName);
	}

}
