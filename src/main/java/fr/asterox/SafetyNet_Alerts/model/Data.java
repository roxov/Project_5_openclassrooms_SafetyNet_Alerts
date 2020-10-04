package fr.asterox.SafetyNet_Alerts.model;

import java.util.ArrayList;
import java.util.List;

public class Data {

	public List<Household> householdsList = new ArrayList<>();
	public List<Firestation> firestationsList = new ArrayList<>();
	public List<Person> personsList = new ArrayList<>();

	// Test constructor
	public Data(List<Household> householdsList, List<Firestation> firestationsList, List<Person> personsList) {
		super();
		this.householdsList = householdsList;
		this.firestationsList = firestationsList;
		this.personsList = personsList;
	}

	public List<Household> getHouseholdsList() {
		return householdsList;
	}

	public void setHouseholdsList(List<Household> householdsList) {
		this.householdsList = householdsList;
	}

	public List<Firestation> getFirestationsList() {
		return firestationsList;
	}

	public void setFirestationsList(List<Firestation> firestationsList) {
		this.firestationsList = firestationsList;
	}

	public List<Person> getPersonsList() {
		return personsList;
	}

	public void setPersonsList(List<Person> personsList) {
		this.personsList = personsList;
	}

}
