package com.app.library.services;

import com.app.library.models.Borrowed;
import com.app.library.models.User;
import com.app.library.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
@Transactional
public class UserService {
    @Autowired
    private BorrowedService borrowedService;

    @Autowired
    private UserRepository userRepository;

    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }
    public User saveUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    public List<User> getAllUsersWithBorrowedBook() {
        List<Borrowed> borrowed = borrowedService.findAll();
        //HashSet for unique users
        Set<User> users = new HashSet<>();
        borrowed.forEach(b -> {
            users.add(findById(b.getBorrower()));
        });
        return new ArrayList<User>(users);
    }
    public List<User> getAllUsersWithBorrowedBookOnDate(Date givenDate) {
        List<Borrowed> borrowed = borrowedService.findBorrowedOnGivenDate(givenDate);
        //HashSet for unique users
        Set<User> users = new HashSet<>();
        borrowed.forEach(b -> {
            users.add(findById(b.getBorrower()));
        });
        return new ArrayList<User>(users);
    }

    public long findIdByFullName(String name, String firstName) {
        return userRepository.findIdByNameAndFirstName(name, firstName);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
    }
}
