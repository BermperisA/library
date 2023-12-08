package com.app.library.repositories;

import com.app.library.models.Borrowed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BorrowedRepository extends JpaRepository<Borrowed, Long> {
    @Query(value = "SELECT * FROM LIBRARY_BORROWED lb WHERE lb.DATE_FROM = ?1 ",
            nativeQuery = true)
    List<Borrowed> findBorrowedOnGivenDate(Date givenDate);
}
