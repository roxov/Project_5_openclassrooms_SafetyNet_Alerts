package fr.asterox.SafetyNet_Alerts.model;

import java.time.LocalDateTime;
import java.util.Map;

public class Person {
	private Map<String, String> firstAndLastNamesMap;
	private LocalDateTime birthdate;
	private Address address;
	private String phone;
	private String email;
	private MedicalRecords medicalRecords;

	public Person() {
		super();
	}

// Constructeur pour les tests

	public Person(Map<String, String> firstAndLastNamesMap, LocalDateTime birthdate, Address address, String phone,
			String email, MedicalRecords medicalRecords) {
		super();
		this.firstAndLastNamesMap = firstAndLastNamesMap;
		this.birthdate = birthdate;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.medicalRecords = medicalRecords;
	}

	public Map<String, String> getFirstAndLastNamesMap() {
		return firstAndLastNamesMap;
	}

	public void setFirstAndLastNamesMap(Map<String, String> firstAndLastNamesMap) {
		this.firstAndLastNamesMap = firstAndLastNamesMap;
	}

	public LocalDateTime getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDateTime birthdate) {
		this.birthdate = birthdate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
