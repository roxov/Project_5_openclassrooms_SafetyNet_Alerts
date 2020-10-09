package fr.asterox.SafetyNet_Alerts.consumer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.model.Person;

/**
 * 
 * Simulate the exchanges with DB
 *
 */
@Repository
public class PersonDAO implements IPersonDAO {

	@Autowired
	public Data data;

	@Override
	public List<Person> getPersonsList() {
		return data.getPersonsList();
	}

}
