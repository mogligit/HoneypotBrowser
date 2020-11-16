import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

public class Start {

	public static final char SV = ',';

	public static void main(String[] args) {
		List<HoneypotData> dataset;
		dataset = new ArrayList<HoneypotData>();

		try {
			
			dataset = readFile("LargeDataset-Honeypots.csv");

			

			System.out.println("Dataset loaded correctly.");
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException. Dataset not found.");
		}

		System.out.println("Total: " + dataset.size() + " entries");
		do {
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
				case 0:
					return;
				default:
					break;
			}
		} while (true);

	}

	private static int menu() {
		System.out.println("\n-- Honeypot attack browser --\n");
		System.out.println("1. List all countries");
		System.out.println("2. Hosts attacked by country");
		System.out.println("3. Search attack");
		System.out.println("4. Search IP address");
		System.out.println("5. Search Host");
		System.out.println("6. Top offending IP address");
		System.out.println("0. QUIT");
		System.out.print("\nPlease choose one: ");

		return UserInput.integer(0, 7);
	}

	private static void displayCountries(List<HoneypotData> dataset) {
		List<String> countries = getCountryList(dataset); // always make a copy of data set
		System.out.println("List of countries:");
		for (String country : countries) {
			System.out.println(" - " + country);
		}
		System.out.println("Total of " + countries.size() + " countries.");
	}

	private static void displayHostsPerCountry(List<HoneypotData> dataset) {
		System.out.println("Hosts attacked per country");

		List<String> countryList = getCountryList(new ArrayList<>(dataset)); // Copy dataset so it doesn't get affected

		System.out.print("Input country: ");
		String countryInput = UserInput.anyString();
		if (!countryList.contains(countryInput)) {
			System.out.println("Country was not found.");
			return;
		}

		System.out.println("\nCountry: " + countryInput + "\nHosts attacked:");

		List<String> hosts = new ArrayList<>();

		for (HoneypotData entry : dataset) {
			if (entry.countryName.equals(countryInput)) {
				hosts.add(entry.host.toString());
			}
		}

		Collections.sort(hosts);
		removeDuplicates(hosts);

		for (String hostString : hosts) {
			System.out.println(" - " + hostString);
		}
		System.out.println("Total of " + hosts.size());

	}

	private static void searchAttack(List<HoneypotData> dataset) {
		System.out.println("Search - press enter to leave blank");
		HoneypotData searchModel = new HoneypotData();

		System.out.print("Date/time (dd/MM/yyyy hh:mm): ");
		searchModel.datetime = UserInput.datetime(true);

		System.out.print("Host: ");
		try {
			searchModel.host = Host.getFrom(UserInput.anyString(true));
		} catch (Exception e) {
			System.out.println("Host not valid. Leaving blank.");
		}

		System.out.print("Protocol: ");
		try {
			searchModel.protocol = Protocol.valueOf(UserInput.anyString(true));
		} catch (Exception e) {
			System.out.println("Protocol not valid. Leaving blank.");
		}

		System.out.print("Source port: ");
		searchModel.srcPort = UserInput.anyString(true);

		System.out.print("Destination port: ");
		searchModel.dptPort = UserInput.anyString(true);

		System.out.print("Source IPv4: ");
		searchModel.sourceIp = UserInput.ipAddress(true);

		System.out.print("Country: ");
		searchModel.countryName = UserInput.anyString(true);

		System.out.print("Locale: ");
		searchModel.locale = UserInput.anyString(true);

		System.out.println("\nResults: ");
		int i = 0;
		for (HoneypotData entry : dataset) {
			if (entry.softEquals(searchModel)) {
				System.out.println(entry.toString());
				i++;
			}
		}
		System.out.println("Total of " + i + " results.");
	}

	private static void searchIp(List<HoneypotData> dataset) {
		System.out.print("Input IPv4 address: ");
		String inputIp = UserInput.ipAddress();

		List<HoneypotData> attacks = getAttacksFromIp(dataset, inputIp);

		System.out.println("List of attacks from " + inputIp);
		for (HoneypotData attack : attacks) {
			System.out.println(attack.host.toString() + ", " + attack.datetime.toString());
		}
		System.out.println("Total of " + attacks.size() + " attacks.");
	}

	private static void searchHost(List<HoneypotData> dataset) {
		System.out.print("Input host name: ");
		Host inputHost;
		try {
			inputHost = Host.getFrom(UserInput.anyString());
		} catch (IllegalArgumentException e) {
			System.out.println("Host not recognised.");
			return;
		}

		List<HoneypotData> attacks = new ArrayList<>();

		for (HoneypotData entry : dataset) {
			if (entry.host.equals(inputHost)) {
				attacks.add(entry);
			}
		}

		System.out.println("List of attacks to host " + inputHost);
		for (HoneypotData attack : attacks) {
			System.out.println(attack.sourceIp + ", " + attack.countryName + ", " + attack.locale);
		}
		System.out.println("Total of " + attacks.size() + " attacks.");
	}

	private static void topOffendingIpAddress(List<HoneypotData> dataset) {
		HashMap<String, Integer> ipAttackCount = new HashMap<>();

		for (HoneypotData entry : dataset) {
			if (ipAttackCount.containsKey(entry.sourceIp)) {
				int oldCount = ipAttackCount.get(entry.sourceIp); // get old count
				ipAttackCount.replace(entry.sourceIp, oldCount + 1); // increment it
			} else {
				ipAttackCount.put(entry.sourceIp, 1);
			}
		}

		int max = 0;
		String topIp = "";
		for (Entry<String, Integer> entry : ipAttackCount.entrySet()) { // finding max value
			if (entry.getValue() > max) {
				max = entry.getValue();
				topIp = entry.getKey();
			}
		}
		System.out.println("Top offending IP: " + topIp);

		List<HoneypotData> attacks = getAttacksFromIp(dataset, topIp);

		System.out.println("Attacks:");
		for (HoneypotData attack : attacks) {
			System.out.println(attack.host.toString() + ", " + attack.datetime.toString());
		}
		System.out.println("Total of " + attacks.size() + " attacks.");
		System.out.println("(outputting it again in case you missed it) Top offending IP: " + topIp);
	}

	private static List<String> getCountryList(List<HoneypotData> dataset) {
		removeDuplicates(dataset, HoneypotData.countryComparator);
		Collections.sort(dataset, HoneypotData.countryComparator); // Nice alphabetical order
		List<String> countryList = new ArrayList<String>();

		for (HoneypotData entry : dataset) {
			if (entry.countryName.length() != 0) { // Filter out empty entries
				countryList.add(entry.countryName);
			}
		}
		return countryList;
	}

	private static List<HoneypotData> getAttacksFromIp(List<HoneypotData> dataset, String ip) {
		List<HoneypotData> attacks = new ArrayList<>();

		for (HoneypotData entry : dataset) {
			if (entry.sourceIp.equals(ip)) {
				attacks.add(entry);
			}
		}

		return attacks;
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

	// Removes duplicates using compareTo()
	// This algorithm assumes that duplicates are contiguous
	private static void removeDuplicates(List<HoneypotData> l, Comparator<HoneypotData> comparator) {
		for (int i = 0; i < l.size() - 1; i++) {
			for (int j = i + 1; j < l.size(); j++) {
				if (l.get(i).compareTo(l.get(j), comparator) == 0) {
					l.remove(j);
					j--;
				}
			}
		}
	}

	// Removes whole entry duplicates using equals()
	private static void removeDuplicates(List<?> l) {
		for (int i = 0; i < l.size() - 1; i++) {
			for (int j = i + 1; j < l.size(); j++) {
				if (l.get(i).equals(l.get(j))) {
					l.remove(j);
					j--;
				}
			}
		}
	}

}
