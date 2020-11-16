import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class HoneypotData implements Comparable<HoneypotData> {
	public LocalDateTime datetime;
	public Host host;
	public String sourceInt;
	public Protocol protocol;
	public String packetType;
	public String srcPort;
	public String dptPort;
	public String sourceIp;
	public String countryCode;
	public String countryName;
	public String locale;
	public String localeAbbr;
	public String postalCode;
	public String latitude;
	public String longitude;

	public HoneypotData(String str) {
		String[] values = str.split(String.valueOf(Start.SV), -1);

		try {
			datetime = LocalDateTime.parse(values[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		} catch (Exception e) {
			datetime = LocalDateTime.parse(values[0], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		}
		

		host = Host.getFrom(values[1]);
		sourceInt = values[2];

		protocol = Protocol.valueOf(values[3]);

		packetType = values[4];
		srcPort = values[5];
		dptPort = values[6];
		sourceIp = values[7];
		countryCode = values[8];
		countryName = values[9];
		locale = values[10];
		localeAbbr = values[11];
		postalCode = values[12];
		latitude = values[13];
		longitude = values[14];
	}

	public HoneypotData() {
	}

	@Override
	public String toString() {
		return formatDateTime(datetime) + Start.SV + host + Start.SV + sourceInt + Start.SV + protocol.toString()
				+ Start.SV + packetType + Start.SV + srcPort + Start.SV + dptPort + Start.SV + sourceIp + Start.SV
				+ countryCode + Start.SV + countryName + Start.SV + locale + Start.SV + localeAbbr + Start.SV
				+ postalCode + Start.SV + latitude + Start.SV + longitude;
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

		return (this.datetime.equals(h.datetime) && this.host.equals(h.host) && this.sourceInt.equals(h.sourceInt)
				&& this.protocol.equals(h.protocol) && this.packetType.equals(h.packetType)
				&& this.srcPort.equals(h.srcPort) && this.dptPort.equals(h.dptPort) && this.sourceIp.equals(h.sourceIp)
				&& this.countryCode.equals(h.countryCode) && this.countryName.equals(h.countryName)
				&& this.locale.equals(h.locale) && this.localeAbbr.equals(h.localeAbbr)
				&& this.postalCode.equals(h.postalCode) && this.latitude.equals(h.latitude)
				&& this.longitude.equals(h.longitude));
	}

	public boolean softEquals(Object o) { // soft because it ignores null fields
		if (o == null) {
			return false;
		}
		if (o.getClass() != HoneypotData.class) {
			return false;
		}

		HoneypotData h = (HoneypotData) o;

		return ((this.datetime.equals(h.datetime) || h.datetime == null) && (this.host.equals(h.host) || h.host == null)
				&& (this.sourceInt.equals(h.sourceInt) || h.sourceInt == null)
				&& (this.protocol.equals(h.protocol) || h.protocol == null)
				&& (this.packetType.equals(h.packetType) || h.packetType == null)
				&& (this.srcPort.equals(h.srcPort) || h.srcPort == null)
				&& (this.dptPort.equals(h.dptPort) || h.dptPort == null)
				&& (this.sourceIp.equals(h.sourceIp) || h.sourceIp == null)
				&& (this.countryCode.equals(h.countryCode) || h.countryCode == null)
				&& (this.countryName.equals(h.countryName) || h.countryName == null)
				&& (this.locale.equals(h.locale) || h.locale == null)
				&& (this.localeAbbr.equals(h.localeAbbr) || h.localeAbbr == null)
				&& (this.postalCode.equals(h.postalCode) || h.postalCode == null)
				&& (this.latitude.equals(h.latitude) || h.latitude == null)
				&& (this.longitude.equals(h.longitude) || h.longitude == null));
	}

	static final Comparator<HoneypotData> countryComparator = new Comparator<HoneypotData>() {
		public int compare(HoneypotData hpd1, HoneypotData hpd2) {
			return hpd1.countryName.compareTo(hpd2.countryName);
		}
	};
	static final Comparator<HoneypotData> hostComparator = new Comparator<HoneypotData>() {
		public int compare(HoneypotData hpd1, HoneypotData hpd2) {
			return hpd1.host.compareTo(hpd2.host);
		}
	};
}
