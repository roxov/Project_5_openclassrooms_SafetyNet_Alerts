package fr.asterox.SafetyNet_Alerts.web.DTO;

import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;

public class FireAndFloodPersonDTO {

	private String lastName;
	private String phone;
	private int age;
	private MedicalRecords medicalRecords;

	public FireAndFloodPersonDTO(String lastName, String phone, int age, MedicalRecords medicalRecords) {
		super();
		this.lastName = lastName;
		this.phone = phone;
		this.age = age;
		this.medicalRecords = medicalRecords;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public MedicalRecords getMedicalRecords() {
		return medicalRecords;
	}

	public void setMedicalRecords(MedicalRecords medicalRecords) {
		this.medicalRecords = medicalRecords;
	}

}
