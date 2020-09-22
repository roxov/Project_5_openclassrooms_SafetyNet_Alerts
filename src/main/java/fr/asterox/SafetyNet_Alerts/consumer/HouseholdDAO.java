package fr.asterox.SafetyNet_Alerts.consumer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.asterox.SafetyNet_Alerts.model.Data;
import fr.asterox.SafetyNet_Alerts.model.Household;

@Repository
public class HouseholdDAO implements IHouseholdDAO {

	@Autowired
	private Data data;

	@Override
	public List<Household> getHouseholdsList() {
		return data.getHouseholdsList();
	}
}
