package fr.asterox.SafetyNet_Alerts.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;

@Repository
public class MedicalRecordsDAO implements IMedicalRecordsDAO {

	@Autowired
	private Data data;

	@Override
	public void addMedicalRecords(MedicalRecords medicalRecords) {

	}

	@Override
	public void updateMedicalRecords(MedicalRecords medicalRecords) {
	}

	@Override
	public void deleteMedicalRecords(MedicalRecords medicalRecords) {
	}
}
