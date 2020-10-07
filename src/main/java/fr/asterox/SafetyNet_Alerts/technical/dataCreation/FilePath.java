package fr.asterox.SafetyNet_Alerts.technical.dataCreation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FilePath {
	public FileReader filereader;
	private static final Logger LOGGER = LogManager.getLogger(GenerateData.class);

	// si static, impossible de mocker et je ne veux pas tester le fichier d'origine
	public static String readDataSourceFile() throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data.json"));
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
			LOGGER.fatal("The data source file was not found", e);
			throw new RuntimeException("The data source file was not found", e);
		}

	}
}
