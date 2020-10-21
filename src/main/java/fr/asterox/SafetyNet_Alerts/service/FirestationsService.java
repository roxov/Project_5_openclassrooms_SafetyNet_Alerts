package fr.asterox.SafetyNet_Alerts.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
import fr.asterox.SafetyNet_Alerts.web.DTO.FireAndFloodPersonDTO;
import fr.asterox.SafetyNet_Alerts.web.DTO.HouseholdDTO;
import fr.asterox.SafetyNet_Alerts.web.DTO.PeopleAndCountForStationDTO;
import fr.asterox.SafetyNet_Alerts.web.DTO.PersonOfStationDTO;

@Service
public class FirestationsService implements IFirestationsService {
	private static final Logger LOGGER = LogManager.getLogger(FirestationsService.class);

	@Autowired
	private Data data;

	private List<Firestation> getFirestationsList() {
		return data.getFirestationsList();
	}

	private Map<Integer, List<Address>> getFirestationsMap() {
		List<Firestation> allFirestationsList = getFirestationsList();
		Map<Integer, List<Address>> firestationsMap = new HashMap<>();
		for (Firestation firestation : allFirestationsList) {
			firestationsMap.put(firestation.getStationNumber(), firestation.getAdressesList());
		}
		return firestationsMap;
	}

	private Map<Address, List<Person>> getHouseholdsMap() {
		List<Household> allHouseholdsList = data.getHouseholdsList();
		Map<Address, List<Person>> householdsMap = new HashMap<>();
		for (Household household : allHouseholdsList) {
			householdsMap.put(household.getAddress(), household.getPersonsList());
		}
		return householdsMap;
	}

	private List<Person> getPersonsServedByStation(int stationNumber) {
		Map<Address, List<Person>> householdsMap = getHouseholdsMap();
		Map<Integer, List<Address>> firestationsMap = getFirestationsMap();
		List<Person> personsServedByStationList = new ArrayList<>();

		List<Address> addressesServedByStation = firestationsMap.get(stationNumber);
		for (Address address : addressesServedByStation) {
			List<Person> personsAtTheAddress = householdsMap.get(address);
			for (Person person : personsAtTheAddress) {
				personsServedByStationList.add(person);
			}
		}
		LOGGER.info("Getting persons served by the station");
		return personsServedByStationList;
	}

	@Override
	public PeopleAndCountForStationDTO getInfoOnPersonsServedByStation(int stationNumber) {
		List<PersonOfStationDTO> personOfStationDTOList = new ArrayList<>();
		int numberOfAdults = 0;
		int numberOfChildren = 0;

		Map<Integer, List<Address>> firestationsMap = getFirestationsMap();
		if (!firestationsMap.containsKey(stationNumber)) {
			LOGGER.error("Impossible to get persons served by station : non existent station number");
			return null;
		}

		List<Person> personsServedByStationList = getPersonsServedByStation(stationNumber);
		for (Person person : personsServedByStationList) {
			PersonOfStationDTO personOfStationDTO = new PersonOfStationDTO(person.getFirstName(), person.getLastName(),
					person.getAddress(), person.getPhone());
			personOfStationDTOList.add(personOfStationDTO);

			LocalDate birthdate = ManipulateDate.convertStringToLocalDate(person.getBirthdate());
			if (birthdate.plusYears(18).isAfter(LocalDate.now())) {
				numberOfChildren = numberOfChildren + 1;
			} else {
				numberOfAdults = numberOfAdults + 1;
			}

		}
		LOGGER.info("Response to Firestation : list of persons served by the station and count of children and adults");
		return new PeopleAndCountForStationDTO(personOfStationDTOList, numberOfAdults, numberOfChildren);
	}

	@Override
	public List<String> getPhoneOfPersonsServedByStation(int stationNumber) {
		List<String> phonesList = new ArrayList<>();

		Map<Integer, List<Address>> firestationsMap = getFirestationsMap();
		if (!firestationsMap.containsKey(stationNumber)) {
			LOGGER.error("Impossible to get phones for the station : non existent station number");
			return null;
		}

		List<Person> personsServedByStationList = getPersonsServedByStation(stationNumber);

		for (Person person : personsServedByStationList) {
			String phone = person.getPhone();
			phonesList.add(phone);
		}
		LOGGER.info("Response to Phone Alert Request : Getting list of phones of people served by the station");
		return phonesList;
	}

