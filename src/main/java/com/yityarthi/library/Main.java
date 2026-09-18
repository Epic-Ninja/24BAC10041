package com.yityarthi.library;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final String DATA_FILE = "data/library_data.txt";

    public static void main(String[] args) {
        try {
            Library library = new Library(new DatabaseManager("data/library.db"));
            library.loadFromFile(DATA_FILE);

            try (Scanner sc = new Scanner(System.in)) {
                menu(sc, library);
            }
        } catch (Exception e) {
            System.err.println("Application error: " + e.getMessage());
        }
    }

    private static void menu(Scanner sc, Library lib) {
        while (true) {
            System.out.println("""

                ===== LIBRARY MANAGEMENT SYSTEM =====
                1. Add book
                2. Add member
                3. List books
                4. List members
                5. Issue book
                6. Return book
                7. Save data
                8. Load data
                9. View issued records (JDBC)
                10. Run statistics task
                0. Exit
                """);
            System.out.print("Enter choice: ");

            try {
                switch (sc.nextLine().trim()) {
                    case "1" -> addBook(sc, lib);
                    case "2" -> addMember(sc, lib);
                    case "3" -> lib.getBooks().forEach(System.out::println);
                    case "4" -> lib.getMembers().forEach(System.out::println);
                    case "5" -> issue(sc, lib);
                    case "6" -> returnBook(sc, lib);
                    case "7" -> { lib.saveToFile(DATA_FILE); System.out.println("Data saved."); }
                    case "8" -> { lib.loadFromFile(DATA_FILE); System.out.println("Data loaded."); }
                    case "9" -> new DatabaseManager("data/library.db").printIssuedRecords();
                    case "10" -> statistics(lib);
                    case "0" -> {
                        lib.saveToFile(DATA_FILE);
                        System.out.println("Data saved. Goodbye.");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (LibraryException | SQLException | java.io.IOException e) {
                System.out.println("Operation failed: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void addBook(Scanner sc, Library lib) throws LibraryException {
        System.out.print("Book ID: ");
        String id = sc.nextLine();
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();
        lib.addBook(new Book(id, title, author));
        System.out.println("Book added.");
    }

    private static void addMember(Scanner sc, Library lib) throws LibraryException {
        System.out.print("Member ID: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Member name: ");
        String name = sc.nextLine();
        lib.addMember(new Member(id, name));
        System.out.println("Member added.");
    }

    private static void issue(Scanner sc, Library lib)
            throws LibraryException, SQLException {
        System.out.print("Book ID: ");
        String book = sc.nextLine();
        System.out.print("Member ID: ");
        int member = Integer.parseInt(sc.nextLine());
        lib.issueBook(book, member);
        System.out.println("Book issued successfully.");
    }

    private static void returnBook(Scanner sc, Library lib)
            throws LibraryException, SQLException {
        System.out.print("Book ID: ");
        String book = sc.nextLine();
        System.out.print("Member ID: ");
        int member = Integer.parseInt(sc.nextLine());
        lib.returnBook(book, member);
        System.out.println("Book returned successfully.");
    }

    private static void statistics(Library lib) {
        StatisticsTask t = new StatisticsTask(lib);
        t.start();
        try { t.join(); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
