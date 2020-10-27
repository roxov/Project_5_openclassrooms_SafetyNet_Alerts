package fr.asterox.SafetyNet_Alerts.web.DTO;

public class ChildDTO {

	private String firstName;
	private String lastName;
	private int age;

	public ChildDTO(String firstName, String lastName, int age) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

}
