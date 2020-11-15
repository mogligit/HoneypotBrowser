import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UserInput {
    public static final Scanner inputScanner = new Scanner(System.in);

    public static String anyString() {
        return inputScanner.nextLine();
    }
    
    public static String anyStringOrNull() {
        String in = inputScanner.nextLine();
        if (in.length() == 0) {
            return null;
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

    public static LocalDateTime datetime() {
        String in;
        LocalDateTime out = null;
        boolean valid = false;
        do {
            in = anyString();
            if (in.length() == 0) {
                valid = true;
            } else {
                try {
                    out = LocalDateTime.parse(in, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                    valid = true;
                } catch (DateTimeParseException e) {
                    System.out.print("Could not parse date. Please try again: ");
                }
            }

        } while (!valid);

        return out;
    }

}
