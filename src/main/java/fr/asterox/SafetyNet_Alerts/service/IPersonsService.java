package fr.asterox.SafetyNet_Alerts.service;

import java.util.List;
import java.util.Map;

import fr.asterox.SafetyNet_Alerts.model.Person;

/**
 * 
 * Manage the operations concerning persons.
 *
 */

public interface IPersonsService {
	/**
	 * endpoint : personInfo?firstName=<firstName>&lastName=<lastName>
	 * 
	 * @param firstName
	 * @param lastName
	 * @return personsSelectedByLastNameList, a Map of Person/age
	 * 
	 */
	public Map<Person, String> getInhabitantsInfo(String firstName, String lastName);

	/**
	 * endpoint : communityEmail?city=<city>
	 * 
	 * @param city
	 * @return personsListOfTheCity
	 * 
	 */
	public List<Person> getPersonsListOfCity(String city);

	public void addPerson(Person person);

	public void updatePerson(Person person);

	public void deletePerson(Person person);
}
