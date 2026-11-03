package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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



}



