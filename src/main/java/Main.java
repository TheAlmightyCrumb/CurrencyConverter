import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CoinsFactory coinsFactory = new CoinsFactory();
        Scanner scanner = new Scanner(System.in);

        /* Welcome screen. */
        System.out.println("Welcome to currency converter!");
        System.out.println("Please choose an option:");
        System.out.println("1 - Converting USD to ILS.");
        System.out.println("2 - Converting ILS to UDS.");
        String userInput = scanner.next();
        boolean isValidUserInput = validateUserInput(userInput);
        while (!isValidUserInput) {
           userInput = scanner.next();
           isValidUserInput = validateUserInput(userInput);
        }
        Coins usersOption = getChosenOption(userInput);

        /* Requesting currency to calculate screen. */
        ArrayList<Double> resultsList = new ArrayList<>();
        System.out.println("Please enter the amount to convert:");
        double amountToConvert = scanner.nextDouble();
        try {
            Coin chosenCoin = coinsFactory.getCoinInstance(usersOption);
            double result = chosenCoin.calculate(amountToConvert);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    static boolean validateUserInput(String input) {
        if (input == null)
            return false;
        if (input.length() > 1)
            return false;
        return input.equalsIgnoreCase("1") || input.equalsIgnoreCase("2");
    }

    static Coins getChosenOption(String input) {
        switch (input) {
            case "1":
                return Coins.USD;
            case "2":
                return Coins.ILS;
            default: return null;
        }
    }
}
