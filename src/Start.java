import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Start {

	public static char SV = ',';
	private static List<HoneypotData> data;
	
	public static void main(String[] args) {

		data = new ArrayList<HoneypotData>();
		
		try {
			data = readFile("SampleDataset-Honeypots.csv");
			System.out.println("Dataset loaded correctly.");
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException. Dataset not found.");
		}
		
		System.out.println("Attacks read: " + data.size());
		
		System.out.println(Host.GEU.toString());
	}
	
	private static List<HoneypotData> readFile(String path) throws FileNotFoundException {
		FileReader fr = new FileReader(path);

		Scanner s = new Scanner(fr);
		
		List<HoneypotData> scanned = new ArrayList<HoneypotData>();
		s.nextLine();	//skip line with headers
		while (s.hasNext()) {
			scanned.add(new HoneypotData(s.nextLine()));
		}
		
		s.close();
		return scanned;
		
	}

}
