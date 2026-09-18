package com.yityarthi.library;

public class StatisticsTask extends Thread {
    private final Library library;

    public StatisticsTask(Library library) {
        this.library = library;
    }

    @Override
    public void run() {
        int total = library.getBooks().size();
        long available = library.getBooks().stream()
                .filter(Book::isAvailable).count();

        System.out.println("[Background Task] Total books: " + total +
                " | Available: " + available +
                " | Issued: " + (total - available));
    }
}
