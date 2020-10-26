package fr.asterox.SafetyNet_Alerts.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.asterox.SafetyNet_Alerts.service.AddressesService;
import fr.asterox.SafetyNet_Alerts.web.DTO.ChildDTO;
import fr.asterox.SafetyNet_Alerts.web.DTO.PeopleAndStationNumberOfAddressDTO;

/**
 * 
 * Process the requests concerning addresses.
 *
 */

@RestController
public class AddressesController {
	private static final Logger LOGGER = LogManager.getLogger(AddressesController.class);

	@Autowired
	private AddressesService addressesService;

	/**
	 * endpoint : childAlert?address=<address>
	 * 
	 */
	@GetMapping(value = "/childAlert")
	public List<ChildDTO> getPersonsLivingInChildHousehold(@RequestParam String address) {
		LOGGER.info("Getting persons living in Child Household for ChildAlert Request");
		return addressesService.getPersonsLivingInChildHousehold(address);
	}

	/**
	 * 
	 * endpoint : fire?address=<address>
	 * 
	 */
	@GetMapping(value = "/fire")
	public PeopleAndStationNumberOfAddressDTO getInhabitantsAndStationOfTheAddress(@RequestParam String address) {
		LOGGER.info("Getting People and Station Number for Fire Request");
		return addressesService.getInhabitantsAndStationOfTheAddress(address);
	}
}
