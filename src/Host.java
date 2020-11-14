
public enum Host {
	GEU("groucho-eu"),
	GNOR("groucho-norcal"),
	GORE("groucho-oregon"),
	GSA("groucho-sa"),
	GSIN("groucho-singapore"),
	GSYD("groucho-sydney"),
	GTOK("groucho-tokyo"),
	GUSE("groucho-us-east"),
	ZNOR("zeppo-norcal");

	private String value;
	
	Host(String string) {
		this.value = string;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
	
	public static Host getFrom(String str) {
		for (Host h : Host.values()) {
			if (h.toString().equals(str)) {
				return h;
			}
		}
		throw new IllegalArgumentException("Could not find a Host: " + str);
	}

	
}
