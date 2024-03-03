package com.project.bookmanagement.service;

import com.project.bookmanagement.repo.Book;
import com.project.bookmanagement.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Create
    public Book saveBook(Book book) {
        book.setCreateDate(LocalDateTime.now());
        book.setUpdateDate(LocalDateTime.now());
        return bookRepository.save(book);
    }

    // Read
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Update
    public Book updateBook(Long id, Book book) {
        System.out.println("======book service book values:" + book.getTitle()+", author:" + book.getAuthor()+", price:" + book.getPrice());
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setPrice(book.getPrice());
            existingBook.setUpdateDate(LocalDateTime.now());
            return bookRepository.save(existingBook);
        } else {
            throw new IllegalArgumentException("Book with id " + id + " not found");
        }
    }

    // Delete
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
