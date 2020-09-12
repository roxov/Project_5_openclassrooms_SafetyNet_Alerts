package fr.asterox.SafetyNet_Alerts.consumer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.asterox.SafetyNet_Alerts.model.Address;
import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.model.MedicalRecords;
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
	public Person emailList(String city) {
		for (Person person : persons) {
			if (person.getAddress().equals(city)) {
				return person;
			}
		}
		Person person2 = new Person("Julie", "Cocotte", "44", new Address(), 25454555, "hello@moi.fr",
				new MedicalRecords());
		return person2;
	}

	public void addPerson(Person person) {
		// TODO : Mapping nom/prénom
	}

	public void updatePerson(Person person) {
	}

	public void deletePerson(Person person) {
	}

}
