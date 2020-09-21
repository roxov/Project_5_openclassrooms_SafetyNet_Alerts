package fr.asterox.SafetyNet_Alerts.service;

import java.util.List;

import fr.asterox.SafetyNet_Alerts.model.Person;

public interface IPersonsService {
	public List<Person> getInhabitantsInfo(String firstName, String lastName);

	public List<Person> getPersonsListOfCity(String city);

	public void addPerson(Person person);

	public void updatePerson(Person person);

	public void deletePerson(Person person);
}
