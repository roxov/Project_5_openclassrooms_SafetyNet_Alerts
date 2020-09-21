package fr.asterox.SafetyNet_Alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.SafetyNet_Alerts.consumer.AddressDAO;
import fr.asterox.SafetyNet_Alerts.consumer.FirestationDAO;
import fr.asterox.SafetyNet_Alerts.consumer.HouseholdDAO;
import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.Firestation;
import fr.asterox.SafetyNet_Alerts.model.Household;
import fr.asterox.SafetyNet_Alerts.model.Person;

@Service
public class AddressesService implements IAddressesService {

	@Autowired
	public AddressDAO addressDAO;
	@Autowired
	public HouseholdDAO householdDAO;
	@Autowired
	public FirestationDAO firestationDAO;

	public Object[] getInhabitantsAndStationOfTheAddress(String street) {
		List<Person> inhabitantsList = new ArrayList<>();
		Integer stationNumber = null;

		List<Household> householdsList = householdDAO.getHouseholdsList();
		for (Household household : householdsList) {
			if (household.getAddress().getStreet().equals(street)) {
				inhabitantsList = household.getPersonsList();
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
		return new Object[] { inhabitantsList, stationNumber };
	}
}
