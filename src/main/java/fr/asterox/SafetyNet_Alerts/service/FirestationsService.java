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

import fr.asterox.SafetyNet_Alerts.consumer.FirestationDAO;
import fr.asterox.SafetyNet_Alerts.consumer.HouseholdDAO;
import fr.asterox.SafetyNet_Alerts.model.Address;
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
	public FirestationDAO firestationDAO;
	@Autowired
	public HouseholdDAO householdDAO;

	private List<Firestation> allFirestationsList;
	private List<Household> allHouseholdsList;
	Map<Integer, List<Address>> firestationsMap;
	Map<Address, List<Person>> householdsMap;

	private void getFirestationsAndHouseholdsMaps() {
		allFirestationsList = firestationDAO.getFirestationsList();
		firestationsMap = new HashMap<>();
		for (Firestation firestation : allFirestationsList) {
			firestationsMap.put(firestation.getStationNumber(), firestation.getAdressesList());
		}

		allHouseholdsList = householdDAO.getHouseholdsList();
		householdsMap = new HashMap<>();
		for (Household household : allHouseholdsList) {
			householdsMap.put(household.getAddress(), household.getPersonsList());
		}
	}

	private List<Person> getPersonsServedByStation(int stationNumber) {
		getFirestationsAndHouseholdsMaps();
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
		List<Person> personsServedByStationList = getPersonsServedByStation(stationNumber);
		List<PersonOfStationDTO> personOfStationDTOList = new ArrayList<>();
		int numberOfAdults = 0;
		int numberOfChildren = 0;

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
		List<Person> personsServedByStationList = getPersonsServedByStation(stationNumber);
		List<String> phonesList = new ArrayList<>();
		for (Person person : personsServedByStationList) {
			String phone = person.getPhone();
			phonesList.add(phone);
		}
		LOGGER.info("Response to Phone Alert Request : Getting list of phones of people served by the station");
		return phonesList;
	}

	@Override
	public List<HouseholdDTO> getHouseholdsServedByStations(List<Integer> stationNumbersList) {

		getFirestationsAndHouseholdsMaps();
		List<HouseholdDTO> householdsDTOList = new ArrayList<>();

		for (int i : stationNumbersList) {
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
		LOGGER.info("Adding a firestation");
		firestationDAO.addFirestation(firestation);
	}

	@Override
	public void updateFirestation(Firestation firestation) {
		LOGGER.info("Updating a firestation");
		firestationDAO.updateFirestation(firestation);
	}

	@Override
	public void deleteFirestation(Firestation firestation) {
		LOGGER.info("Deleting a firestation");
		firestationDAO.deleteFirestation(firestation);
	}
}
