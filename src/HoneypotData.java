import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;

public class HoneypotData implements Comparable<HoneypotData> {
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
	private String latitude;
	private String longitude;

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
		srcIp = csvValues[SRCIPINT_INDEX];
		countryCode = csvValues[COUNTRYCODE_INDEX];
		countryName = csvValues[COUNTRYNAME_INDEX];
		locale = csvValues[LOCALE_INDEX];
		localeAbbr = csvValues[LOCALEABBR_INDEX];
		postalCode = csvValues[POSTALCODE_INDEX];
		latitude = csvValues[LATITUDE_INDEX];
		longitude = csvValues[LONGITUDE_INDEX];

		// Store everything in String array for search purposes
		this.strValues = csvValues;
	}

	public HoneypotData() {
	}

	@Override
	public String toString() {
		return formatDateTime(datetime) + Start.SEPARATION_CHAR + host + Start.SEPARATION_CHAR + srcIpInt + Start.SEPARATION_CHAR + protocol.toString()
				+ Start.SEPARATION_CHAR + packetType + Start.SEPARATION_CHAR + srcPort + Start.SEPARATION_CHAR + dstPort + Start.SEPARATION_CHAR + srcIp + Start.SEPARATION_CHAR
				+ countryCode + Start.SEPARATION_CHAR + countryName + Start.SEPARATION_CHAR + locale + Start.SEPARATION_CHAR + localeAbbr + Start.SEPARATION_CHAR
				+ postalCode + Start.SEPARATION_CHAR + latitude + Start.SEPARATION_CHAR + longitude;
	}

	private String formatDateTime(LocalDateTime datetime) {
		return datetime.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"));
	}

	@Override
	public int compareTo(HoneypotData o) {
		return this.datetime.compareTo(o.datetime);
	}

	// Compares certain fields
	public int compareTo(HoneypotData o, Comparator<HoneypotData> c) {
		return c.compare(this, o);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o.getClass() != HoneypotData.class) {
			return false;
		}

		HoneypotData h = (HoneypotData) o;

		return Arrays.equals(this.strValues, h.strValues);
	}

	static final Comparator<HoneypotData> countryComparator = new Comparator<HoneypotData>() {
		public int compare(HoneypotData hpd1, HoneypotData hpd2) {
			return hpd1.countryName.compareTo(hpd2.countryName);
		}
	};

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

	public String getSourceInt() {
		return srcIpInt;
	}

	public void setSourceInt(String sourceInt) {
		if (sourceInt == null) {
			strValues[2] = "";
		} else {
			strValues[2] = datetime.toString();
		}

		this.srcIpInt = sourceInt;
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
			strValues[4] = packetType.toString();
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
			strValues[5] = srcPort.toString();
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
			strValues[6] = dstPort.toString();
		}

		this.dstPort = dstPort;
	}

	public String getSourceIp() {
		return srcIp;
	}

	public void setSourceIp(String sourceIp) {
		if (sourceIp == null) {
			strValues[7] = "";
		} else {
			strValues[7] = sourceIp.toString();
		}

		this.srcIp = sourceIp;
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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		if (latitude == null) {
			strValues[13] = "";
		} else {
			strValues[13] = latitude;
		}

		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		if (longitude == null) {
			strValues[14] = "";
		} else {
			strValues[14] = longitude;
		}

		this.longitude = longitude;
	}
}