	@Override
	public List<HouseholdDTO> getHouseholdsServedByStations(List<Integer> stationNumbersList) {
		Map<Integer, List<Address>> firestationsMap = getFirestationsMap();
		Map<Address, List<Person>> householdsMap = getHouseholdsMap();
		List<HouseholdDTO> householdsDTOList = new ArrayList<>();

		for (int i : stationNumbersList) {
			if (!firestationsMap.containsKey(i)) {
				LOGGER.error("Impossible to get households served by station : non existent station number");
				return null;
			}

			List<Address> addressesServedByStation = firestationsMap.get(i);
			for (Address address : addressesServedByStation) {
				List<Person> personsInHousehold = householdsMap.get(address);
				List<FireAndFloodPersonDTO> personsDTOOfHousehold = new ArrayList<>();
				for (Person person : personsInHousehold) {
					LocalDate birthdate = ManipulateDate.convertStringToLocalDate(person.getBirthdate());
					int age = LocalDate.now().getYear() - birthdate.getYear();
					personsDTOOfHousehold.add(new FireAndFloodPersonDTO(person.getLastName(), person.getPhone(), age,
							person.getMedicalRecords()));
				}
				HouseholdDTO householdDTO = new HouseholdDTO(personsDTOOfHousehold);
				householdsDTOList.add(householdDTO);
			}
		}
		LOGGER.info("Response to Flood Request : Getting people served by the stations, sorted by household");
		return householdsDTOList;
	}

	@Override
	public void addFirestation(Firestation firestation) {
		Map<Integer, List<Address>> firestationsMap = getFirestationsMap();

		if (firestation.getAdressesList() == null) {
			LOGGER.error("Impossible to add firestation : missing adresses list");
		} else if (firestationsMap.containsKey(firestation.getStationNumber())) {
			LOGGER.error("Impossible to add firestation : existing station number");
		} else {
			List<Firestation> allFirestationsList = getFirestationsList();
			allFirestationsList.add(firestation);
			data.setFirestationsList(allFirestationsList);
			LOGGER.info("Adding a firestation");
		}
	}

	@Override
	public void updateFirestation(String street, int newStationNumber) {

		if (street == null) {
			LOGGER.error("Impossible to update firestation : missing street");
		} else {
			List<Firestation> allFirestationsList = getFirestationsList();
			for (Firestation firestation : allFirestationsList) {
				List<Address> firestationAddressesList = firestation.getAdressesList();
				for (Address address : firestationAddressesList) {
					if (street.equals(address.getStreet())) {
						firestation.setStationNumber(newStationNumber);
						data.setFirestationsList(allFirestationsList);
						LOGGER.info("Updating a firestation");
						break;
					}
				}
			}
		}
	}

	@Override
	public void deleteFirestation(int stationNumber) {

		Map<Integer, List<Address>> firestationsMap = getFirestationsMap();
		if (!firestationsMap.containsKey(stationNumber)) {
			LOGGER.error("Impossible to delete firestation : non existent station number");
		} else {
			List<Firestation> allFirestationsList = getFirestationsList();
			for (Firestation firestation : allFirestationsList) {
				if (stationNumber == firestation.getStationNumber()) {
					int index = allFirestationsList.indexOf(firestation);
					allFirestationsList.remove(index);
					data.setFirestationsList(allFirestationsList);
					LOGGER.info("Deleting a firestation");
					break;
				}
			}
		}
	}

//	@Override
//	public void deleteAddressFromFirestation(String street) {
//		List<Firestation> allFirestationsList = getFirestationsList();
//		for (Firestation firestation : allFirestationsList) {
//			List<Address> addressesServedByStation = firestation.getAdressesList();
//			for (Address address : addressesServedByStation) {
//				if (street == address.getStreet()) {
//					int addressIndex = addressesServedByStation.indexOf(address);
//					addressesServedByStation.remove(addressIndex);
//					firestation.setAdressesList(addressesServedByStation);
//					data.setFirestationsList(allFirestationsList);
//					LOGGER.info("Deleting a firestation");
//					break;
//				}
//			}
//
//		}
//	}

}
