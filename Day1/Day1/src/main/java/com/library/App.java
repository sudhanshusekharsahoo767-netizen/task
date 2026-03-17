package com.library;

import com.library.dao.LibraryDAO;
import com.library.entity.Book;
import com.library.entity.Member;
import com.library.util.HibernateUtil;

import java.util.List;

public class App {
    public static void main(String[] args) {
        LibraryDAO libraryDAO = new LibraryDAO();

        System.out.println("--- Library Management System Initialization ---");

        // 1. Add some books
        Book book1 = new Book("Effective Java", "Joshua Bloch", "978-0134685991");
        Book book2 = new Book("Clean Code", "Robert C. Martin", "978-0132350884");
        libraryDAO.addBook(book1);
        libraryDAO.addBook(book2);

        // 2. Register some members
        Member member1 = new Member("Alice Smith", "alice@example.com");
        Member member2 = new Member("Bob Jones", "bob@example.com");
        libraryDAO.registerMember(member1);
        libraryDAO.registerMember(member2);

        // 3. List all books
        System.out.println("\n--- Initial List of Books ---");
        printBooks(libraryDAO.listAllBooks());

        // 4. Borrow a book (assuming book1 got ID 1)
        System.out.println("\n--- Borrowing 'Effective Java' ---");
        libraryDAO.borrowBook(1L);

        // 5. List books to see availability changes
        System.out.println("\n--- Books after borrowing ---");
        printBooks(libraryDAO.listAllBooks());

        // 6. Try to borrow the same book again
        System.out.println("\n--- Borrowing 'Effective Java' again ---");
        libraryDAO.borrowBook(1L);

        // 7. Return the book
        System.out.println("\n--- Returning 'Effective Java' ---");
        libraryDAO.returnBook(1L);

        // 8. Final list of books
        System.out.println("\n--- Books after returning ---");
        printBooks(libraryDAO.listAllBooks());

        // Shutdown Hibernate
        HibernateUtil.shutdown();
        System.out.println("\n--- System Shutdown ---");
    }

    private static void printBooks(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }
}
