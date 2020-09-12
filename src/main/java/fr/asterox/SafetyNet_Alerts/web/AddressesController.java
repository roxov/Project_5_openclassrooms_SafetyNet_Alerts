package fr.asterox.SafetyNet_Alerts.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import fr.asterox.SafetyNet_Alerts.service.AddressesService;

/**
 * 
 * Process the requests concerning addresses.
 *
 */

@RestController
public class AddressesController {

	@Autowired
	private AddressesService addressesService;

	/*
	 * @GetMapping(value = "/childAlert") public Person emailList(@RequestParam
	 * Address address) { return adressesService(address); }
	 */
	/*
	 * @GetMapping(value = "/fire") public Person emailList(@RequestParam Address
	 * address) { return firestationService(address); }
	 */

}
