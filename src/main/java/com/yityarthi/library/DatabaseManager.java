package com.yityarthi.library;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class DatabaseManager {
    private final String url;

    public DatabaseManager(String databasePath) {
        this.url = "jdbc:sqlite:" + databasePath;
    }

    public void initialize() throws SQLException {
        try {
            Path path = Path.of(url.substring("jdbc:sqlite:".length()));
            if (path.getParent() != null) Files.createDirectories(path.getParent());
        } catch (Exception e) {
            throw new SQLException("Could not create database directory.", e);
        }

        String sql = """
            CREATE TABLE IF NOT EXISTS issued_books (
                book_id TEXT NOT NULL,
                member_id INTEGER NOT NULL,
                issued_at TEXT DEFAULT CURRENT_TIMESTAMP
            )
            """;

        try (Connection c = DriverManager.getConnection(url);
             Statement s = c.createStatement()) {
            s.execute(sql);
        }
    }

    public void recordIssue(String bookId, int memberId) throws SQLException {
        String sql = "INSERT INTO issued_books(book_id, member_id) VALUES (?, ?)";
        try (Connection c = DriverManager.getConnection(url);
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, bookId);
            p.setInt(2, memberId);
            p.executeUpdate();
        }
    }

    public void recordReturn(String bookId, int memberId) throws SQLException {
        String sql = """
            DELETE FROM issued_books
            WHERE rowid = (
                SELECT rowid FROM issued_books
                WHERE book_id = ? AND member_id = ?
                ORDER BY rowid DESC LIMIT 1
            )
            """;
        try (Connection c = DriverManager.getConnection(url);
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, bookId);
            p.setInt(2, memberId);
            p.executeUpdate();
        }
    }

    public void printIssuedRecords() throws SQLException {
        String sql = "SELECT book_id, member_id, issued_at FROM issued_books ORDER BY rowid";
        try (Connection c = DriverManager.getConnection(url);
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            System.out.println("\n--- JDBC Issued Records ---");
            boolean found = false;
            while (r.next()) {
                found = true;
                System.out.printf("Book: %s | Member: %d | Issued: %s%n",
                        r.getString("book_id"), r.getInt("member_id"),
                        r.getString("issued_at"));
            }
            if (!found) System.out.println("No issued records found.");
        }
    }
}
