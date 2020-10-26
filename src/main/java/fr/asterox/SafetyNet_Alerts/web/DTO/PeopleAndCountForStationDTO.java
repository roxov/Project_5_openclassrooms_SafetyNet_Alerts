package fr.asterox.SafetyNet_Alerts.web.DTO;

import java.util.List;

public class PeopleAndCountForStationDTO {

	private List<PersonOfStationDTO> peopleOfStation;
	int numberOfAdults;
	int numberOfChildren;

	public PeopleAndCountForStationDTO() {
		super();
	}

	public PeopleAndCountForStationDTO(List<PersonOfStationDTO> peopleOfStation, int numberOfAdults,
			int numberOfChildren) {
		super();
		this.peopleOfStation = peopleOfStation;
		this.numberOfAdults = numberOfAdults;
		this.numberOfChildren = numberOfChildren;
	}

	public List<PersonOfStationDTO> getPeopleOfStation() {
		return peopleOfStation;
	}

	public void setPeopleOfStation(List<PersonOfStationDTO> peopleOfStation) {
		this.peopleOfStation = peopleOfStation;
	}

	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

}
