import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HoneypotData {	
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
	
	public HoneypotData(String str) implements Comparable<HoneypotData> {
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
		return String.format(formatDateTime(datetime) + "%s" + host + "%s" + sourceInt + "%s" + protocol.toString() + "%s" + packetType + "%s" + srcPort + "%s" + dptPort + "%s" + sourceIp + "%s" + countryCode + "%s" + countryName + "%s" + locale + "%s" + localeAbbr + "%s" + postalCode + "%s" + latitude + "%s" + longitude, Start.SV);
	}
	private String formatDateTime(LocalDateTime datetime) {
		return datetime.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"));
	}
}
