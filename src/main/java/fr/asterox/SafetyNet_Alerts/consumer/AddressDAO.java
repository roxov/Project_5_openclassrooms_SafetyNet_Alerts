package fr.asterox.SafetyNet_Alerts.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.asterox.SafetyNet_Alerts.technical.dataCreation.Data;

@Repository
public class AddressDAO implements IAddressDAO {

	@Autowired
	private Data data;
}
