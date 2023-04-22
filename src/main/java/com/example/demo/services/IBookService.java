package com.example.demo.services;

import com.example.demo.DTO.BookDTO;
import com.example.demo.models.Book;
import com.example.demo.repos.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface IBookService {
    BookDTO createBook(BookDTO bookDTO);
    List<BookDTO> getAllBooks();

    BookDTO gteBookByID(int id);
    BookDTO updateBook(BookDTO bookDTO, int id);

    void deleteBook(int id);

}
