package com.app.library;

import com.app.library.enums.GenderEnum;
import com.app.library.models.Book;
import com.app.library.models.Borrowed;
import com.app.library.models.User;
import com.app.library.services.BookService;
import com.app.library.services.BorrowedService;
import com.app.library.services.UserService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class InitializeLibrary implements ApplicationRunner {
    private final Logger LOGGER = LoggerFactory.getLogger(InitializeLibrary.class);

    @Value("${users.data}")
    private String usersData;
    @Value("${books.data}")
    private String booksData;
    @Value("${borrowed.data}")
    private String borrowedData;
    @Autowired
    private UserService userService;
    private Class<Object> resourceLoader;
    @Autowired
    private BookService bookService;
    @Autowired
    private BorrowedService borrowedService;

    @Override
    public void run(ApplicationArguments arg0) throws Exception {
        LOGGER.info("Hello World from Application Runner");
        initializeDatabase();
    }

    private void initializeDatabase() {

        try {
            //load the users from the csv and insert them to db
            final List<User> users = this.loadUsers(this.usersData);
            this.userService.saveAll(users);
            LOGGER.info(users.size() + " Users loader from CSV");
            //same for the books
            final List<Book> books = this.loadBooks(this.booksData);
            this.bookService.saveAll(books);
            LOGGER.info(books.size() + " Books loader from CSV");
            //same for the borrows
            final List<Borrowed> borrows = this.loadBorrowed(this.borrowedData);
            this.borrowedService.saveAll(borrows);
            LOGGER.info(borrows.size() + " Borrows loader from CSV");
        } catch (Exception e) {
            throw new RuntimeException("Failed to read and parse CSV file, " + e.getMessage());
        }
    }

    private List<User> loadUsers(String path) throws IOException, ParseException {
        List<User> userList = new ArrayList<>();
        SimpleDateFormat dtFr = new SimpleDateFormat("dd/MM/yyyy");
        final Resource resource = new ClassPathResource(path);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            // Read first line to skip headers
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                // split on comma(',')
                String[] csvData = line.split(",");
                if (csvData.length == 5) {
                    String name = csvData[0];
                    String firsName = csvData[1];
                    Date dateSince = csvData[2].isBlank() ? null : dtFr.parse(csvData[2]);
                    Date dateTill = csvData[3].isBlank() ? null : dtFr.parse(csvData[3]);
                    // create car object to store values
                    User user = new User(name, firsName, dateSince, dateTill, GenderEnum.fromString(csvData[4]));
                    // adding car objects to a list
                    userList.add(user);
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("File not found " + path + e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw (e);
        }
        return userList;
    }

    private List<Book> loadBooks(String path) throws IOException {
        List<Book> bookList = new ArrayList<>();
        final Resource resource = new ClassPathResource(path);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            // Read first line to skip headers
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                // split on comma(',')
                String[] csvData = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                //a book should at least have a title and an author
                if (!csvData[0].isBlank() && !csvData[1].isBlank()) {
                    // create car object to store values
                    Book book = new Book(csvData[0].replaceAll("\"", ""), csvData[1].replaceAll("\"", ""), csvData[2], csvData[3]);
                    // adding car objects to a list
                    bookList.add(book);
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("File not found " + path + e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw (e);
        }
        return bookList;
    }

    private List<Borrowed> loadBorrowed(String path) throws IOException, ParseException {
        List<Borrowed> borrowedsList = new ArrayList<>();
        SimpleDateFormat dtFr = new SimpleDateFormat("dd/MM/yyyy");
        final Resource resource = new ClassPathResource(path);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            // Read first line to skip headers
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                // split on comma(',')
                String[] csvData = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                //someone has to borrow a book
                if (!csvData[0].isBlank() && !csvData[1].isBlank()) {
                    String[] fullName = csvData[0].replaceAll("\"", "").split(",");
                    long borrowerId = userService.findIdByFullName(fullName[0], fullName[1]);
                    String book = csvData[1].replaceAll("\"", "");
                    Date dateFrom = csvData[2].isBlank() ? null : dtFr.parse(csvData[2]);
                    Date dateTo = csvData[3].isBlank() ? null : dtFr.parse(csvData[3]);
                    // create car object to store values
                    Borrowed borrowed = new Borrowed(borrowerId, book, dateFrom, dateTo);
                    // adding car objects to a list
                    borrowedsList.add(borrowed);
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("File not found " + path + e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw (e);
        }
        return borrowedsList;
    }
}