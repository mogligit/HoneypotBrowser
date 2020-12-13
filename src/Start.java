import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Start {

	public static final char SEPARATION_CHAR = ',';

	public static void main(String[] args) {
		ArrayList<HoneypotData> dataset;
		dataset = new ArrayList<>();

		try {
			dataset = readFile("LargeDataset-Honeypots.csv");		
			System.out.println("Dataset loaded correctly.");
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException. Dataset not found.");
		}

		System.out.println("Total: " + dataset.size() + " entries");
		while(true) {
			switch (menu()) {
				case 1:
					displayCountries(new ArrayList<>(dataset));
					break;
				case 2:
					displayHostsPerCountry(new ArrayList<>(dataset));
					break;
				case 3:
					searchAttack(new ArrayList<>(dataset));
					break;
				case 4:
					searchIp(new ArrayList<>(dataset));
					break;
				case 5:
					searchHost(new ArrayList<>(dataset));
					break;
				case 6:
					topOffendingIpAddress(new ArrayList<>(dataset));
					break;
				case 7:
					findSimilarAttacks(new ArrayList<>(dataset));
					break;
				case 0:
					return;
				default:
					break;
			}
		}

	}

	private static int menu() {
		System.out.println("\n-- Honeypot attack browser --\n");
		System.out.println("1. List all countries");
		System.out.println("2. Hosts attacked by country");
		System.out.println("3. Search attack");
		System.out.println("4. Search IP address");
		System.out.println("5. Search Host");
		System.out.println("6. Top offending IP address");
		System.out.println("7. Find geographically similar attacks");
		System.out.println("0. QUIT");
		System.out.print("\nPlease choose one: ");

		return UserInput.integer(0, 7);
	}

	private static void displayCountries(ArrayList<HoneypotData> dataset) {
		ArrayList<String> countryList = new ArrayList<>(toHashMap(dataset, HoneypotData.COUNTRYNAME_INDEX).keySet());	// Get list of countries
		Collections.sort(countryList);	// nice alphabetical order

		System.out.println("List of countries:");
		printArray(countryList.toArray());
	}

	private static void displayHostsPerCountry(ArrayList<HoneypotData> dataset) {
		System.out.println("Hosts attacked per country");

		HashMap<String, ArrayList<HoneypotData>> countryDataMap = toHashMap(dataset, HoneypotData.COUNTRYNAME_INDEX);

		System.out.print("Input country: ");
		String input = UserInput.anyString();

		if (!countryDataMap.containsKey(input)) {
			System.out.println("Country was not found.");
			return;
		}

		// Gets list of hosts attacked by this country and puts them in a linear array to then remove duplicates
		ArrayList<Host> hosts = new ArrayList<>();
		for (HoneypotData entry : countryDataMap.get(input)) {
			hosts.add(entry.getHost());
		}
		// Remove duplicates
		hosts = removeDuplicates(hosts);

		// Output		
		System.out.println("\nCountry: " + input + "\nHosts attacked:");
		for (Host host : hosts) {
			System.out.println(" - " + host.toString());
		}

		System.out.println("Total of " + hosts.size());

	}

	private static void searchAttack(ArrayList<HoneypotData> dataset) {
		System.out.println("Search - press enter to leave blank");
		String searchString = "";
		HoneypotData searchModel;

		System.out.print("Date/time (dd/MM/yyyy hh:mm): ");
		searchString += UserInput.datetime(true) + SEPARATION_CHAR;

		System.out.print("Host: ");
		try {
			searchString += Host.getFrom(UserInput.anyString(true));
		} catch (Exception e) {
			System.out.println("Host not valid. Leaving blank.");
		} finally {
			searchString += SEPARATION_CHAR;
		}

		searchString += SEPARATION_CHAR;	// Comma for sourceInt

		System.out.print("Protocol: ");
		try {
			searchString += Protocol.valueOf(UserInput.anyString(true));
		} catch (Exception e) {
			System.out.println("Protocol not valid. Leaving blank.");
		} finally {
			searchString += SEPARATION_CHAR;
		}

		searchString += SEPARATION_CHAR;	// Comma for packetType

		System.out.print("Source port: ");
		searchString += UserInput.anyString(true) + SEPARATION_CHAR;

		System.out.print("Destination port: ");
		searchString += UserInput.anyString(true) + SEPARATION_CHAR;

		System.out.print("Source IPv4: ");
		searchString += UserInput.ipAddress(true) + SEPARATION_CHAR;

		searchString += SEPARATION_CHAR;	// Comma for country code

		System.out.print("Country: ");
		searchString += UserInput.anyString(true) + SEPARATION_CHAR;

		System.out.print("Locale: ");
		searchString += UserInput.anyString(true) + SEPARATION_CHAR;

		searchString += ",,,";	// Comma for remaining empty fields
		searchModel = new HoneypotData(searchString);

		System.out.println("\nResults: ");
		
		// Puts entries in a hashmap with the first search criterion that isn't null as key
		
		printArray(search(searchModel, dataset).toArray());
	}

	// Recursive search
	private static ArrayList<HoneypotData> search(HoneypotData searchModel, ArrayList<HoneypotData> dataset) {
		 int i = findNextFieldIndex(searchModel);
		 if (i == -1) {
			 return dataset;
		 }
		 
		 ArrayList<HoneypotData> results = new ArrayList<>();
		 for (HoneypotData entry : dataset) {
			 if (searchModel.getFieldFromIndex(i).equals(entry.getFieldFromIndex(i))) {
				 results.add(entry);
			 }
		 }

		 searchModel.setFieldFromIndex(i, null);	// Removes this search criterion so it can move on to the next

		 return search(searchModel, results);
	}
	private static int findNextFieldIndex(HoneypotData entry) {
		for (int i = 0; i < 16; i++) {
			if (entry.getFieldFromIndex(i) != null) {
				return i;
			}
		}
		return -1;
	}
	private static void printArray(Object[] array) {
		int i = 0;
		for(Object entry : array) {
			System.out.println(entry.toString());
			i++;
		}

		System.out.println("Total of " + i + " results.");
	}


	private static void searchIp(ArrayList<HoneypotData> dataset) {
		System.out.print("Input IPv4 address: ");
		String inputIp = UserInput.ipAddress();
		HoneypotData searchModel = new HoneypotData(",,,,,,," + inputIp + ",,,,,,,");

		printArray(search(searchModel, dataset).toArray());
	}

	private static void searchHost(ArrayList<HoneypotData> dataset) {
		System.out.print("Input host name: ");
		Host inputHost;
		try {
			inputHost = Host.getFrom(UserInput.anyString());
		} catch (IllegalArgumentException e) {
			System.out.println("Host not recognised.");
			return;
		}

		HoneypotData searchModel = new HoneypotData(SEPARATION_CHAR+ inputHost.toString() + ",,,,,,,,,,,,,");

		printArray(search(searchModel, dataset).toArray());
	}

	private static void topOffendingIpAddress(ArrayList<HoneypotData> dataset) {
		HashMap<String, ArrayList<HoneypotData>> srcIpMap = toHashMap(dataset, HoneypotData.SRCIP_INDEX);

		if (srcIpMap.size() == 0) {
			return;
		}

		int topSize = 0;
		String topIp = "";	// Won't let me build if it isn't initialised even though it'll never be null
		for (String srcIp : srcIpMap.keySet()) {
			int currentSize = srcIpMap.get(srcIp).size();
			if (currentSize > topSize) {
				topSize = currentSize;
				topIp = srcIp;
			}
		}

		System.out.print("Output attacks by this address? (Y/N): ");
		if (UserInput.bool()) {
			printArray(srcIpMap.get(topIp).toArray());
		}
		
		System.out.println("Top offending IP address: " + topIp);
	}

	private static void findSimilarAttacks(ArrayList<HoneypotData> dataset) {
		System.out.print("Please enter an IP address to find close attacks to (within 1 degree): ");
		String srcIp = UserInput.ipAddress();

		HoneypotData ipSearchModel = new HoneypotData();
		ipSearchModel.setSrcIp(srcIp);

		HashMap<String, ArrayList<HoneypotData>> ipMap = toHashMap(dataset, HoneypotData.SRCIP_INDEX);

		if (!ipMap.containsKey(srcIp)) {
			System.out.println("This IP address does not exist in the dataset. Exiting\n");
			return;
		}
		if (ipMap.get(srcIp).get(0).getCoords() == null) {
			System.out.print("This IP address does not have any coordinates. Please try another IP: ");
			srcIp = UserInput.ipAddress();
		}

		Coordinates coordsModel = ipMap.get(srcIp).get(0).getCoords();

		ArrayList<String> similarIps = new ArrayList<>();
		for (String ipEntry : ipMap.keySet()) {
			if (coordsModel.equals(ipMap.get(ipEntry).get(0).getCoords())) {
				similarIps.add(ipEntry);
			}
		}
		
		System.out.println("The following IP addresses are located within 1 degree of latitude and longitude of the given IP:");
		
		int i = 0;
		for (String ip : similarIps) {
			System.out.println(" - " + ip);
			i++;
		}
		System.out.println("Total of " + i + " results.");
		
	}

	// HashMap with a specified field as keys, and array of entries that contain those values
	private static HashMap<String, ArrayList<HoneypotData>> toHashMap(ArrayList<HoneypotData> dataset, int key) {
		HashMap<String, ArrayList<HoneypotData>> hMap = new HashMap<>();
		for (int i = 0; i < dataset.size(); i++) {	// loop through dataset
			if (!hMap.containsKey(dataset.get(i).getValueArray()[key])) {	
				hMap.put(dataset.get(i).getValueArray()[key], new ArrayList<HoneypotData>());
			}
			hMap.get(dataset.get(i).getValueArray()[key]).add(dataset.get(i));
		}
		return hMap;
	}

	private static ArrayList<HoneypotData> readFile(String path) throws FileNotFoundException {
		FileReader fr = new FileReader(path);

		Scanner s = new Scanner(fr);

		ArrayList<HoneypotData> scanned = new ArrayList<>();
		s.nextLine(); // skip line with headers
		while (s.hasNext()) {
			scanned.add(new HoneypotData(s.nextLine()));
		}

		s.close();
		return scanned;

	}

	// Removes duplicates using a hashset
	private static <E> ArrayList<E> removeDuplicates(ArrayList<E> l) {
		HashSet<E> hashes = new HashSet<>();
		for (E item : l) {
			hashes.add(item);
		}

		return new ArrayList<>(hashes);

	}

}
