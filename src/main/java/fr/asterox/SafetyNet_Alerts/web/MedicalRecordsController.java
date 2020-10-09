package fr.asterox.SafetyNet_Alerts.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public void addMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
		LOGGER.info("Adding new Medical Records");
		medicalRecordsService.addMedicalRecords(medicalRecords);
	}

	@PutMapping(value = "/medicalRecord")
	public void updateMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
		LOGGER.info("Updating Medical Records");
		medicalRecordsService.updateMedicalRecords(medicalRecords);
	}

	@DeleteMapping(value = "/medicalRecord")
	public void deleteMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
		LOGGER.info("Deleting Medical Records");
		medicalRecordsService.deleteMedicalRecords(medicalRecords);
	}

}
