package com.orientdb.demo.domain;

import java.util.Date;

/**
 * @author Erik Pragt
 */
public class Book {

    private String title;
    private int pages;
    private Date publicationDate;

    public Book(String title, int pages, Date publicationDate) {
        this.title = title;
        this.pages = pages;
        this.publicationDate = publicationDate;
    }

    public String getTitle() {
        return title;
    }

    public int getPages() {
        return pages;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }
}
