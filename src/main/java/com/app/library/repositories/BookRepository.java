package com.app.library.repositories;

import com.app.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT * FROM LIBRARY_BOOKS b WHERE "
            + "b.TITLE not in (Select TITLE from LIBRARY_BORROWED lb WHERE  b.TITLE=lb.BOOK and CURDATE() between lb.DATE_FROM and lb.DATE_TO)",
            nativeQuery = true)
    List<Book> findAvailableBooks();
}
