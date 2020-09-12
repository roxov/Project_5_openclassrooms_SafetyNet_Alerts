package fr.asterox.SafetyNet_Alerts.model;

public class MedicalRecords {
	private String medications;
	private String allergies;

	public MedicalRecords() {
		super();
	}

	public MedicalRecords(String medications, String allergies) {
		super();
		this.medications = medications;
		this.allergies = allergies;
	}

	public String getMedications() {
		return medications;
	}

	public void setMedications(String medications) {
		this.medications = medications;
	}

	public String getAllergies() {
		return allergies;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

}
