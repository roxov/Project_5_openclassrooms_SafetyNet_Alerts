package fr.asterox.SafetyNet_Alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.SafetyNet_Alerts.consumer.FirestationDAO;
import fr.asterox.SafetyNet_Alerts.consumer.HouseholdDAO;
import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.Firestation;
import fr.asterox.SafetyNet_Alerts.model.Household;

@Service
public class HouseholdsService {

	@Autowired
	private HouseholdDAO householdDAO;
	private FirestationDAO firestationDAO;

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
}
