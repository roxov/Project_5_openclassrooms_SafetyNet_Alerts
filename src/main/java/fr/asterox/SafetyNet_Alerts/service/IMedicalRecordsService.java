package fr.asterox.SafetyNet_Alerts.service;

import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;

/**
 * 
 * Manage the operations concerning medical records (CRUD only).
 *
 */

public interface IMedicalRecordsService {

	public void addMedicalRecords(MedicalRecords medicalRecords);

	public void updateMedicalRecords(MedicalRecords medicalRecords);

	public void deleteMedicalRecords(MedicalRecords medicalRecords);
}
