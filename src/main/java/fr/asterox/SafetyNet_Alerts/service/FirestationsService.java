package fr.asterox.SafetyNet_Alerts.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

	@Override
	public List<Person> getPersonsServedByStation(int stationNumber) {
		List<Firestation> allFirestationsList = firestationDAO.getFirestationsList();
		List<Household> allHouseholdsList = householdDAO.getHouseholdsList();
		List<Person> personsServedByStationList = new ArrayList<>();

		for (Firestation firestation : allFirestationsList) {
			if (firestation.getStationNumber() == stationNumber) {
				List<Address> addressesServedByStation = firestation.getAdressesList();
				for (Address address : addressesServedByStation) {
					for (Household household : allHouseholdsList) {
						if (household.getAddress().equals(address)) {
							List<Person> personsInHousehold = household.getPersonsList();
							for (Person person : personsInHousehold) {
								personsServedByStationList.add(person);
							}
							break;
						}
					}
				}
			}
		}
		return personsServedByStationList;
	}

	@Override
	public PeopleAndCountForStationDTO getInfoOnPersonsServedByStation(int stationNumber) {
		List<Person> personsServedByStationList = this.getPersonsServedByStation(stationNumber);
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
			}
			numberOfAdults = numberOfAdults + 1;
		}
		return new PeopleAndCountForStationDTO(personOfStationDTOList, numberOfAdults, numberOfChildren);
	}

	@Override
	public List<String> getPhoneOfPersonsServedByStation(int stationNumber) {
		List<Person> personsServedByStationList = this.getPersonsServedByStation(stationNumber);
		List<String> phonesList = new ArrayList<>();
		for (Person person : personsServedByStationList) {
			String phone = person.getPhone();
			phonesList.add(phone);
		}
		return phonesList;
	}

	@Override
	public List<HouseholdDTO> getHouseholdsServedByStations(List<Integer> stationNumbersList) {

		List<Household> allHouseholdsList = householdDAO.getHouseholdsList();
		List<Firestation> allFirestationsList = firestationDAO.getFirestationsList();
		List<Household> householdsServedByStationsList = new ArrayList<>();
		List<HouseholdDTO> householdsDTOList = new ArrayList<>();

		for (int i : stationNumbersList) {
			for (Firestation firestation : allFirestationsList) {
				if (firestation.getStationNumber() == i) {
					List<Address> addressesServedByStation = firestation.getAdressesList();
					for (Address address : addressesServedByStation)
						for (Household household : allHouseholdsList) {
							if (household.getAddress().equals(address)) {
								householdsServedByStationsList.add(household);
							}
						}
				}
				break;
			}
		}

		for (Household household : householdsServedByStationsList) {

			List<Person> personsOfHousehold = household.getPersonsList();
			List<FireAndFloodPersonDTO> personsDTOOfHousehold = new ArrayList<>();
			for (Person person : personsOfHousehold) {
				LocalDate birthdate = ManipulateDate.convertStringToLocalDate(person.getBirthdate());
				int age = LocalDate.now().getYear() - birthdate.getYear();
				personsDTOOfHousehold.add(new FireAndFloodPersonDTO(person.getLastName(), person.getPhone(), age,
						person.getMedicalRecords()));

			}
			HouseholdDTO householdDTO = new HouseholdDTO(personsDTOOfHousehold);
			householdsDTOList.add(householdDTO);
		}

		return householdsDTOList;
	}

	@Override
	public void addFirestation(Firestation firestation) {
		firestationDAO.addFirestation(firestation);
	}

	@Override
	public void updateFirestation(Firestation firestation) {
		firestationDAO.updateFirestation(firestation);
	}

	@Override
	public void deleteFirestation(Firestation firestation) {
		firestationDAO.deleteFirestation(firestation);
	}
}
