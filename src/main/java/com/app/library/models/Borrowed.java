package com.app.library.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "library_borrowed")
public class Borrowed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long borrower;
    private String book;
    private Date dateFrom;
    private Date dateTo;

    public Borrowed(){}
    public Borrowed(Long borrower, String book, Date dateFrom, Date dateTo) {
        this.borrower = borrower;
        this.book = book;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Long getBorrower() {
        return borrower;
    }

    public void setBorrower(Long borrower) {
        this.borrower = borrower;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public Date getFrom() {
        return dateFrom;
    }

    public void setFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getTo() {
        return dateTo;
    }

    public void setTo(Date dateTo) { this.dateTo = dateTo; }
}
