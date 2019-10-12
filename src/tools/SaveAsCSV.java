package tools;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaveAsCSV {
	/**
	 * Permet de sauvegarder dans un fichier CSV les operations effectuees dans la session courrante
	 * @param listTitleName
	 * @param listOperationCSV
	 * @throws IOException
	 */
	public SaveAsCSV(List<String> listTitleName,List<List<String>> listOperationCSV) throws IOException {
		
		FileWriter csvWriter = new FileWriter("OperationsBancaires_"+LocalDate.now().toString()+".csv");
		for(String string:listTitleName) {
			csvWriter.append(string);
			csvWriter.append(";");
		}
		csvWriter.append("\n");
		
		for (List<String> row : listOperationCSV) {
		    csvWriter.append(String.join(";", row));
		    csvWriter.append("\n");
		}

		csvWriter.flush();
		csvWriter.close();
	}

}
