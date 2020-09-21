package fr.asterox.SafetyNet_Alerts.consumer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.model.Firestation;

@Repository
public class FirestationDAO implements IFirestationDAO {

	@Autowired
	private Data data;

	public List<String> getPhonesListAssignedToFirestation(int stationNumber) {
		return null;
	}

	@Override
	public void addFirestation(Firestation firestation) {
	}

	@Override
	public void updateFirestation(Firestation firestation) {
	}

	@Override
	public void deleteFirestation(Firestation firestation) {
	}
}
