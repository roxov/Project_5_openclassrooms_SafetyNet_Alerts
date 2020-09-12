package fr.asterox.SafetyNet_Alerts.model;

import java.util.ArrayList;
import java.util.List;

public class Firestation {
	private List<Address> adressesList = new ArrayList<>();
	private int stationNumber;

	public Firestation() {
		super();
	}

	public Firestation(List<Address> adressesList, int stationNumber) {
		super();
		this.adressesList = adressesList;
		this.stationNumber = stationNumber;
	}

	public List<Address> getAdressesList() {
		return adressesList;
	}

	public void setAdressesList(List<Address> adressesList) {
		this.adressesList = adressesList;
	}

	public int getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}

}
