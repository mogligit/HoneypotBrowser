public class Coordinates {
    private float lat;
    private float lon;

    private static final float TOLERANCE = 1;    // Accuracy of .equals in degrees

    public Coordinates(float latitude, float longitude) {
        lat = latitude;
        lon = longitude;
    }

    // Returns true if coordinates are within TOLERANCE proximity of each other
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != Coordinates.class) {
            return false;
        }

		Coordinates c = (Coordinates) obj;

        return (Math.abs(lat - c.lat) < TOLERANCE && Math.abs(lon - c.lon) < TOLERANCE);
    }
}
