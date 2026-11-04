package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;
    private double balance;

    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount, double balance) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.balance = balance;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalance() {
        return balance;
    }

    public static HashMap<LocalDate, List<Transaction>> readTransactions() {
        HashMap<LocalDate, List<Transaction>> transactions = new HashMap<>();

        try {
            FileReader fr1 = new FileReader("transactions.csv");
            BufferedReader br1 = new BufferedReader(fr1);

            String Line;
            br1.readLine();

            while ((Line = br1.readLine()) != null) {
                String[] split = Line.split("[|]");
                LocalDate date = LocalDate.parse(split[0].trim());
                LocalTime time = LocalTime.parse(split[1].trim());
                String description = split[2].trim();
                String vendor = split[3].trim();
                double amount = Double.parseDouble(split[4].trim());
                double balance = Double.parseDouble(split[5].trim());

                Transaction transaction = new Transaction(date, time, description, vendor, amount, balance);
                transactions.computeIfAbsent(date, (LocalDate k) -> new ArrayList<>()).add(transaction);
            }
        } catch (IOException e) {
            System.out.print("Error Reading The file");
        }
        return transactions;

    }
    public static double getLastBalance(){
        double balance = 0.0;
        try{
            FileReader reader = new FileReader("transactions.csv");
            BufferedReader br2 = new BufferedReader(reader);

            String Line;
            br2.readLine();

            while ((Line = br2.readLine()) != null) {
                if (!Line.isBlank()) {
                    String[] data = Line.split("[|]");

                    if (data.length == 6) {
                        balance = Double.parseDouble(data[5]);
                    }
                }
            }
        }catch (IOException e) {
            System.out.println("Error Reading The file" + e.getMessage());
        }return balance;

    }
    public static void writeTransaction(Transaction transaction){

        try (FileWriter fr1 = new FileWriter("transactions.csv", true)) {
            fr1.write( LocalDate.now() + " | " + LocalTime.now().withNano(0) + " | " + transaction.getDescription() + " | " +
                    transaction.getVendor() + " | " + transaction.getAmount() + " | " + transaction.getBalance() + System.lineSeparator());
            System.out.println("Transaction saved successfully!");
        } catch (IOException e) {
            System.out.println( "Error raading file" + e.getMessage());
        }

    }
    public static void addDeposit() {
        String description = Console.promptForString("Enter Description");
        String vendor = Console.promptForString("Enter vendor Name");
        String doubleAmount = Console.promptForString("Enter Deposit Amount");
        double amount = Double.parseDouble(doubleAmount);
        double balance = getLastBalance();
        if (amount <= 0) {
            System.out.println("Amount Must Be Greater Than 0");
        } else {
            Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(),
                    description, vendor, amount, balance + amount);
            writeTransaction(transaction);
        }

    }
    public static void paymentTransactions(){
        String description = Console.promptForString("Enter Description");
        String vendor = Console.promptForString("Enter vendor Name");
        String doubleAmount = Console.promptForString("Enter Deposit Amount");
        double amount =Double.parseDouble(doubleAmount);
        double balance = getLastBalance();
        if (amount <= 0) {
            System.out.println("Amount Must Be less greater than 0");
        } else if (amount > balance) {
            System.out.println("Amount Must Be Should not cross your current balance");
            System.out.println("Insufficient balance!" + getLastBalance());
        }
        else {
            Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(),
                    description, vendor, -amount, balance - amount);
            writeTransaction(transaction);
        }
    }
    public static void readAllTransactions(){
        HashMap<LocalDate, List<Transaction>> transactions = readTransactions();

        List<LocalDate> sortedDates = new ArrayList<>(transactions.keySet());
        Collections.sort(sortedDates, Collections.reverseOrder());

        for (LocalDate date : sortedDates)   {
            transactions.get(date).sort(Comparator.comparing(Transaction::getTime).reversed());

            for (Transaction t : transactions.get(date)) {
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
    public static void readDeposits(){
        HashMap<LocalDate, List<Transaction>> transactions = readTransactions();

        List<Transaction> deposits = new ArrayList<>();

        for (List<Transaction> transactionList : transactions.values()) {
            for (Transaction t : transactionList ) {
                if (t.getAmount() > 0) {
                    deposits.add(t);
                }
            }
        }
        deposits.sort(Comparator.comparing(Transaction::getDate)
                .thenComparing(Transaction::getTime).reversed());
        for (Transaction d : deposits) {
            System.out.printf("%s   |   %s   |   %s   |   %s   |   %.2f   |   %.2f%n",
                    d.getDate(),
                    d.getTime(),
                    d.getDescription(),
                    d.getVendor(),
                    d.getAmount(),
                    d.getBalance());
        }
    }
    public static void readPayments() {
        HashMap<LocalDate, List<Transaction>> transactions = readTransactions();

        List<Transaction> payments = new ArrayList<>();

        for (List<Transaction> transactionList : transactions.values()) {
            for (Transaction t : transactionList ) {
                if (t.getAmount() < 0) {
                    payments.add(t);

                }
            }
        }
        payments.sort(Comparator.comparing(Transaction::getDate)
                .thenComparing(Transaction::getTime).reversed());
        for (Transaction t : payments) {
            System.out.printf("%s   |   %s   |   %s   |   %s   |   %.2f   |   %.2f%n",
                    t.getDate(),
                    t.getTime(),
                    t.getDescription(),
                    t.getVendor(),
                    t.getAmount(),
                    t.getBalance());
        }
    }
    public static void reportMonthToDate(){
        HashMap<LocalDate, List<Transaction>> transactions =readTransactions();

        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate =  LocalDate.now();

        for (LocalDate date : transactions.keySet()) {
            for (Transaction t : transactions.get(date)) {
                if (!date.isBefore(startDate) && !date.isAfter(endDate) ) {
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

    }
    public static void reportOfPreviousMonth() {
        HashMap<LocalDate, List<Transaction>> transactions = readTransactions();

        LocalDate startDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().withDayOfMonth(1).minusDays(1);

        for (LocalDate date : transactions.keySet()) {
            for (Transaction t : transactions.get(date)) {
                if (!date.isBefore(startDate) && !date.isAfter(endDate)) {
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

    }
    public static void reportByYearToDate() {
        HashMap<LocalDate, List<Transaction>> transactions = readTransactions();

        LocalDate startDate = LocalDate.now().withMonth(1).withDayOfMonth(1);
        LocalDate endDate = LocalDate.now();

        for (LocalDate date : transactions.keySet()) {
            for (Transaction t : transactions.get(date)) {
                if (!date.isBefore(startDate) && !date.isAfter(endDate)) {
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
    }
    public static void reportByPreviousYear() {
        HashMap<LocalDate, List<Transaction>> transactions = readTransactions();

        LocalDate startDate = LocalDate.now().minusYears(1).withMonth(1).withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().withMonth(1).minusDays(1);

        for (LocalDate date : transactions.keySet()) {
            for (Transaction t : transactions.get(date)) {
                if (!date.isBefore(startDate) && !date.isAfter(endDate)) {
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
    }
    public static void reportByVendor() {
        String vendor = Console.promptForString("Enter The name of the vendor");

        HashMap<LocalDate, List<Transaction>> transactions = readTransactions();

        for (List<Transaction> transactionList : transactions.values()){
            for (Transaction t: transactionList) {
                if (t.getVendor().equalsIgnoreCase(vendor)) {
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

    }
}



