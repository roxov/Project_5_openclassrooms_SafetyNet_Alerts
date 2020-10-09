package fr.asterox.SafetyNet_Alerts.consumer;

import java.util.List;

import fr.asterox.SafetyNet_Alerts.model.Person;

/**
 * 
 * Get persons List from data source.
 *
 */

public interface IPersonDAO {
	public List<Person> getPersonsList();
}
