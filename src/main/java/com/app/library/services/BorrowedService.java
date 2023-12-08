package com.app.library.services;

import com.app.library.models.Borrowed;
import com.app.library.repositories.BorrowedRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BorrowedService {

    @Autowired
    private BorrowedRepository borrowedRepository;

    public void saveAll(List<Borrowed> borrowedList) {
        borrowedRepository.saveAll(borrowedList);
    }

    public List<Borrowed> findAll() {
        return borrowedRepository.findAll();
    }
    public List<Borrowed> findBorrowedOnGivenDate(Date givenDate) {
        return borrowedRepository.findBorrowedOnGivenDate(givenDate);
    }
    public Borrowed findById(Long borrowedId) {
        return borrowedRepository.findById(borrowedId).orElseThrow(EntityNotFoundException::new);
    }
}
