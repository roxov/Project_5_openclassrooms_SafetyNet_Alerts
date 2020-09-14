package fr.asterox.SafetyNet_Alerts.technical.dataCreation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.asterox.SafetyNet_Alerts.technical.dataCreation.dataSourceModel.RootModel;

@Service
public class JsonReader {

	public FileReader filereader;
	private static final Logger logger = LogManager.getLogger(JsonReader.class);

	public String readDataSourceFile() throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("SafetyNet_Alerts/src/main/resources/data.json"));
			// TODO : trouver une méthode qui lit tout le fichier d'un coup ?
			StringBuilder stbuilder = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				stbuilder.append(line);
				line = reader.readLine();
			}
			String allData = stbuilder.toString();
			reader.close();
			return allData;
		} catch (FileNotFoundException e) {
			logger.fatal("The data source file was not found", e);
			throw new RuntimeException("The data source file was not found", e);
		}

	}

	public RootModel createObjectFromData() throws IOException {
		String allData = this.readDataSourceFile();
		RootModel rootModel = new ObjectMapper().readValue(allData, RootModel.class);
		return rootModel;
	}

}
