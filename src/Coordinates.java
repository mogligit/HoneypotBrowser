public class Coordinates {
    public final float latitude;
    public final float longitude;

    private static final float TOLERANCE = 1;    // Accuracy of .equals in degrees

    public Coordinates(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Returns true if coordinates are within TOLERANCE proximity of each other
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != Coordinates.class) {
            return false;
        }

		Coordinates c = (Coordinates) obj;

        return (Math.abs(latitude - c.latitude) < TOLERANCE && Math.abs(longitude - c.longitude) < TOLERANCE);
    }
}
