package TrackModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ExcelParser {

	private String inputFile;
	ArrayList<ArrayList<String>> line = new ArrayList<ArrayList<String>>();

	public ExcelParser(String inputFile) throws IOException {
		this.inputFile = inputFile;
		read();
	}

	public void read() throws IOException {
		File inputWorkbook = new File(inputFile);
		try {
			Scanner in = new Scanner(inputWorkbook);
			// scan in the csv
			while (in.hasNext()) {
				String[] input = in.nextLine().split(",");
				if (input.length != 0) {
					ArrayList<String> x = new ArrayList<String>();
					for (int i = 0; i < input.length; i++)
						x.add(input[i]);
					line.add(x);
				}
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ArrayList<String>> getLine() {
		return line;
	}

}