package fr.asterox.SafetyNet_Alerts.consumer;

import java.util.List;

import fr.asterox.SafetyNet_Alerts.model.Household;

/**
 * 
 * Get the list of households.
 *
 */

public interface IHouseholdDAO {
	public List<Household> getHouseholdsList();
}
