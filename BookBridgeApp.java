/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bookbridge_tsa1;

/**
 *
 * @author Gail Jacinto
 */
import java.util.Scanner;

public class BookBridgeApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final int MAX_TRANSACTIONS = 5;
        
        String[] memberIds = new String[MAX_TRANSACTIONS];
        String[] memberNames = new String[MAX_TRANSACTIONS];
        String[] memberTypes = new String[MAX_TRANSACTIONS];
        String[] isbns = new String[MAX_TRANSACTIONS];
        String[] bookTitles = new String[MAX_TRANSACTIONS];
        int[] requestedDays = new int[MAX_TRANSACTIONS];
        boolean[] availabilities = new boolean[MAX_TRANSACTIONS];
        int[] lateDays = new int[MAX_TRANSACTIONS];
        
        String[] statuses = new String[MAX_TRANSACTIONS];
        double[] estimatedFines = new double[MAX_TRANSACTIONS];
        
        System.out.println("  BookBridge Prototype Initialization");
        
        for (int i = 0; i < MAX_TRANSACTIONS; i++) {
            System.out.println("--- Entering Transaction " + (i + 1) + " ---");
            
            do {
                System.out.print("Enter Member ID: ");
                memberIds[i] = scanner.nextLine().trim();
            } while (memberIds[i].isEmpty());
            
            do {
                System.out.print("Enter Member Name: ");
                memberNames[i] = scanner.nextLine().trim();
            } while (memberNames[i].isEmpty());
            
            do {
                System.out.print("Enter Membership Type (STUDENT, FACULTY, GUEST): ");
                memberTypes[i] = scanner.nextLine().trim().toUpperCase();
            } while (!validateMembershipType(memberTypes[i]));
            
            do {
                System.out.print("Enter Book ISBN: ");
                isbns[i] = scanner.nextLine().trim();
            } while (isbns[i].isEmpty());
            
            do {
                System.out.print("Enter Book Title: ");
                bookTitles[i] = scanner.nextLine().trim();
            } while (bookTitles[i].isEmpty());
            
            do {
                System.out.print("Enter Requested Days (1-30): ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid format. Enter Requested Days (1-30): ");
                    scanner.next(); 
                }
                requestedDays[i] = scanner.nextInt();
                scanner.nextLine(); 
            } while (requestedDays[i] < 1 || requestedDays[i] > 30);
            
            System.out.print("Is the book available? (true/false): ");
            while (!scanner.hasNextBoolean()) {
                System.out.print("Invalid format. Is the book available? (true/false): ");
                scanner.next(); 
            }
            availabilities[i] = scanner.nextBoolean();
            scanner.nextLine();
            
            do {
                System.out.print("Enter Estimated Late Days (>= 0): ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid format. Enter Estimated Late Days (>= 0): ");
                    scanner.next(); 
                }
                lateDays[i] = scanner.nextInt();
                scanner.nextLine(); 
            } while (lateDays[i] < 0);
            
            int maxDays = getMaximumDays(memberTypes[i]);
            double dailyFine = getDailyFine(memberTypes[i]);
            
            statuses[i] = classifyRequest(availabilities[i], requestedDays[i], maxDays);
            estimatedFines[i] = lateDays[i] * dailyFine;
            
            System.out.println("\n>> Transaction " + (i + 1) + " Processed");
            System.out.println(">> Status: " + statuses[i]);
            System.out.println(">> Estimated Late Charge: PHP " + estimatedFines[i] + "\n");
        }
        
        printSummary(statuses, requestedDays, estimatedFines);
        
        scanner.close();
    }
    
    public static boolean validateMembershipType(String type) {
        return type.equals("STUDENT") || type.equals("FACULTY") || type.equals("GUEST");
    }
    
    public static int getMaximumDays(String type) {
        return switch (type) {
            case "STUDENT" -> 14;
            case "FACULTY" -> 30;
            case "GUEST" -> 7;
            default -> 0; 
        };
    }
    
    public static double getDailyFine(String type) {
        return switch (type) {
            case "STUDENT" -> 5.0;
            case "FACULTY" -> 2.0;
            case "GUEST" -> 10.0;
            default -> 0.0;
        };
    }
    
    public static String classifyRequest(boolean isAvailable, int reqDays, int maxDays) {
        if (!isAvailable) {
            return "RESERVED";
        } else if (reqDays <= maxDays) {
            return "APPROVED";
        } else {
            return "DENIED";
        }
    }
    
    public static void printSummary(String[] statuses, int[] requestedDays, double[] estimatedFines) {
        int approvedCount = 0;
        int reservedCount = 0;
        int deniedCount = 0;
        int maxReqDays = 0;
        double totalFines = 0.0;
        
        for (int i = 0; i < statuses.length; i++) {
            if (statuses[i].equals("APPROVED")) approvedCount++;
            else if (statuses[i].equals("RESERVED")) reservedCount++;
            else if (statuses[i].equals("DENIED")) deniedCount++;
            
            if (requestedDays[i] > maxReqDays) {
                maxReqDays = requestedDays[i];
            }
            
            totalFines += estimatedFines[i];
        }
        
        System.out.println("      BOOKBRIDGE FINAL SUMMARY       ");
        System.out.println("Total Approved Transactions: " + approvedCount);
        System.out.println("Total Reserved Transactions: " + reservedCount);
        System.out.println("Total Denied Transactions:   " + deniedCount);
        System.out.println("Highest Requested Days:      " + maxReqDays);
        System.out.println("Total Estimated Late Charges: PHP " + totalFines);
    }
}