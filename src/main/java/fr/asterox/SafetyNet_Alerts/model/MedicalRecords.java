package fr.asterox.SafetyNet_Alerts.model;

import java.util.List;

public class MedicalRecords {
	private List<String> medications;
	private List<String> allergies;

	public MedicalRecords(List<String> medications, List<String> allergies) {
		super();
		this.medications = medications;
		this.allergies = allergies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allergies == null) ? 0 : allergies.hashCode());
		result = prime * result + ((medications == null) ? 0 : medications.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalRecords other = (MedicalRecords) obj;
		if (allergies == null) {
			if (other.allergies != null)
				return false;
		} else if (!allergies.equals(other.allergies))
			return false;
		if (medications == null) {
			if (other.medications != null)
				return false;
		} else if (!medications.equals(other.medications))
			return false;
		return true;
	}

	public List<String> getMedications() {
		return medications;
	}

	public void setMedications(List<String> medications) {
		this.medications = medications;
	}

	public List<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}

}
