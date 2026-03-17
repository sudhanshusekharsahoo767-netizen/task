package com.library.dao;

import com.library.entity.Book;
import com.library.entity.Member;
import com.library.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class LibraryDAO {

    // Add a new book to the library
    public void addBook(Book book) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(book);
            transaction.commit();
            System.out.println("Book added successfully: " + book.getTitle());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Register a new member
    public void registerMember(Member member) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(member);
            transaction.commit();
            System.out.println("Member registered successfully: " + member.getName());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // List all books
    public List<Book> listAllBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Book> query = session.createQuery("from Book", Book.class);
            return query.list();
        }
    }

    // Borrow a book (set isAvailable to false)
    public void borrowBook(Long bookId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Book book = session.get(Book.class, bookId);
            if (book != null && book.isAvailable()) {
                book.setAvailable(false);
                session.update(book);
                System.out.println("Book borrowed successfully: " + book.getTitle());
            } else {
                System.out.println("Book is not available or does not exist.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Return a book (set isAvailable to true)
    public void returnBook(Long bookId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Book book = session.get(Book.class, bookId);
            if (book != null && !book.isAvailable()) {
                book.setAvailable(true);
                session.update(book);
                System.out.println("Book returned successfully: " + book.getTitle());
            } else {
                System.out.println("Book is already available or does not exist.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
