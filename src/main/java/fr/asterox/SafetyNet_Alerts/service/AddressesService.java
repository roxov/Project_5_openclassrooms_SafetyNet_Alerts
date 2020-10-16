package fr.asterox.SafetyNet_Alerts.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.model.Firestation;
import fr.asterox.SafetyNet_Alerts.model.Household;
import fr.asterox.SafetyNet_Alerts.model.Person;
import fr.asterox.SafetyNet_Alerts.technical.ManipulateDate;
import fr.asterox.SafetyNet_Alerts.web.DTO.ChildDTO;
import fr.asterox.SafetyNet_Alerts.web.DTO.FireAndFloodPersonDTO;
import fr.asterox.SafetyNet_Alerts.web.DTO.PeopleAndStationNumberOfAddressDTO;

@Service
public class AddressesService implements IAddressesService {
	private static final Logger LOGGER = LogManager.getLogger(AddressesService.class);

	@Autowired
	private Data data;

	private List<Firestation> allFirestationsList;
	private List<Household> allHouseholdsList;
	Map<Integer, List<Address>> firestationsMap;
	Map<Address, List<Person>> householdsMap;

	private void getFirestationsAndHouseholdsLists() {
		allFirestationsList = data.getFirestationsList();
		allHouseholdsList = data.getHouseholdsList();
	}

	@Override
	public List<ChildDTO> getPersonsLivingInChildHousehold(String street) {
		List<Person> personsInHousehold = new ArrayList<>();
		List<ChildDTO> childrenInHousehold = new ArrayList<>();
		List<ChildDTO> adultsInHousehold = new ArrayList<>();

		getFirestationsAndHouseholdsLists();

		for (Household household : allHouseholdsList) {
			if (household.getAddress().getStreet().equals(street)) {
				personsInHousehold = household.getPersonsList();
				for (Person person : personsInHousehold) {
					LocalDate birthdate = ManipulateDate.convertStringToLocalDate(person.getBirthdate());
					if (birthdate.plusYears(18).isAfter(LocalDate.now())) {
						Integer age = LocalDate.now().getYear() - birthdate.getYear();
						ChildDTO childDTO = new ChildDTO(person.getFirstName(), person.getLastName(), age);
						childrenInHousehold.add(childDTO);
					} else {
						Integer age = LocalDate.now().getYear() - birthdate.getYear();
						ChildDTO adult = new ChildDTO(person.getFirstName(), person.getLastName(), age);
						adultsInHousehold.add(adult);
					}
				}
				break;
			}
		}
		if (childrenInHousehold.isEmpty()) {
			LOGGER.info("Response to Child Alert Request : No child at the address");
			return null;
		}
		for (ChildDTO adult : adultsInHousehold) {
			childrenInHousehold.add(adult);
		}
		LOGGER.info("Response to Child Alert Request : Children and adults info");
		return childrenInHousehold;
	}

	@Override
	public PeopleAndStationNumberOfAddressDTO getInhabitantsAndStationOfTheAddress(String street) {

		List<Person> inhabitantsList = new ArrayList<>();
		Integer stationNumber = null;
		List<FireAndFloodPersonDTO> inhabitantsDTOList = new ArrayList<>();

		getFirestationsAndHouseholdsLists();

		for (Household household : allHouseholdsList) {
			if (household.getAddress().getStreet().equals(street)) {
				inhabitantsList = household.getPersonsList();
				for (Person person : inhabitantsList) {
					LocalDate birthdate = ManipulateDate.convertStringToLocalDate(person.getBirthdate());
					Integer age = LocalDate.now().getYear() - birthdate.getYear();
					FireAndFloodPersonDTO FirePersonDTO = new FireAndFloodPersonDTO(person.getLastName(),
							person.getPhone(), age, person.getMedicalRecords());
					inhabitantsDTOList.add(FirePersonDTO);
				}
			}
			break;
		}

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
}
