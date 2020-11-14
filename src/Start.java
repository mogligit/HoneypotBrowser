import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Start {

	public static final char SV = ',';

	public static void main(String[] args) {
		List<HoneypotData> data;
		data = new ArrayList<HoneypotData>();

		try {
			data = readFile("SampleDataset-Honeypots.csv");
			System.out.println("Dataset loaded correctly.");
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException. Dataset not found.");
		}

		switch (key) {
			case value:
				
				break;
		
			default:
				break;
		}
		// Collections.sort(data, HoneypotData.countryOrder);
		// for (HoneypotData entry : data) {
		// 	System.out.println(entry.toString());
		// }
	}

	private static int menu() {
		System.out.println("-- Honeypot attack browser --\n");
		System.out.println("1. List all countries");
		System.out.println("2. Hosts attacked by country and location");
		System.out.println("3. Search attack");
		System.out.println("4. Search IP address");
		System.out.println("5. Search Host");
		System.out.println("6. Top offending IP address");
		System.out.println("7. Find geographically similar IP addresses");
		System.out.print("\nPlease choose one: ");

		return UserInput.integer(1, 7);
	}

	private static List<HoneypotData> readFile(String path) throws FileNotFoundException {
		FileReader fr = new FileReader(path);

		Scanner s = new Scanner(fr);

		List<HoneypotData> scanned = new ArrayList<HoneypotData>();
		s.nextLine(); // skip line with headers
		while (s.hasNext()) {
			scanned.add(new HoneypotData(s.nextLine()));
		}

		s.close();
		return scanned;

	}

}
