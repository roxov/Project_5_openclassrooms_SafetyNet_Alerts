package fr.asterox.SafetyNet_Alerts.service;

import java.util.List;

import fr.asterox.SafetyNet_Alerts.model.Firestation;
import fr.asterox.SafetyNet_Alerts.model.Person;
import fr.asterox.SafetyNet_Alerts.web.DTO.HouseholdDTO;
import fr.asterox.SafetyNet_Alerts.web.DTO.PeopleAndCountForStationDTO;

/**
 * 
 * Manage the operations concerning firestations.
 *
 */

public interface IFirestationsService {

	/**
	 * 
	 * @param stationNumber
	 * @return personsServedByStationList, the list of people served by station
	 */
	public List<Person> getPersonsServedByStation(int stationNumber);

	/**
	 * endpoint : firestation?stationNumber=<station_number>
	 * 
	 * @param stationNumber
	 * @return the list of people served by station and the count of adults and
	 *         children
	 * 
	 * 
	 */
	public PeopleAndCountForStationDTO getInfoOnPersonsServedByStation(int stationNumber);

	/**
	 * 
	 * endpoint : phoneAlert?firestation=<firestation_number>
	 * 
	 * @param stationNumber
	 * @return phonesList, the phones list of persons served by station
	 * 
	 */
	public List<String> getPhoneOfPersonsServedByStation(int stationNumber);

	/**
	 * endpoint : flood/stations?stations=<a list of station_numbers>
	 * 
	 * @param stationNumbersList
	 * @return householdsDTOList, the list oh households and their occupants served
	 *         by station
	 * 
	 */
	public List<HouseholdDTO> getHouseholdsServedByStations(List<Integer> stationNumbersList);

	public void addFirestation(Firestation firestation);

	public void updateFirestation(Firestation firestation);

	public void deleteFirestation(Firestation firestation);
}
