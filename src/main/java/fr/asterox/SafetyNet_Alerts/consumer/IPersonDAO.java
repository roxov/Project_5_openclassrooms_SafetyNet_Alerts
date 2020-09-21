package fr.asterox.SafetyNet_Alerts.consumer;

import java.util.List;

import fr.asterox.SafetyNet_Alerts.model.Person;

public interface IPersonDAO {
	public List<Person> getPersonsList();

	public void addPerson(Person person);

	public void updatePerson(Person person);

	public void deletePerson(Person person);
}
