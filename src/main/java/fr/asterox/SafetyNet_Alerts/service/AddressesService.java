package fr.asterox.SafetyNet_Alerts.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.SafetyNet_Alerts.consumer.FirestationDAO;
import fr.asterox.SafetyNet_Alerts.consumer.HouseholdDAO;
import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.Firestation;
import fr.asterox.SafetyNet_Alerts.model.Household;
import fr.asterox.SafetyNet_Alerts.model.Person;
import fr.asterox.SafetyNet_Alerts.technical.ManipulateDate;

@Service
public class AddressesService implements IAddressesService {

	@Autowired
	public HouseholdDAO householdDAO;
	@Autowired
	public FirestationDAO firestationDAO;

	@Override
	public Object[] getPersonsLivingInChildHousehold(String street) {
		List<Person> personsInHousehold = new ArrayList<>();
		Map<Person, Integer> childrenInHousehold = new HashMap<Person, Integer>();
		List<Person> adultsInHousehold = new ArrayList<>();

		List<Household> householdsList = householdDAO.getHouseholdsList();

		for (Household household : householdsList) {
			if (household.getAddress().getStreet().equals(street)) {
				personsInHousehold = household.getPersonsList();
				for (Person person : personsInHousehold) {
					LocalDate birthdate = ManipulateDate.convertStringToLocalDate(person.getBirthdate());
					if (birthdate.plusYears(18).isAfter(LocalDate.now())) {
						Integer age = LocalDate.now().getYear() - birthdate.getYear();
						childrenInHousehold.put(person, age);
					} else {
						adultsInHousehold.add(person);
					}
				}
				break;
			}
		}
		if (childrenInHousehold.isEmpty()) {
			return new Object[] { null };
		}
		return new Object[] { childrenInHousehold, adultsInHousehold };
	}

	@Override
	public Object[] getInhabitantsAndStationOfTheAddress(String street) {
		Map<Person, String> InhabitantsInfoMap = new HashMap<Person, String>();
		List<Person> inhabitantsList = new ArrayList<>();
		Integer stationNumber = null;

		List<Household> householdsList = householdDAO.getHouseholdsList();
		for (Household household : householdsList) {
			if (household.getAddress().getStreet().equals(street)) {
				inhabitantsList = household.getPersonsList();
				for (Person person : inhabitantsList) {
					LocalDate birthdate = ManipulateDate.convertStringToLocalDate(person.getBirthdate());
					Integer age = LocalDate.now().getYear() - birthdate.getYear();
					String ageSt = age + " years old";
					InhabitantsInfoMap.put(person, ageSt);
					// TODO : affiche person@2c3d... La Map ne peut pas être transformée par Jackson
					// (tableau ou liste)
				}
				break;
			}
		}

		List<Firestation> firestationsList = firestationDAO.getFirestationsList();
		for (Firestation firestation : firestationsList) {
			List<Address> addressesListOfStation = firestation.getAdressesList();
			for (Address address : addressesListOfStation) {
				if (address.getStreet().equals(street)) {
					stationNumber = firestation.getStationNumber();
					break;
				}
			}
		}
		return new Object[] { InhabitantsInfoMap, stationNumber };
	}
}
