import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class HoneypotData implements Comparable<HoneypotData> {	
	private LocalDateTime datetime;
	private Host host;
	private String sourceInt;
	private Protocol protocol;
	private String packetType;
	private String srcPort;
	private String dptPort;
	private String sourceIp;
	private String countryCode;
	private String countryName;
	private String locale;
	private String localeAbbr;
	private String postalCode;
	private String latitude;
	private String longitude;
	
	public HoneypotData(String str) {
		String[] values = str.split(String.valueOf(Start.SV), -1);
		
		datetime = LocalDateTime.parse(values[0], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		
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
		localeAbbr = values [11];
		postalCode = values[12];
		latitude = values[13];
		longitude = values[14];
	}
	
	@Override
	public String toString() {
		return formatDateTime(datetime) + Start.SV + host + Start.SV + sourceInt + Start.SV + protocol.toString() + Start.SV + packetType + Start.SV + srcPort + Start.SV + dptPort + Start.SV + sourceIp + Start.SV + countryCode + Start.SV + countryName + Start.SV + locale + Start.SV + localeAbbr + Start.SV + postalCode + Start.SV + latitude + Start.SV + longitude;
	}
	private String formatDateTime(LocalDateTime datetime) {
		return datetime.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"));
	}

	@Override
	public int compareTo(HoneypotData o) {
		return this.datetime.compareTo(o.datetime);
	}

	static final Comparator<HoneypotData> countryOrder = new Comparator<HoneypotData>() {
		public int compare(HoneypotData hpd1, HoneypotData hpd2) {
			return hpd1.countryName.compareTo(hpd2.countryName);
		}
	};
}
