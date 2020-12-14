import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HoneypotData {
	private String[] strValues;
	private LocalDateTime datetime;
	private Host host;
	private String srcIpInt;	// String because no arithmetic needs to be done to it
	private Protocol protocol;
	private String packetType;
	private String srcPort;
	private String dstPort;
	private String srcIp;
	private String countryCode;
	private String countryName;
	private String locale;
	private String localeAbbr;
	private String postalCode;
	private Coordinates coords;

	// Set of constants to specify where in the string array these are located
	public static final int DATETIME_INDEX = 0;
	public static final int HOST_INDEX = 1;
	public static final int SRCIPINT_INDEX = 2;
	public static final int PROTOCOL_INDEX = 3;
	public static final int PACKETTYPE_INDEX = 4;
	public static final int SRCPORT_INDEX = 5;
	public static final int DSTPORT_INDEX = 6;
	public static final int SRCIP_INDEX = 7;
	public static final int COUNTRYCODE_INDEX = 8;
	public static final int COUNTRYNAME_INDEX = 9;
	public static final int LOCALE_INDEX = 10;
	public static final int LOCALEABBR_INDEX = 11;
	public static final int POSTALCODE_INDEX = 12;
	public static final int LATITUDE_INDEX = 13;
	public static final int LONGITUDE_INDEX = 14;
	public static final int COORDINATES_INDEX = 15;

	// A makeshift attempt at making this object iterable. Very spaghetti
	public Object get(int index) {
		switch (index) {
			case DATETIME_INDEX:
				return this.datetime;
			case HOST_INDEX:
				return this.host;
			case SRCIPINT_INDEX:
				return this.srcIpInt;
			case PROTOCOL_INDEX:
				return this.protocol;
			case PACKETTYPE_INDEX:
				return this.packetType;
			case SRCPORT_INDEX:
				return this.srcPort;
			case DSTPORT_INDEX:
				return this.dstPort;
			case SRCIP_INDEX:
				return this.srcIp;
			case COUNTRYCODE_INDEX:
				return this.countryCode;
			case COUNTRYNAME_INDEX:
				return this.countryName;
			case LOCALE_INDEX:
				return this.locale;
			case LOCALEABBR_INDEX:
				return this.localeAbbr;
			case POSTALCODE_INDEX:
				return this.postalCode;
			case COORDINATES_INDEX:
				return this.coords;
			default:
				return null;
		}
	}

	// I know it's painful
	public void set(int index, Object newValue) {
		switch (index) {
			case DATETIME_INDEX:
				this.datetime = (LocalDateTime) newValue;
				break;
			case HOST_INDEX:
				this.host = (Host) newValue;
				break;
			case SRCIPINT_INDEX:
				this.srcIpInt = (String) newValue;
				break;
			case PROTOCOL_INDEX:
				this.protocol = (Protocol) newValue;
				break;
			case PACKETTYPE_INDEX:
				this.packetType = (String) newValue;
				break;
			case SRCPORT_INDEX:
				this.srcPort = (String) newValue;
				break;
			case DSTPORT_INDEX:
				this.dstPort = (String) newValue;
				break;
			case SRCIP_INDEX:
				this.srcIp = (String) newValue;
				break;
			case COUNTRYCODE_INDEX:
				this.countryCode = (String) newValue;
				break;
			case COUNTRYNAME_INDEX:
				this.countryName = (String) newValue;
				break;
			case LOCALE_INDEX:
				this.locale = (String) newValue;
				break;
			case LOCALEABBR_INDEX:
				this.localeAbbr = (String) newValue;
				break;
			case POSTALCODE_INDEX:
				this.postalCode = (String) newValue;
				break;
			case COORDINATES_INDEX:
				this.coords = (Coordinates) newValue;
				break;
			default:
				throw new ArrayIndexOutOfBoundsException();
		}
	}

	public HoneypotData(String str) {
		String[] csvValues = str.split(String.valueOf(Start.SEPARATION_CHAR), -1);

		try {
			datetime = LocalDateTime.parse(csvValues[0], DateTimeFormatter.ofPattern("[yyyy-MM-dd HH:mm:ss][dd/MM/yyyy HH:mm]"));	// Both patterns from SampleDataset and LargeDataset
		} catch (Exception e) {
			datetime = null;
			csvValues[DATETIME_INDEX] = "";
		}

		try {
			host = Host.getFrom(csvValues[1]);
		} catch (Exception e) {
			host = null;
			csvValues[HOST_INDEX] = "";
		}
		
		srcIpInt = csvValues[2];

		try {
			protocol = Protocol.valueOf(csvValues[3]);
		} catch (Exception e) {
			protocol = null;
			csvValues[SRCIPINT_INDEX] = "";
		}
		

		packetType = csvValues[PACKETTYPE_INDEX];
		srcPort = csvValues[SRCPORT_INDEX];
		dstPort = csvValues[DSTPORT_INDEX];
		srcIp = csvValues[SRCIP_INDEX];
		countryCode = csvValues[COUNTRYCODE_INDEX];
		countryName = csvValues[COUNTRYNAME_INDEX];
		locale = csvValues[LOCALE_INDEX];
		localeAbbr = csvValues[LOCALEABBR_INDEX];
		postalCode = csvValues[POSTALCODE_INDEX];

		if (!(csvValues[LATITUDE_INDEX].isEmpty() || csvValues[LONGITUDE_INDEX].isEmpty())) {
			coords = new Coordinates(Float.parseFloat(csvValues[LATITUDE_INDEX]), Float.parseFloat(csvValues[LONGITUDE_INDEX]));
		}		

		// Store everything in String array for search purposes
		this.strValues = csvValues;
	}

	public HoneypotData() {
		this.strValues = new String[15];
		for (int i = 0; i < this.strValues.length; i++) {
			this.strValues[i] = "";
		}
	}

	@Override
	public String toString() {
		String coordinates;
		if (coords != null) {
			coordinates = String.valueOf(coords.latitude) + Start.SEPARATION_CHAR + String.valueOf(coords.longitude);
		} else {
			coordinates = String.valueOf(Start.SEPARATION_CHAR);
		}

		return formatDateTime(datetime) + Start.SEPARATION_CHAR + host + Start.SEPARATION_CHAR + srcIpInt + Start.SEPARATION_CHAR + protocol.toString()
				+ Start.SEPARATION_CHAR + packetType + Start.SEPARATION_CHAR + srcPort + Start.SEPARATION_CHAR + dstPort + Start.SEPARATION_CHAR + srcIp + Start.SEPARATION_CHAR
				+ countryCode + Start.SEPARATION_CHAR + countryName + Start.SEPARATION_CHAR + locale + Start.SEPARATION_CHAR + localeAbbr + Start.SEPARATION_CHAR
				+ postalCode + Start.SEPARATION_CHAR + coordinates;
	}

	private String formatDateTime(LocalDateTime datetime) {
		return datetime.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"));
	}

	public String[] getValueArray() {
		return strValues;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		if (datetime == null) {
			strValues[0] = "";
		} else {
			strValues[0] = datetime.toString();
		}
		
		this.datetime = datetime;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		if (host == null) {
			strValues[1] = "";
		} else {
			strValues[1] = host.toString();
		}

		this.host = host;
	}

	public String getSrcIpInt() {
		return srcIpInt;
	}

	public void setSrcIpInt(String srcIpInt) {
		if (srcIpInt == null) {
			strValues[2] = "";
		} else {
			strValues[2] = datetime.toString();
		}

		this.srcIpInt = srcIpInt;
	}

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		if (protocol == null) {
			strValues[3] = "";
		} else {
			strValues[3] = protocol.toString();
		}

		this.protocol = protocol;
	}

	public String getPacketType() {
		return packetType;
	}

	public void setPacketType(String packetType) {
		if (packetType == null) {
			strValues[4] = "";
		} else {
			strValues[4] = packetType;
		}

		this.packetType = packetType;
	}

	public String getSrcPort() {
		return srcPort;
	}

	public void setSrcPort(String srcPort) {
		if (srcPort == null) {
			strValues[5] = "";
		} else {
			strValues[5] = srcPort;
		}

		this.srcPort = srcPort;
	}

	public String getDstPort() {
		return dstPort;
	}

	public void setDstPort(String dstPort) {
		if (dstPort == null) {
			strValues[6] = "";
		} else {
			strValues[6] = dstPort;
		}

		this.dstPort = dstPort;
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		if (srcIp == null) {
			strValues[7] = "";
		} else {
			strValues[7] = srcIp;
		}

		this.srcIp = srcIp;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		if (countryCode == null) {
			strValues[8] = "";
		} else {
			strValues[8] = countryCode;
		}

		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		if (countryName == null) {
			strValues[9] = "";
		} else {
			strValues[9] = countryName;
		}

		this.countryName = countryName;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		if (locale == null) {
			strValues[10] = "";
		} else {
			strValues[10] = locale;
		}

		this.locale = locale;
	}

	public String getLocaleAbbr() {
		return localeAbbr;
	}

	public void setLocaleAbbr(String localeAbbr) {
		if (localeAbbr == null) {
			strValues[11] = "";
		} else {
			strValues[11] = localeAbbr;
		}

		this.localeAbbr = localeAbbr;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		if (postalCode == null) {
			strValues[12] = "";
		} else {
			strValues[12] = postalCode;
		}

		this.postalCode = postalCode;
	}

	public Coordinates getCoords() {
		return coords;
	}

	public void setCoords(Coordinates coords) {
		this.coords = coords;
	}
}
