package fr.asterox.SafetyNet_Alerts.service;

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
	 * @return Object[] { childrenInHousehold, adultsInHousehold } where
	 *         childrenInHousehold contains children info + age and
	 *         adultsInHousehold contains adults info
	 * 
	 */
	public Object[] getPersonsLivingInChildHousehold(String street);

	/**
	 * 
	 * endpoint : fire?address=<address>
	 * 
	 * @param street
	 * @return Object[] { InhabitantsInfoMap, stationNumber } where
	 *         InhabitantsInfoMap is a Map of Person/age
	 * 
	 */
	public Object[] getInhabitantsAndStationOfTheAddress(String street);
}
