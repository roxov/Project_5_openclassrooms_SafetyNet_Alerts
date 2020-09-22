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
import fr.asterox.SafetyNet_Alerts.model.Person;

@Service
public class FirestationsService implements IFirestationsService {

	@Autowired
	public FirestationDAO firestationDAO;
	@Autowired
	public HouseholdDAO householdDAO;

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

	public void addFirestation(Firestation firestation) {
		firestationDAO.addFirestation(firestation);
	}

	public void updateFirestation(Firestation firestation) {
		firestationDAO.updateFirestation(firestation);
	}

	public void deleteFirestation(Firestation firestation) {
		firestationDAO.deleteFirestation(firestation);
	}
}
