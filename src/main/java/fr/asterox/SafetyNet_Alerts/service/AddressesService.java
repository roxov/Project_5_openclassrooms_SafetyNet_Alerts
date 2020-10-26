package fr.asterox.SafetyNet_Alerts.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.model.Firestation;
import fr.asterox.SafetyNet_Alerts.model.Household;
import fr.asterox.SafetyNet_Alerts.model.Person;
import fr.asterox.SafetyNet_Alerts.technical.CalculateAge;
import fr.asterox.SafetyNet_Alerts.web.DTO.ChildDTO;
import fr.asterox.SafetyNet_Alerts.web.DTO.FireAndFloodPersonDTO;
import fr.asterox.SafetyNet_Alerts.web.DTO.PeopleAndStationNumberOfAddressDTO;

@Service
public class AddressesService implements IAddressesService {
	private static final Logger LOGGER = LogManager.getLogger(AddressesService.class);

	@Autowired
	private Data data;

	@Override
	public List<ChildDTO> getPersonsLivingInChildHousehold(String street) {
		List<ChildDTO> childrenInHousehold = new ArrayList<>();
		List<ChildDTO> adultsInHousehold = new ArrayList<>();
		boolean foundStreet = false;

		if (street == null) {
			LOGGER.error("Impossible to get information : empty street");
			return null;
		}

		List<Household> allHouseholdsList = getHouseholdsList();

		for (Household household : allHouseholdsList) {
			if (household.getAddress().getStreet().equals(street)) {
				List<Person> personsInHousehold = household.getPersonsList();
				for (Person person : personsInHousehold) {
					int age = CalculateAge.calculateAge(person.getBirthdate());
					if (age <= 18) {
						ChildDTO childDTO = new ChildDTO(person.getFirstName(), person.getLastName(), age);
						childrenInHousehold.add(childDTO);
					} else {
						ChildDTO adult = new ChildDTO(person.getFirstName(), person.getLastName(), age);
						adultsInHousehold.add(adult);
					}
				}
				foundStreet = true;
				break;

			}
		}
		if (!foundStreet) {
			LOGGER.error("Impossible to get information : no match found");
			return Collections.emptyList();
		}

		if (childrenInHousehold.isEmpty()) {
			LOGGER.info("Response to Child Alert Request : No child at the address");
			return Collections.emptyList();
		}
		for (ChildDTO adult : adultsInHousehold) {
			childrenInHousehold.add(adult);
		}
		LOGGER.info("Response to Child Alert Request : Children and adults info");
		return childrenInHousehold;
	}

	@Override
	public PeopleAndStationNumberOfAddressDTO getInhabitantsAndStationOfTheAddress(String street) {
		boolean foundStreet = false;
		Integer stationNumber = null;
		List<FireAndFloodPersonDTO> inhabitantsDTOList = new ArrayList<>();

		if (street == null) {
			LOGGER.error("Impossible to get information : empty street");
			return null;
		}

		List<Household> allHouseholdsList = getHouseholdsList();

		for (Household household : allHouseholdsList) {
			if (household.getAddress().getStreet().equals(street)) {
				List<Person> inhabitantsList = household.getPersonsList();
				for (Person person : inhabitantsList) {
					int age = CalculateAge.calculateAge(person.getBirthdate());
					FireAndFloodPersonDTO FirePersonDTO = new FireAndFloodPersonDTO(person.getLastName(),
							person.getPhone(), age, person.getMedicalRecords());
					inhabitantsDTOList.add(FirePersonDTO);
				}
				foundStreet = true;
				break;
			}
		}

		if (!foundStreet) {
			LOGGER.error("Impossible to get information : no match found");
			return null;
		}

		List<Firestation> allFirestationsList = getFirestationsList();
		for (Firestation firestation : allFirestationsList) {
			List<Address> addressesListOfStation = firestation.getAdressesList();
			for (Address address : addressesListOfStation) {
				if (address.getStreet().equals(street)) {
					stationNumber = firestation.getStationNumber();
					break;
				}
			}
		}
		LOGGER.info("Response to Fire Request : Inhabitants and station number of the address");
		return new PeopleAndStationNumberOfAddressDTO(inhabitantsDTOList, stationNumber);
	}

	private List<Firestation> getFirestationsList() {
		return data.getFirestationsList();
	}

	private List<Household> getHouseholdsList() {
		return data.getHouseholdsList();
	}
}
