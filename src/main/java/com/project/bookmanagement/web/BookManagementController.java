package com.project.bookmanagement.web;

import com.project.bookmanagement.model.BookDto;
import com.project.bookmanagement.service.WebService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("bookmanagement/web")
public class BookManagementController {
    private final WebService webService;

    public BookManagementController(WebService webService) {
        this.webService = webService;
    }

    @GetMapping("/books")
    public Mono<String> listBooks(Model model) {
        Flux<BookDto> booksFlux = webService.fetchBooks();
        return booksFlux.collectList().doOnNext(books -> model.addAttribute("books", books)).thenReturn("book-list");
        /*return booksFlux.collectList().flatMap(books -> {
            model.addAttribute("books", books);
            return Mono.just("book-list");
        });*/
    }

    @DeleteMapping("books/{id}")
    public String deleteBook(@PathVariable("id") Long bookId, RedirectAttributes redirectAttributes) {
        Mono<Void> mono = webService.deleteBook(bookId);
        mono.block();
        // Add a flash attribute to indicate successful deletion
        redirectAttributes.addFlashAttribute("message", "Book deleted successfully");

        // Redirect back to the list page
        return "redirect:/bookmanagement/web/books";
    }

    @GetMapping("/books/{id}/edit")
    public Mono<String> editBookPage(@PathVariable("id") Long bookId, Model model) {
        Mono<BookDto> bookMono = webService.getBookById(bookId);
        return bookMono.map(bookDto -> {
            model.addAttribute("book", bookDto);
            return "edit-book";
        });
    }

    @PatchMapping("/books/{id}")
    public Mono<String> editBook(@PathVariable("id") Long bookId, @ModelAttribute BookDto book, Model model) {
        Mono<BookDto> bookMono = webService.updateBook(bookId, book);
        return bookMono.map(bookDto -> {
            System.out.println("=====map is called");
            model.addAttribute("book", bookDto);
            model.addAttribute("message", "Update successful");
            return "edit-book";
        });
    }

    @GetMapping("/books/create")
    public String createBookPage(Model model) {
        model.addAttribute("book", new BookDto());
        return "edit-book";
    }

    @PostMapping("/books")
    public Mono<String> createBook(@ModelAttribute BookDto book, Model model) {
        Mono<BookDto> bookMono = webService.createBook(book);
        return bookMono.map(bookDto -> {
            model.addAttribute("book", bookDto);
            model.addAttribute("message", "Add successful");
            return "edit-book";
        });
    }
}
