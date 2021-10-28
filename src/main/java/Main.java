import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /* Welcome screen. */
        System.out.println("Welcome to currency converter!");
        Coins userOption = getUserOptionInterface();

        /* Requesting currency to calculate screen. */
        ArrayList<String> resultsList = new ArrayList<>();
        try {
            /* Show result and ask user to start over screen. */
            double result = calcResultInterface(userOption);
            saveResultToList(resultsList, userOption, result);
            while (startOver()) {
                userOption = getUserOptionInterface();
                result = calcResultInterface(userOption);
                saveResultToList(resultsList, userOption, result);
            }
            /* Until next time screen */
            System.out.println("Thank you for using our currency converter!");
            System.out.println("Your results list: ");
            for (String savedResult: resultsList) {
                System.out.println(savedResult);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    static boolean validateUserInput(String input, ArrayList<String> possibleOptions) {
        if (input == null)
            return false;
        if (input.length() > 1)
            return false;
        return input.equalsIgnoreCase(possibleOptions.get(0)) || input.equalsIgnoreCase(possibleOptions.get(1));
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

    static Coins getUserOptionInterface() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose an option:");
        System.out.println("1 - Converting USD to ILS.");
        System.out.println("2 - Converting ILS to UDS.");
        String userInput = scanner.nextLine();
        ArrayList<String> validOptions = new ArrayList<>();
        validOptions.add("1");
        validOptions.add("2");
        boolean isValidUserInput = validateUserInput(userInput, validOptions);
        while (!isValidUserInput) {
            System.out.println("Invalid option, please type '1' or '2':");
            userInput = scanner.nextLine();
            isValidUserInput = validateUserInput(userInput, validOptions);
        }
        Coins userOption = getChosenOption(userInput);
        return userOption;
    }

    static double calcResultInterface(Coins userOption) throws Exception {
        CoinsFactory coinsFactory = new CoinsFactory();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the amount to convert:");
        double amountToConvert = scanner.nextDouble();
        Coin chosenCoin = coinsFactory.getCoinInstance(userOption);
        double result = chosenCoin.calculate(amountToConvert);
        return result;
    }

    static void saveResultToList(ArrayList<String> list, Coins userOption, double result) {
        try {
            list.add(prettyPrintResult(userOption, result));
        } catch (Exception e) {
            System.out.println("Failed to save or display result.");
            e.printStackTrace();
        }
    }

    static String prettyPrintResult(Coins userOption, double result) {
        String formattedResult = String.format("%.2f", result);
        switch (userOption) {
            case ILS:
                System.out.println("RESULT: " + formattedResult + " USD.");
                return formattedResult + " USD.";
            case USD:
                System.out.println("RESULT: " + formattedResult + " ILS.");
                return formattedResult + " ILS.";
        }
        return null;
    }

    static boolean startOver() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to perform another calculation? Type 'Y'/'N':");
        String answer = scanner.nextLine();
        ArrayList<String> validOptions = new ArrayList<>();
        validOptions.add("Y");
        validOptions.add("N");
        boolean isValidUserInput = validateUserInput(answer, validOptions);
        while (!isValidUserInput) {
            System.out.println("Invalid input, please type 'Y'/'N':");
            answer = scanner.nextLine();
            isValidUserInput = validateUserInput(answer, validOptions);
        }
        if (answer.equalsIgnoreCase("N"))
            return false;
        return true;
    }
}
