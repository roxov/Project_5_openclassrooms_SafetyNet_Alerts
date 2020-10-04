package fr.asterox.SafetyNet_Alerts.service;

import java.util.List;

import fr.asterox.SafetyNet_Alerts.model.Person;
import fr.asterox.SafetyNet_Alerts.web.DTO.PersonInfoDTO;

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
	 * @return personsSelectedByLastNameList, the information on the concerned
	 *         person
	 * 
	 */
	public List<PersonInfoDTO> getInhabitantsInfo(String firstName, String lastName);

	/**
	 * endpoint : communityEmail?city=<city>
	 * 
	 * @param city
	 * @return emailsListOfTheCity
	 * 
	 */
	public List<String> getEmailsListOfCity(String city);

	public void addPerson(Person person);

	public void updatePerson(Person person);

	public void deletePerson(Person person);
}
