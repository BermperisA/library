package com.app.library.controllers;

import com.app.library.InitializeLibrary;
import com.app.library.models.User;
import com.app.library.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @GetMapping("/getAllUsersWithBook")
    public ResponseEntity<List<User>> getAllUsersWithBook() {
        return ResponseEntity.ok(userService.getAllUsersWithBorrowedBook());
    }

    @GetMapping("/getAllUsersWhoBorrowedOnDate")
    public ResponseEntity<List<User>> getAllUsersWithBook(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date givenDate) {
        LOGGER.info("Date param " + givenDate.toString());
        return ResponseEntity.ok(userService.getAllUsersWithBorrowedBookOnDate(givenDate));
    }
}
