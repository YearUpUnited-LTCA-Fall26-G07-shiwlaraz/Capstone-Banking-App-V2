package com.pluralsight;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
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
}
