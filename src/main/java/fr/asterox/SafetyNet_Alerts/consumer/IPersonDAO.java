package fr.asterox.SafetyNet_Alerts.consumer;

import fr.asterox.SafetyNet_Alerts.model.Person;

public interface IPersonDAO {
	public Person emailList(String city);
}
