package com.orientdb.demo.domain;

import com.orientechnologies.orient.core.id.ORID;

import javax.persistence.Id;
import java.util.List;

/**
 * @author Erik Pragt
 */
public class Author {

    private String name;
    private List<Book> books;

    Author() {
    }

    @Id
    private ORID rid;

    public ORID getRid() {
        return rid;
    }

    public Author(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return books;
    }
}
