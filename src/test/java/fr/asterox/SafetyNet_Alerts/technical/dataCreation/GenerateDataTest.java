package fr.asterox.SafetyNet_Alerts.technical.dataCreation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GenerateDataTest {

//	private static GenerateData data;
//	List<PersonModel> personsModelList;
//	List<FirestationModel> firestationsModelList;
//	List<MedicalRecordsModel> medicalRecordsModelList;
//
//	@Mock
//	private static JsonReader jsonReader;
//	@Mock
//	private static RootModel rootModel;
//
//	@BeforeEach
//	private void setUpPerTest() throws IOException {
//		personsModelList = new ArrayList<>();
//		PersonModel personModel1 = new PersonModel("fname1", "lname1", "streetmodel1", "city1", 123, "phone1",
//				"email1");
//		personsModelList.add(personModel1);
//
//		firestationsModelList = new ArrayList<>();
//		FirestationModel firestationModel = new FirestationModel("streetmodel1", 1);
//		firestationsModelList.add(firestationModel);
//
//		medicalRecordsModelList = new ArrayList<>();
//		MedicalRecordsModel medicalRecordsModel1 = new MedicalRecordsModel("fname1", "lname1", "birthdate1",
//				"medications1", "allergies1");
//		medicalRecordsModelList.add(medicalRecordsModel1);
//	}
//
//	@Test
//	public void givenTwoPersonsModelWithDifferentAddresses_whenCreateAddressesList_thenReturnAListOfTwoAddresses()
//			throws IOException {
//		// GIVEN
//		PersonModel personModel2 = new PersonModel("fname2", "lname2", "streetmodel2", "city2", 456, "phone2",
//				"email2");
//		personsModelList.add(personModel2);
//		rootModel = new RootModel(personsModelList, firestationsModelList, medicalRecordsModelList);
//
//		when(jsonReader.createObjectFromData()).thenReturn(rootModel);
//
//		List<Address> addressesList = new ArrayList<>();
//		addressesList.add(new Address("streetmodel1", 123, "city1"));
//		addressesList.add(new Address("streetmodel2", 456, "city2"));
//
//		// WHEN
//		List<Address> result = data.createAddressesList();
//
//		// THEN
//		// verify
//		assertEquals(addressesList, result);
//	}
//
//	@Test
//	public void givenTwoPersonsModelWithSameAddress_whenCreateAddressesList_thenReturnAListOfOneAddress()
//			throws IOException {
//		// ARRANGE
//		PersonModel personModel3 = new PersonModel("fname3", "lname3", "streetmodel1", "city1", 123, "phone3",
//				"email3");
//		personsModelList.add(personModel3);
//		List<Address> addressesList = new ArrayList<>();
//		addressesList.add(new Address("streetmodel1", 123, "city1"));
//		when(rootModel.getPersons()).thenReturn(personsModelList);
//
//		// ASSERT
//		List<Address> result = data.createAddressesList();
//
//		// ACT
//		assertEquals(addressesList, result);
//	}
//
//	@Test
//	public void givenTwoMedicalRecords_whenCreateMedicalRecordsList_thenReturnAListOfMedicalRecords()
//			throws IOException {
//		// ARRANGE
//		MedicalRecordsModel medicalRecordsModel2 = new MedicalRecordsModel("fname2", "lname2", "birthdate2",
//				"medications2", "allergies2");
//		medicalRecordsModelList.add(medicalRecordsModel2);
//		rootModel = new RootModel(personsModelList, firestationsModelList, medicalRecordsModelList);
//
//		when(jsonReader.createObjectFromData()).thenReturn(any(RootModel.class));
//		when(rootModel.getMedicalrecords()).thenReturn(medicalRecordsModelList);
//
//		List<MedicalRecords> medicalsRecordsList = new ArrayList<>();
//		medicalsRecordsList.add(new MedicalRecords("medications1", "allergies1"));
//		medicalsRecordsList.add(new MedicalRecords("medications2", "allergies2"));
//
//		// ASSERT
//		List<MedicalRecords> result = data.createMedicalRecordsList();
//
//		// ACT
//		assertEquals(medicalsRecordsList, result);
//	}

//	@Test
//	public void givenTwoAddressesForAFirestation_whenCreateFirestationsList_thenReturnAListOfOneFirestationWithTwoAddresses()
//			throws IOException {
//		// ARRANGE
//		PersonModel personModel3 = new PersonModel("fname3", "lname3", "streetmodel1", "city1", 123, "phone3",
//				"email3");
//		personsModelList.add(personModel3);
//		List<Address> addressesList = new ArrayList<>();
//		addressesList.add(new Address("streetmodel1", 123, "city1"));
//		when(rootModel.getPersons()).thenReturn(personsModelList);
//
//		// ASSERT
//		List<Address> result = data.createAddressesList();
//
//		// ACT
//		assertEquals(addressesList, result);
//	}
}
