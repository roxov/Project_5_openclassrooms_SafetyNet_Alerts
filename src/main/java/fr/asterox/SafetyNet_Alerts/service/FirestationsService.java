package fr.asterox.SafetyNet_Alerts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.SafetyNet_Alerts.consumer.FirestationDAO;
import fr.asterox.SafetyNet_Alerts.model.Firestation;

@Service
public class FirestationsService implements IFirestationsService {

	@Autowired
	public FirestationDAO firestationDAO;

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
