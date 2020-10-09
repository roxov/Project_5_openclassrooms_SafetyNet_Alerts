package fr.asterox.SafetyNet_Alerts.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.SafetyNet_Alerts.consumer.MedicalRecordsDAO;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;

@Service
public class MedicalRecordsService implements IMedicalRecordsService {
	private static final Logger LOGGER = LogManager.getLogger(MedicalRecordsService.class);

	@Autowired
	public MedicalRecordsDAO medicalRecordsDAO;

	@Override
	public void addMedicalRecords(MedicalRecords medicalRecords) {
		LOGGER.info("Adding Medical Records");
		medicalRecordsDAO.addMedicalRecords(medicalRecords);
	}

	@Override
	public void updateMedicalRecords(MedicalRecords medicalRecords) {
		LOGGER.info("Updating Medical Records");
		medicalRecordsDAO.updateMedicalRecords(medicalRecords);
	}

	@Override
	public void deleteMedicalRecords(MedicalRecords medicalRecords) {
		LOGGER.info("Deleting Medical Records");
		medicalRecordsDAO.deleteMedicalRecords(medicalRecords);
	}
}
