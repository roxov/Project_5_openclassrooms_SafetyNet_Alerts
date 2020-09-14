package fr.asterox.SafetyNet_Alerts.technical.dataCreation.dataSourceModel;

import java.util.List;

public class RootModel {
	public List<PersonModel> persons;
	public List<FirestationModel> firestations;
	public List<MedicalRecordsModel> medicalrecords;

	public List<PersonModel> getPersons() {
		return persons;
	}

	public void setPersons(List<PersonModel> persons) {
		this.persons = persons;
	}

	public List<FirestationModel> getFirestations() {
		return firestations;
	}

	public void setFirestations(List<FirestationModel> firestations) {
		this.firestations = firestations;
	}

	public List<MedicalRecordsModel> getMedicalrecords() {
		return medicalrecords;
	}

	public void setMedicalrecords(List<MedicalRecordsModel> medicalrecords) {
		this.medicalrecords = medicalrecords;
	}

}
