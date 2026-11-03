package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                LocalDate date = LocalDate.parse(split[0]);
                LocalTime time = LocalTime.parse(split[1]);
                String description = split[2];
                String vendor = split[3];
                double amount = Double.parseDouble(split[4]);
                double balance = Double.parseDouble(split[5]);

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
        String doubleAmount = Console.promptForString("Enter Depoit Amount");
        double amount = Double.parseDouble(doubleAmount);
        double balance = getLastBalance();
        if (amount <= 0) {
            System.out.println("Amount Must Be Greater Than 0");
        } else {
            Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount, balance + amount);
            writeTransaction(transaction);
        }

    }
    public static void paymentTransactions(){
        String description = Console.promptForString("Enter Description");
        String vendor = Console.promptForString("Enter vendor Name");
        String doubleAmount = Console.promptForString("Enter Depoit Amount");
        double amount =Double.parseDouble(doubleAmount);
        double balance = getLastBalance();
        if (amount <= 0) {
            System.out.println("Amount Must Be less then Current Balance");
        } else if (amount > balance) {
            System.out.println("Insufficient balance!");
        }
        else {
            Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, -amount, balance - amount);
            writeTransaction(transaction);
        }
    }

}



