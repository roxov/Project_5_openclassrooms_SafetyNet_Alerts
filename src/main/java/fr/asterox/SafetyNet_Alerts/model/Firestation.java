package fr.asterox.SafetyNet_Alerts.model;

import java.util.List;

public class Firestation {
	private int stationNumber;
	private List<Address> adressesList;

	public Firestation(int stationNumber, List<Address> adressesList) {
		super();
		this.adressesList = adressesList;
		this.stationNumber = stationNumber;
	}

	public int getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}

	public List<Address> getAdressesList() {
		return adressesList;
	}

	public void setAdressesList(List<Address> adressesList) {
		this.adressesList = adressesList;
	}

}
