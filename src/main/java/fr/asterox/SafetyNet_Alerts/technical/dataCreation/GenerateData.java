package fr.asterox.SafetyNet_Alerts.technical.dataCreation;

/**
 * 
 * Load datas from data.json
 *
 */

public class GenerateData {

//	@Autowired
//	private JsonReader jsonReader;
//
//	public static List<Address> addressesList = new ArrayList<>();
//	public static List<MedicalRecords> medicalRecordsList = new ArrayList<>();
//
//	// public static List<Household> householdsList = new ArrayList<>();
//	public static List<Firestation> firestationsList = new ArrayList<>();
//	public static List<Person> personsList1 = new ArrayList<>();
//	public static List<Person> personsList2 = new ArrayList<>();
//	public static List<Person> personsList3 = new ArrayList<>();
//	public static List<Person> personsList4 = new ArrayList<>();
//	public static List<Address> adressesListStation1 = new ArrayList<>();
//	public static List<Address> adressesListStation2 = new ArrayList<>();
//
//	static {
//
//		// 1 ROOT MODEL qui contient
//		// public List<PersonModel> persons; String firstName + lastName + address =
//		// street + city; int zip; String phone + email;
//
//		// public List<FirestationModel> firestations; String address = street+ int
//		// station;
//
//		// public List<MedicalRecordsModel> medicalrecords;String firstName + lastName +
//		// String birthdate + String medications + String allergies;
//
//		// Person : Map nom/prenom, birthdate à transformer en LocalDateTime, Address,
//		// phone, email, MedicalRecords;
//
//		Address address1 = new Address("1509 Culver St", 97451, "Culver");
//		Address address2 = new Address("29 15th St", 97451, "Culver");
//		Address address3 = new Address("834 Binoc Ave", 97451, "Culver");
//		Address address4 = new Address("644 Gershwin Cir", 97451, "Culver");
//
//		adressesListStation1.add(address1);
//		adressesListStation1.add(address2);
//		adressesListStation2.add(address3);
//		adressesListStation2.add(address4);
//
//		Household household1 = new Household(address1, personsList1);
//		Household household2 = new Household(address2, personsList2);
//		Household household3 = new Household(address3, personsList3);
//		Household household4 = new Household(address4, personsList4);
//	}
//
//	@Bean
//	public RootModel createRootModel() throws IOException {
//		RootModel rootModel = jsonReader.createObjectFromData();
//		return rootModel;
//	}
//
//	@Bean
//	public List<Address> createAddressesList() throws IOException {
//		String streetModel;
//		List<PersonModel> personsModelList = this.createRootModel().getPersons();
//
//		for (PersonModel personModel : personsModelList) {
//			streetModel = personModel.getAddress();
//			for (Address i : addressesList) {
//				if (streetModel.equals(i.getStreet())) {
//				}
//				Address address = new Address(personModel.getAddress(), personModel.getZip(), personModel.getCity());
//				addressesList.add(address);
//			}
//		}
//		return addressesList;
//	}
//
//	@Bean
//	public List<MedicalRecords> createMedicalRecordsList() throws IOException {
//
//		List<MedicalRecordsModel> medicalRecordsModelList = this.createRootModel().getMedicalrecords();
//
//		for (MedicalRecordsModel medicalRecordsModel : medicalRecordsModelList) {
//			medicalRecordsList
//					.add(new MedicalRecords(medicalRecordsModel.getMedications(), medicalRecordsModel.getAllergies()));
//		}
//		return medicalRecordsList;
//	}
//
//	@Bean
//	public List<Firestation> createFirestationsList() throws IOException {
//		List<Address> addressesList = this.createAddressesList();
//		Integer stationNumberModel;
//		String streetModel;
//		List<FirestationModel> firestationsModelList = this.createRootModel().getFirestations();
//
//		for (FirestationModel firestationModel : firestationsModelList) {
//			stationNumberModel = firestationModel.getStation();
//			streetModel = firestationModel.getAddress();
//
//			for (Firestation i : firestationsList) {
//				if (stationNumberModel.equals(i.getStationNumber())) {
//					List<Address> existingAddressesListOfTheStation = i.getAdressesList();
//					for (Address a : existingAddressesListOfTheStation) {
//						if (streetModel.equals(a.getStreet())) {
//						}
//						existingAddressesListOfTheStation.add(a);
//					}
//				}
//				List<Address> addressesListOfTheFirestation = new ArrayList<>();
//				for (Address a : addressesList) {
//					if (a.getStreet().equals(streetModel)) {
//						addressesListOfTheFirestation.add(a);
//					}
//					// Jeter une exception : adresse inexistante dans la DB
//				}
//			}
//		}
//		return firestationsList;
//	}
}
