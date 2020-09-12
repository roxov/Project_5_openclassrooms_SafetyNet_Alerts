package fr.asterox.SafetyNet_Alerts.consumer;

import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;

public interface IMedicalRecordsDAO {
	public void addMedicalRecords(MedicalRecords medicalRecords);

	public void updateMedicalRecords(MedicalRecords medicalRecords);

	public void deleteMedicalRecords(MedicalRecords medicalRecords);
}
