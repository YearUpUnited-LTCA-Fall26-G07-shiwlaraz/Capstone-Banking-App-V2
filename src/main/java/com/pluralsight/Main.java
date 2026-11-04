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
                "D) Enter (D) To Add Deposit \n" +
                "P) Enter (P) Make Payment (Debit) \n" +
                "L) Enter (L) Ledger To Check Ledger \n" +
                "X) Enter (X) To Exit  \n" +
                "Enter Your Command: ";
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
                                        "Enter Your Command: "
                        ).toUpperCase();
                        switch(ledgerChoice) {
                            case "A":
                                Transaction.readAllTransactions();
                            case "D":
                                Transaction.readDeposits();
                            case "P":
                                Transaction.readPayments();
                            case "R":
                                int reportChoice;
                                do {
                                    reportChoice = Console.promptForInt(
                                            "=== Reports Menu === \n" +
                                                    "1) Generate report of -- Month To Date -- \n" +
                                                    "2) Generate report of -- Previous Month -- \n" +
                                                    "3) Generate report of -- Year To Date -- \n" +
                                                    "4) Generate report of -- Previous Year --  \n" +
                                                    "5) Generate report by -- Search By The Vendor -- \n" +
                                                    "0) Enter -- 0 -- Back \n" +
                                                    "Enter Your Command: ");
                                    switch(reportChoice) {
                                        case 1:
                                            Transaction.reportMonthToDate();
                                            break;
                                        case 2:
                                            Transaction.reportOfPreviousMonth();
                                            break;
                                        case 3:
                                            Transaction.reportByYearToDate();
                                            break;
                                        case 4:
                                            Transaction.reportByPreviousYear();
                                            break;
                                        case 5:
                                            Transaction.reportByVendor();
                                            break;
                                        default:
                                            System.out.println("Invalid choice! Please try again.");
                                    }
                                } while ( reportChoice != 0);

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
