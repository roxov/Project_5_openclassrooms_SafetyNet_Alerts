package fr.asterox.SafetyNet_Alerts.service;

import java.util.List;

import fr.asterox.SafetyNet_Alerts.model.Firestation;
import fr.asterox.SafetyNet_Alerts.model.Household;
import fr.asterox.SafetyNet_Alerts.model.Person;

/**
 * 
 * Manage the operations concerning firestations.
 *
 */

public interface IFirestationsService {
	/**
	 * endpoint : firestation?stationNumber=<station_number>
	 * 
	 * @param stationNumber
	 * @return Object[] { personsServedByStationList, childrenAndAdultCount } where
	 *         personsServedByStationList contains persons info of the household and
	 *         childrenAndAdultCount contains a count of adults and children
	 * 
	 * 
	 */
	public Object[] getInfoOnPersonsServedByStation(int stationNumber);

	/**
	 * 
	 * endpoint : phoneAlert?firestation=<firestation_number>
	 * 
	 * @param stationNumber
	 * @return personsServedByStationList
	 * 
	 */
	public List<Person> getPersonsServedByStation(int stationNumber);

	/**
	 * endpoint : flood/stations?stations=<a list of station_numbers>
	 * 
	 * @param stationNumbersList
	 * @return householdsServedByStationsList
	 * 
	 */
	public List<Household> getHouseholdsServedByStations(List<Integer> stationNumbersList);

	public void addFirestation(Firestation firestation);

	public void updateFirestation(Firestation firestation);

	public void deleteFirestation(Firestation firestation);
}
