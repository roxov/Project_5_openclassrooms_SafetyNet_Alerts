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
public class FirestationsService implements IFirestationsService {

	@Autowired
	public FirestationDAO firestationDAO;
	@Autowired
	public HouseholdDAO householdDAO;

	@Override
	public Object[] getInfoOnPersonsServedByStation(int stationNumber) {
		List<Person> personsServedByStationList = new ArrayList<>();
		List<Firestation> allFirestationsList = firestationDAO.getFirestationsList();
		List<Household> allHouseholdsList = householdDAO.getHouseholdsList();
		Map<String, Integer> childrenAndAdultCount = new HashMap<>();
		childrenAndAdultCount.put("Number of adults", 0);
		childrenAndAdultCount.put("Number of children", 0);

		for (Firestation firestation : allFirestationsList) {
			if (firestation.getStationNumber() == stationNumber) {
				List<Address> addressesServedByStation = firestation.getAdressesList();
				for (Address address : addressesServedByStation) {
					for (Household household : allHouseholdsList) {
						if (household.getAddress().equals(address)) {
							List<Person> personsInHousehold = household.getPersonsList();
							personsServedByStationList.addAll(personsInHousehold);
							for (Person person : personsInHousehold) {
								LocalDate birthdate = ManipulateDate.convertStringToLocalDate(person.getBirthdate());
								if (birthdate.plusYears(18).isAfter(LocalDate.now())) {
									childrenAndAdultCount.put("Number of children",
											childrenAndAdultCount.get("Number of children") + 1);
								}
								childrenAndAdultCount.put("Number of adults",
										childrenAndAdultCount.get("Number of adults") + 1);
							}
							break;
						}
					}
				}
			}
		}

		return new Object[] { personsServedByStationList, childrenAndAdultCount };
	}

	@Override
	public List<Person> getPersonsServedByStation(int stationNumber) {
		List<Person> personsServedByStationList = new ArrayList<>();
		List<Firestation> allFirestationsList = firestationDAO.getFirestationsList();
		List<Household> allHouseholdsList = householdDAO.getHouseholdsList();

		for (Firestation firestation : allFirestationsList) {
			if (firestation.getStationNumber() == stationNumber) {
				List<Address> addressesServedByStation = firestation.getAdressesList();
				for (Address address : addressesServedByStation) {
					for (Household household : allHouseholdsList) {
						if (household.getAddress().equals(address)) {
							personsServedByStationList.addAll(household.getPersonsList());
						}
					}
				}
				break;
			}
		}
		return personsServedByStationList;
	}

	@Override
	public List<Household> getHouseholdsServedByStations(List<Integer> stationNumbersList) {
		List<Firestation> allFirestationsList = firestationDAO.getFirestationsList();
		List<Household> allHouseholdsList = householdDAO.getHouseholdsList();
		List<Household> householdsServedByStationsList = new ArrayList<>();
		for (Integer i : stationNumbersList) {
			for (Firestation firestation : allFirestationsList) {
				Integer stationNumber = firestation.getStationNumber();
				if (stationNumber.equals(i)) {
					List<Address> addressesServedByStation = firestation.getAdressesList();
					for (Address address : addressesServedByStation)
						for (Household household : allHouseholdsList) {
							if (household.getAddress().equals(address)) {
								householdsServedByStationsList.add(household);
							}
						}
				}
			}
		}
		return householdsServedByStationsList;
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
