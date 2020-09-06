package fr.asterox.SafetyNet_Alerts.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import fr.asterox.SafetyNet_Alerts.model.Person;

/**
 * 
 * Simulate the exchanges with DB
 *
 */
@Repository
public class PersonDAO {
	public static List<Person> persons = new ArrayList<>();
	static {
		persons.add(0, new Person());
		// TODO : importer données du fichier ??
	}

	public List<Person> personFindByFirestaton(int stationNumber) {
		List<Person> list = new ArrayList<>();
		return list;
	}

	public Person save(Person person) {
		persons.add(person);
		return person;
	}

}
