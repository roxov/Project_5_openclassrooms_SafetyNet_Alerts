package fr.asterox.SafetyNet_Alerts.web.DTO;

import java.util.List;

public class PeopleAndStationNumberOfAddressDTO {

	private List<FireAndFloodPersonDTO> peopleOfAddress;
	int stationNumber;

	public PeopleAndStationNumberOfAddressDTO(List<FireAndFloodPersonDTO> peopleOfAddress, int stationNumber) {
		super();
		this.peopleOfAddress = peopleOfAddress;
		this.stationNumber = stationNumber;
	}

	public List<FireAndFloodPersonDTO> getPeopleOfAddress() {
		return peopleOfAddress;
	}

	public void setPeopleOfAddress(List<FireAndFloodPersonDTO> peopleOfAddress) {
		this.peopleOfAddress = peopleOfAddress;
	}

	public int getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}

}
