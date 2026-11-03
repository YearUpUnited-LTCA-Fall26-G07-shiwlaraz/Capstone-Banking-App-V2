package com.pluralsight;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        showHomeScreen();


        HashMap<LocalDate, List<Transaction>> readTransactions = Transaction.readTransactions();
        for (LocalDate date : readTransactions.keySet()) {
            for (Transaction t : readTransactions.get(date)){
                System.out.printf("%s   |   %s   |   %s   |   %s   |   %.2f   |   %.2f%n",
                        t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendor(),
                        t.getAmount(),
                        t.getBalance());
            }
        }
    }
    public static void showHomeScreen(){
        String mainMenuTexts = "=== Manu Options ===\n" +
                "D) Add Deposit \n" +
                "P) Make Payment (Debit) \n" +
                "L) Ledger \n" +
                "X) Exit \n" +
                "Enter Your Command";
        String choice;
        do {
            choice = Console.promptForString(mainMenuTexts).toUpperCase();
            switch (choice) {
                case "D":
                    Transaction.addDeposit();
                    break;
                case "P":
                    Transaction.paymentTransactions();
                    break;
                case "L":
                    String ledgerChoice;
                    do {
                        ledgerChoice = Console.promptForString(
                                "=== Menu Options === \n" +
                                        "A) All -- Get All Transactions \n" +
                                        "D) Deposit -- Get all deposit transactions \n" +
                                        "P) Payments -- Get all payment transactions\n" +
                                        "R) Report -- Create a report \n" +
                                        "H) To go back \n" +
                                        "Enter Your Command\n"
                        ).toUpperCase();
                        switch(ledgerChoice) {
                            case "A":
                                Transaction.readAllTransactions();
                            case "D":
                                Transaction.readDeposits();
                            default:
                                System.out.println("Invalid choice! Please try again.");

                        }
                    }while (!ledgerChoice.equals("H"));
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (!choice.equals("X"));
    }
}
