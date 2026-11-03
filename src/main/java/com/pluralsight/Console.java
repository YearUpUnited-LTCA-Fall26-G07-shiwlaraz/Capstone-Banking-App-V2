package com.pluralsight;

import java.util.Scanner;

public class Console {
    public static final Scanner myScanner;

    public  Console() {

    }
    public static String promptForString(String prompt) {
        System.out.print(prompt);
        return myScanner.nextLine();
    }
    public static int promptForInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int result = myScanner.nextInt();
                myScanner.nextLine();
                return result;
            } catch (Exception var3) {
                System.out.print("Invalid input, Please enter a number");
                myScanner.nextLine();
            }
        }
    }
    static {
        myScanner = new Scanner(System.in);
    }
}
