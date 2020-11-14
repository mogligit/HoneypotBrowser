import java.util.Scanner;

public class UserInput {
    public static final Scanner inputScanner = new Scanner(System.in);

    public static String anyString() {
        return inputScanner.nextLine();
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

}
