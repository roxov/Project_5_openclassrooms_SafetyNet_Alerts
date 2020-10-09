package fr.asterox.SafetyNet_Alerts.model;

import java.util.ArrayList;
import java.util.List;

public class Household {
	private Address address;
	private List<Person> personsList = new ArrayList<>();

	public Household(Address address, List<Person> personsList) {
		super();
		this.address = address;
		this.personsList = personsList;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Person> getPersonsList() {
		return personsList;
	}

	public void setPersonsList(List<Person> personsList) {
		this.personsList = personsList;
	}

}
