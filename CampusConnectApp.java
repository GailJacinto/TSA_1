/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.campusconnect_tsa1;

/**
 *
 * @author Gail Jacinto
 */
import java.util.Scanner;

public class CampusConnectApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        final int MAX_REQUESTS = 5;
        final int MIN_PRIORITY = 1;
        final int MAX_PRIORITY = 5;
        
        String[] studentIds = new String[MAX_REQUESTS];
        String[] studentNames = new String[MAX_REQUESTS];
        String[] categories = new String[MAX_REQUESTS];
        int[] priorities = new int[MAX_REQUESTS];
        String[] descriptions = new String[MAX_REQUESTS];
        
        int[] estimatedDays = new int[MAX_REQUESTS];
        
        System.out.println(" CampusConnect System Initialization ");
        
        for (int i = 0; i < MAX_REQUESTS; i++) {
            System.out.println("--- Entering Request " + (i + 1) + " ---");
            
            do {
                System.out.print("Enter Student ID: ");
                studentIds[i] = scanner.nextLine().trim();
            } while (studentIds[i].isEmpty());
            
            do {
                System.out.print("Enter Student Name: ");
                studentNames[i] = scanner.nextLine().trim();
            } while (studentNames[i].isEmpty());
            
            do {
                System.out.print("Enter Category (ENROLLMENT, DOCUMENT, PAYMENT, ADVISING): ");
                categories[i] = scanner.nextLine().trim().toUpperCase();
            } while (!validateCategory(categories[i]));
            
            do {
                System.out.print("Enter Priority (1-5): ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. Enter Priority (1-5): ");
                    scanner.next(); // Clear bad input
                }
                priorities[i] = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } while (priorities[i] < MIN_PRIORITY || priorities[i] > MAX_PRIORITY);
            
            do {
                System.out.print("Enter Short Concern Description: ");
                descriptions[i] = scanner.nextLine().trim();
            } while (descriptions[i].isEmpty());
            
            estimatedDays[i] = estimateDays(categories[i], priorities[i]);
            
            printTicket(studentIds[i], studentNames[i], categories[i], priorities[i], estimatedDays[i]);
        }
        
        printSummary(categories, priorities, estimatedDays);
        
        scanner.close();
    }

    public static boolean validateCategory(String category) {
        return category.equals("ENROLLMENT") || 
               category.equals("DOCUMENT") || 
               category.equals("PAYMENT") || 
               category.equals("ADVISING");
    }
    
    public static int estimateDays(String category, int priority) {
        int days = switch (category) {
            case "ENROLLMENT" -> 3;
            case "DOCUMENT" -> 2;
            case "PAYMENT" -> 1;
            case "ADVISING" -> 4;
            default -> 0;
        };
        
        if (priority == 5) {
            days -= 1;
        }
        
        if (days < 1) {
            days = 1;
        }
        
        return days;
    }

    public static void printTicket(String id, String name, String category, int priority, int estDays) {
        System.out.println("\n>> --- TICKET ACCEPTED ---");
        System.out.println(">> Student: " + name + " (" + id + ")");
        System.out.println(">> Category: " + category + " | Priority: " + priority);
        System.out.println(">> Estimated Completion: " + estDays + " working day(s)\n");
    }
    
    public static void printSummary(String[] categories, int[] priorities, int[] estimatedDays) {
        int countEnrollment = 0, countDocument = 0, countPayment = 0, countAdvising = 0;
        int highestPriority = 0;
        double totalDays = 0.0;
        
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].equals("ENROLLMENT")) countEnrollment++;
            else if (categories[i].equals("DOCUMENT")) countDocument++;
            else if (categories[i].equals("PAYMENT")) countPayment++;
            else if (categories[i].equals("ADVISING")) countAdvising++;
            
            if (priorities[i] > highestPriority) {
                highestPriority = priorities[i];
            }
            
            totalDays += estimatedDays[i];
        }
        
        double averageDays = totalDays / categories.length;
        
        System.out.println("    CAMPUSCONNECT FINAL SUMMARY      ");
        System.out.println("Requests per Category:");
        System.out.println("  ENROLLMENT: " + countEnrollment);
        System.out.println("  DOCUMENT:   " + countDocument);
        System.out.println("  PAYMENT:    " + countPayment);
        System.out.println("  ADVISING:   " + countAdvising);
        System.out.println("Highest Priority Logged: " + highestPriority);
        System.out.println("Average Estimated Days:  " + averageDays);
    }
}