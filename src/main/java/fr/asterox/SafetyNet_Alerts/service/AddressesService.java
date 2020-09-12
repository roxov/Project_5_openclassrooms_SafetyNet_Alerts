package fr.asterox.SafetyNet_Alerts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.SafetyNet_Alerts.consumer.AddressDAO;

@Service
public class AddressesService implements IAddressesService {

	@Autowired
	public AddressDAO addressDAO;
}
