import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) {

        /* Welcome screen. */
        System.out.println("Welcome to currency converter!");
        Coins userOption = getUserOptionInterface();

        /* Requesting currency to calculate screen. */
        ArrayList<Result> resultsList = new ArrayList<>();
        try {
            /* Show result and ask user to start over screen. */
            double result = calcResultInterface(userOption);
            Result resultToSave = new Result(result, userOption);
            saveResultToList(resultsList, resultToSave);
            String formattedResult = prettyPrintResult(resultToSave);
            System.out.println(formattedResult);
            saveResultToFile(formattedResult, "results.txt");
            while (startOver()) {
                userOption = getUserOptionInterface();
                result = calcResultInterface(userOption);
                resultToSave = new Result(result, userOption);
                saveResultToList(resultsList, resultToSave);
                formattedResult = prettyPrintResult(resultToSave);
                System.out.println(formattedResult);
                saveResultToFile(formattedResult, "results.txt");
            }
            /* Until next time screen */
            System.out.println("Thank you for using our currency converter!");
            System.out.println("Your results list: ");
            for (Result savedResult: resultsList) {
                formattedResult = prettyPrintResult(savedResult);
                System.out.println(formattedResult);
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
        for(String option: possibleOptions) {
            if (input.equalsIgnoreCase(option))
                return true;
        }
        return false;
    }

    static Coins getChosenOption(String input) {
        switch (input) {
            case "1":
                return Coins.USD;
            case "2":
                return Coins.ILS;
            case "3":
                return Coins.EUR;
            default: return null;
        }
    }

    static Coins getUserOptionInterface() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose an option:");
        System.out.println("1 - Converting USD to ILS.");
        System.out.println("2 - Converting ILS to USD.");
        System.out.println("3 - Converting EUR to ILS.");
        String userInput = scanner.nextLine();
        ArrayList<String> validOptions = new ArrayList<>();
        validOptions.add("1");
        validOptions.add("2");
        validOptions.add("3");
        boolean isValidUserInput = validateUserInput(userInput, validOptions);
        while (!isValidUserInput) {
            System.out.println("Invalid option, please type '1', '2' or '3':");
            userInput = scanner.nextLine();
            isValidUserInput = validateUserInput(userInput, validOptions);
        }
        Coins userOption = getChosenOption(userInput);
        return userOption;
    }

    static double calcResultInterface(Coins userOption) throws Exception {
        CoinsFactory coinsFactory = new CoinsFactory();
        Scanner scanner;
        double amountToConvert = 0;
        boolean isValid = false;
        while (!isValid) {
            try {
                System.out.println("Please enter the amount to convert:");
                scanner = new Scanner(System.in);
                amountToConvert = scanner.nextDouble();
                isValid = true;
            } catch(InputMismatchException e) {
                System.out.println("Invalid input, only digits are allowed!");
            }
        }
        Coin chosenCoin = coinsFactory.getCoinInstance(userOption);
        double result = chosenCoin.calculate(amountToConvert);
        return result;
    }

    static void saveResultToList(ArrayList<Result> list, Result resultToSave) {
        try {
            list.add(resultToSave);
        } catch (Exception e) {
            System.out.println("Failed to save or display result.");
            e.printStackTrace();
        }
    }

    static String prettyPrintResult(Result result) {
        String formattedResult = String.format("%.2f", result.getValue());
        switch (result.getType()) {
            case ILS:
                return formattedResult + " USD.";
            case USD: case EUR:
                return formattedResult + " ILS.";
        }
        return null;
    }

    static String getCurrentTimestamp() {
        LocalDateTime currentTimestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return currentTimestamp.format(formatter);
    }

    static void saveResultToFile(String textToSave, String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.write(getCurrentTimestamp() + ": " + textToSave + "\n");
            writer.close();
            System.out.println("Successfully written results into: " + filePath + ".");
        } catch (IOException e) {
            System.out.println("Could not write results to file.");
            e.printStackTrace();
        }
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
