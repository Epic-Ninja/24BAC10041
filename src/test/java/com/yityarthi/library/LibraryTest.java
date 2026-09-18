package com.yityarthi.library;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    @Test
    void canAddBookAndMember() throws Exception {
        Library lib = new Library(new DatabaseManager("data/test1.db"));
        lib.addBook(new Book("B1", "Java Basics", "Author"));
        lib.addMember(new Member(1, "Member"));
        assertEquals(1, lib.getBooks().size());
        assertEquals(1, lib.getMembers().size());
    }

    @Test
    void issueAndReturnWork() throws Exception {
        Library lib = new Library(new DatabaseManager("data/test2.db"));
        lib.addBook(new Book("B2", "OOP", "Author"));
        lib.addMember(new Member(2, "Member"));
        lib.issueBook("B2", 2);
        assertFalse(lib.findBook("B2").isAvailable());
        lib.returnBook("B2", 2);
        assertTrue(lib.findBook("B2").isAvailable());
    }

    @Test
    void unavailableBookThrowsException() throws Exception {
        Library lib = new Library(new DatabaseManager("data/test3.db"));
        lib.addBook(new Book("B3", "Exceptions", "Author"));
        lib.addMember(new Member(3, "One"));
        lib.addMember(new Member(4, "Two"));
        lib.issueBook("B3", 3);
        assertThrows(BookNotAvailableException.class,
                () -> lib.issueBook("B3", 4));
    }
}
