package fr.asterox.SafetyNet_Alerts.consumer;

import fr.asterox.SafetyNet_Alerts.model.Firestation;

public interface IFirestationDAO {
	public void addFirestation(Firestation firestation);

	public void updateFirestation(Firestation firestation);

	public void deleteFirestation(Firestation firestation);
}
