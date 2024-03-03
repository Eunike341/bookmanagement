package com.project.bookmanagement.api;

import com.project.bookmanagement.model.BookDto;
import com.project.bookmanagement.repo.Book;
import com.project.bookmanagement.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("bookmanagement/api")
public class BookManagementRestController {

    private final BookService bookService;

    public BookManagementRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("books")
    public List<BookDto> getBooks() {
        List<Book> bookEntities = bookService.getAllBooks();
        return bookEntities.stream().map(BookDto::new).toList();
    }

    @DeleteMapping("books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("books/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long bookId) {
        Optional<Book> book = bookService.getBookById(bookId);
        return book.map(
                entity -> ResponseEntity.ok(new BookDto(entity)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("books/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("id") Long bookId, @RequestBody BookDto updateValues) {
        System.out.println("======REST received value:" + updateValues.getTitle()+", author:" + updateValues.getAuthor()+", price:" + updateValues.getPrice());
        return ResponseEntity.ok(new BookDto(bookService.updateBook(bookId, convertDtoToBook(updateValues))));
    }

    @PostMapping("books")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto newBook) {
        return ResponseEntity.ok(new BookDto(bookService.saveBook(convertDtoToBook(newBook))));
    }

    private Book convertDtoToBook(BookDto bookDto) {
        Book book = new Book();
        book.setAuthor(bookDto.getAuthor());
        book.setTitle(bookDto.getTitle());
        book.setPrice(new BigDecimal(bookDto.getPrice()));
        return book;
    }
}
