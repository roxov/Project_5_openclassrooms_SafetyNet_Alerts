package fr.asterox.SafetyNet_Alerts.service;

import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;

/**
 * 
 * Manage the operations concerning medical records (CRUD only).
 *
 */

public interface IMedicalRecordsService {

	public void addMedicalRecords(String firstName, String lastName, String birthdate, MedicalRecords medicalRecords);

	public void updateMedicalRecords(String firstName, String lastName, MedicalRecords medicalRecords);

	public void deleteMedicalRecords(String firstName, String lastName);
}
