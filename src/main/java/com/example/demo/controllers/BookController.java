package com.example.demo.controllers;

import com.example.demo.DTO.BookDTO;
import com.example.demo.models.Book;
import com.example.demo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books/")
public class BookController {

    @Autowired
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("getAllBooks")
    public ResponseEntity<List<BookDTO>> getAllBooks(){
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("book/{id}")
    public ResponseEntity<BookDTO> getBookByID(@PathVariable int id) {
        return ResponseEntity.ok(bookService.gteBookByID(id));
    }

    @PostMapping("book/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookdto){
//        System.out.println(bookdto.getId());
//        System.out.println(bookdto.getTitle());
//        System.out.println(bookdto.getDescription());
//        System.out.println(bookdto.getPrice());
        return new ResponseEntity<>(bookService.createBook(bookdto), HttpStatus.CREATED);
    }

    @PutMapping("book/update/{id}")
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO,
                                           @PathVariable("id") int id){
        BookDTO response = bookService.updateBook(bookDTO, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("book/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") int id){
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted");
    }
}
