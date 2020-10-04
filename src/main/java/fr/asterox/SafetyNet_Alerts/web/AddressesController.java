package fr.asterox.SafetyNet_Alerts.web;

import java.util.List;

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

	@Autowired
	private AddressesService addressesService;

	@GetMapping(value = "/childAlert")
	public List<ChildDTO> getPersonsLivingInChildHousehold(@RequestParam String address) {
		return addressesService.getPersonsLivingInChildHousehold(address);
	}

	@GetMapping(value = "/fire")
	public PeopleAndStationNumberOfAddressDTO getInhabitantsAndStationOfTheAddress(@RequestParam String address) {
		return addressesService.getInhabitantsAndStationOfTheAddress(address);
	}
}
