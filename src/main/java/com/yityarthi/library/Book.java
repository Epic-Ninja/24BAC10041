package com.yityarthi.library;

public class Book extends LibraryItem {
    private final String author;
    private boolean available = true;

    public Book(String id, String title, String author) {
        super(id, title);
        this.author = author;
    }

    public String getAuthor() { return author; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String getItemType() { return "Book"; }

    @Override
    public String toString() {
        return getId() + " | " + getTitle() + " | " + author +
                " | " + (available ? "Available" : "Issued");
    }
}
