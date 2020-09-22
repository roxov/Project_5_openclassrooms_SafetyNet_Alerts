package fr.asterox.SafetyNet_Alerts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.SafetyNet_Alerts.consumer.MedicalRecordsDAO;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;

@Service
public class MedicalRecordsService implements IMedicalRecordsService {

	@Autowired
	public MedicalRecordsDAO medicalRecordsDAO;

	@Override
	public void addMedicalRecords(MedicalRecords medicalRecords) {
		medicalRecordsDAO.addMedicalRecords(medicalRecords);
	}

	@Override
	public void updateMedicalRecords(MedicalRecords medicalRecords) {
		medicalRecordsDAO.updateMedicalRecords(medicalRecords);
	}

	@Override
	public void deleteMedicalRecords(MedicalRecords medicalRecords) {
		medicalRecordsDAO.deleteMedicalRecords(medicalRecords);
	}
}
