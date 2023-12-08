package com.app.library.services;

import com.app.library.models.Book;
import com.app.library.models.User;
import com.app.library.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    public void saveAll(List<Book> books){
        bookRepository.saveAll(books);
    }

    public Book findById(Long bookId){
        return bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
    }
    public List<Book> findAvailableBooks(){
        return bookRepository.findAvailableBooks();
    }
}
