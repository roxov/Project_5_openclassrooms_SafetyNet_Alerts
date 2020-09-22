package fr.asterox.SafetyNet_Alerts.consumer;

import java.util.List;

import fr.asterox.SafetyNet_Alerts.model.Firestation;

/**
 * 
 * Update or get firestations data.
 *
 */

public interface IFirestationDAO {
	public List<Firestation> getFirestationsList();

	public void addFirestation(Firestation firestation);

	public void updateFirestation(Firestation firestation);

	public void deleteFirestation(Firestation firestation);
}
