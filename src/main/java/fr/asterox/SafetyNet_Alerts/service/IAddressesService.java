package fr.asterox.SafetyNet_Alerts.service;

import java.util.List;

import fr.asterox.SafetyNet_Alerts.web.DTO.ChildDTO;
import fr.asterox.SafetyNet_Alerts.web.DTO.PeopleAndStationNumberOfAddressDTO;

/**
 * 
 * Manage the operations concerning addresses.
 *
 */

public interface IAddressesService {

	/**
	 * endpoint : childAlert?address=<address>
	 * 
	 * @param street
	 * @return a list of all children and adults of the address if there is a child,
	 *         or an empty list if there is not
	 * 
	 */
	public List<ChildDTO> getPersonsLivingInChildHousehold(String street);

	/**
	 * 
	 * endpoint : fire?address=<address>
	 * 
	 * @param street
	 * @return the list of inhabitants at this address and the station number of
	 *         firestation for this address
	 * 
	 */
	public PeopleAndStationNumberOfAddressDTO getInhabitantsAndStationOfTheAddress(String street);
}
