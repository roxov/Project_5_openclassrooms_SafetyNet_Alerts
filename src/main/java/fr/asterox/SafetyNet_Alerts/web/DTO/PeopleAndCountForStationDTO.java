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

	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

}
