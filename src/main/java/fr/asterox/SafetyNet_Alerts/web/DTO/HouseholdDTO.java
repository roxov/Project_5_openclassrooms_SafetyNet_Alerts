package fr.asterox.SafetyNet_Alerts.web.DTO;

import java.util.List;

public class HouseholdDTO {

	private List<FireAndFloodPersonDTO> PersonsListOfHousehold;

	public HouseholdDTO(List<FireAndFloodPersonDTO> personsListOfHousehold) {
		super();
		PersonsListOfHousehold = personsListOfHousehold;
	}

	public List<FireAndFloodPersonDTO> getPersonsListOfHousehold() {
		return PersonsListOfHousehold;
	}

	public void setPersonsListOfHousehold(List<FireAndFloodPersonDTO> personsListOfHousehold) {
		PersonsListOfHousehold = personsListOfHousehold;
	}

}
