package fr.asterox.SafetyNet_Alerts.consumer;

import java.util.ArrayList;
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
	public static List<Person> persons = new ArrayList<>();

	@Autowired
	private Data data;

	@Override
	public List<Person> getPersonsList() {
		return data.getPersonsList();
	}

	@Override
	public void addPerson(Person person) {
		// TODO : Mapping nom/prénom
	}

	@Override
	public void updatePerson(Person person) {
	}

	@Override
	public void deletePerson(Person person) {
	}

}
