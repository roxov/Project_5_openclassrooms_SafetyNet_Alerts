package fr.asterox.SafetyNet_Alerts.web.DTO;

import java.time.LocalDate;

import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
import fr.asterox.SafetyNet_Alerts.technical.ManipulateDate;

public class PersonDTO {

	private String firstName;
	private String lastName;
	private String birthdate;
	private LocalDate age;
	private Address address;
	private String phone;
	private String email;
	private MedicalRecords medicalRecords;

	public PersonDTO() {
		super();
	}

	public PersonDTO(String firstName, String lastName, String birthdate, LocalDate age, Address address, String phone,
			String email, MedicalRecords medicalRecords) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.medicalRecords = medicalRecords;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public LocalDate getAge() {
		return age;
	}

	public void setAge(String birthdate) {
		this.birthdate = birthdate;
		LocalDate age = ManipulateDate.convertStringToLocalDate(birthdate);
		this.age = age;
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
