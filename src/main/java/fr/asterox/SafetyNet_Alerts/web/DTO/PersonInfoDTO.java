package fr.asterox.SafetyNet_Alerts.web.DTO;

import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;

public class PersonInfoDTO {

	private String lastName;
	private Address address;
	private int age;
	private String email;
	private MedicalRecords medicalRecords;

	public PersonInfoDTO(String lastName, Address address, int age, String email, MedicalRecords medicalRecords) {
		super();
		this.lastName = lastName;
		this.address = address;
		this.age = age;
		this.email = email;
		this.medicalRecords = medicalRecords;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public MedicalRecords getMedicalRecords() {
		return medicalRecords;
	}

	public void setMedicalRecords(MedicalRecords medicalRecords) {
		this.medicalRecords = medicalRecords;
	}

}
