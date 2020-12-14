import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserInput {
    public static final Scanner inputScanner = new Scanner(System.in);

    public static String anyString() {
        return anyString(false);
    }

    public static String anyString(boolean allowNull) {
        String in = inputScanner.nextLine();
        if (allowNull && in.length() == 0) {
            return "";
        }
        return in;
    }

    public static int integer() {
        do {
            try {
                String in = anyString();
                return Integer.parseInt(in);
            } catch (Exception e) {
                System.out.print("Input is not an integer. Please try again: ");
            }
        } while (true);
    }

    public static int integer(int min, int max) {
        int in;
        in = integer();
        while (in < min || in > max) {
            System.out.print("Input is outside of range. Please try again: ");
            in = integer();
        }
        return in;
    }
    public static boolean bool() {
        String in = anyString();
        while (!(in.equalsIgnoreCase("Y") || in.equalsIgnoreCase("N"))) {
            System.out.print("Input must be Y/N. Please try again: ");
            in = anyString();
        }

        return in.equalsIgnoreCase("Y");
    }

    public static String datetime() {
        return datetime(false);
    }
    public static String datetime(boolean allowNull) {
        String in;
        String out;
        do {
            in = anyString();
            if (allowNull && in.length() == 0) {
                return "";
            } else {
                try {
                    out = LocalDateTime.parse(in, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")).toString();
                    return out;
                } catch (Exception e) {
                    System.out.print("Could not parse date. Please try again: ");
                }
            }

        } while (true);
    }

    public static String ipAddress() {
        return ipAddress(false);
    }
    public static String ipAddress(boolean allowNull) {
        String zeroTo255 = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])";
        String regex = zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;

        Pattern p = Pattern.compile(regex);

        String out = anyString();
        if (allowNull && out.length() == 0) {
            return "";
        }
        while (!p.matcher(out).matches()) {
            System.out.print("IP address not valid. Please try again: ");
            out = anyString();
        }
        return out;
    }

}
