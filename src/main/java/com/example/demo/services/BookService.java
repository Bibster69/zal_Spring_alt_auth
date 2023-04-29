package com.example.demo.services;

import com.example.demo.DTO.BookDTO;
import com.example.demo.models.Book;
import com.example.demo.repos.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService{
    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setPrice(bookDTO.getPrice());

        Book newBook = bookRepository.save(book);

        BookDTO bookResponse = new BookDTO();
        bookResponse.setId(newBook.getId());
        bookResponse.setTitle(newBook.getTitle());
        bookResponse.setDescription(newBook.getDescription());
        bookResponse.setPrice(newBook.getPrice());

        return bookResponse;


    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public BookDTO gteBookByID(int id) {
        Book book = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        return mapToDTO(book);
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO, int id) {
        Book book = bookRepository.findById(id).orElseThrow(RuntimeException::new);

        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setPrice(bookDTO.getPrice());
        bookRepository.save(book);

        return mapToDTO(book);
    }

    @Override
    public void deleteBook(int id) {
        Book book = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        bookRepository.delete(book);
    }

    private BookDTO mapToDTO(Book book){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setPrice(book.getPrice());

        return bookDTO;
    }
}
