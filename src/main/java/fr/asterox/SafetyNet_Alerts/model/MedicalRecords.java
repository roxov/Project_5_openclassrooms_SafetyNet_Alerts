package fr.asterox.SafetyNet_Alerts.model;

import java.util.List;

public class MedicalRecords {
	private List<String> medications;
	private List<String> allergies;

	public MedicalRecords(List<String> medications, List<String> allergies) {
		super();
		this.medications = medications;
		this.allergies = allergies;
	}

	public List<String> getMedications() {
		return medications;
	}

	public void setMedications(List<String> medications) {
		this.medications = medications;
	}

	public List<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}

}
