package fr.asterox.SafetyNet_Alerts.web.DTO;

import fr.asterox.SafetyNet_Alerts.model.Address;

public class PersonOfStationDTO {

	private String firstName;
	private String lastName;
	private Address address;
	private String phone;

	public PersonOfStationDTO(String firstName, String lastName, Address address, String phone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Address getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

}
