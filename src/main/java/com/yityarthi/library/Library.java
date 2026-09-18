package com.yityarthi.library;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Library implements Persistable {
    private final List<Book> books = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private final DatabaseManager database;

    public Library(DatabaseManager database) throws SQLException {
        this.database = database;
        database.initialize();
    }

    public void addBook(Book book) throws LibraryException {
        if (findBook(book.getId()) != null)
            throw new LibraryException("A book with this ID already exists.");
        books.add(book);
    }

    public void addMember(Member member) throws LibraryException {
        if (findMember(member.getId()) != null)
            throw new LibraryException("A member with this ID already exists.");
        members.add(member);
    }

    public Book findBook(String id) {
        for (Book b : books)
            if (b.getId().equalsIgnoreCase(id)) return b;
        return null;
    }

    public Member findMember(int id) {
        for (Member m : members)
            if (m.getId() == id) return m;
        return null;
    }

    public void issueBook(String bookId, int memberId)
            throws LibraryException, SQLException {
        Book book = findBook(bookId);
        if (book == null) throw new LibraryException("Book not found.");
        if (findMember(memberId) == null) throw new LibraryException("Member not found.");
        if (!book.isAvailable())
            throw new BookNotAvailableException("Book is already issued.");

        book.setAvailable(false);
        database.recordIssue(bookId, memberId);
    }

    public void returnBook(String bookId, int memberId)
            throws LibraryException, SQLException {
        Book book = findBook(bookId);
        if (book == null) throw new LibraryException("Book not found.");
        if (book.isAvailable())
            throw new LibraryException("Book is not currently issued.");

        book.setAvailable(true);
        database.recordReturn(bookId, memberId);
    }

    public List<Book> getBooks() { return List.copyOf(books); }
    public List<Member> getMembers() { return List.copyOf(members); }

    @Override
    public void saveToFile(String path) throws IOException {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(path))) {
            for (Book b : books) {
                w.write("BOOK|" + b.getId() + "|" + b.getTitle() + "|" +
                        b.getAuthor() + "|" + b.isAvailable());
                w.newLine();
            }
            for (Member m : members) {
                w.write("MEMBER|" + m.getId() + "|" + m.getName());
                w.newLine();
            }
        }
    }

    @Override
    public void loadFromFile(String path) throws IOException {
        File f = new File(path);
        if (!f.exists()) return;

        books.clear();
        members.clear();

        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] p = line.split("\\|", -1);
                if (p[0].equals("BOOK") && p.length == 5) {
                    Book b = new Book(p[1], p[2], p[3]);
                    b.setAvailable(Boolean.parseBoolean(p[4]));
                    books.add(b);
                } else if (p[0].equals("MEMBER") && p.length == 3) {
                    members.add(new Member(Integer.parseInt(p[1]), p[2]));
                }
            }
        }
    }
}
