package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "books")
public class YourEntity {

    @Id
    @Indexed(name = "id", type = "string")
    private String id;

    @Indexed(name = "author", type = "string")
    private String author;

    @Indexed(name = "bookname", type = "string")
    private String bookname;

    @Indexed(name = "year", type = "string")
    private String year;

    @Indexed(name = "genre", type = "string")
    private String genre;

    public YourEntity() {
    }

    // Конструктор с параметрами (нужен для удобства создания объектов)
    public YourEntity(String id, String author, String bookname, String year, String genre) {
        this.id = id;
        this.author = author;
        this.bookname = bookname;
        this.year = year;
        this.genre = genre;
    }

    
    // Геттеры и сеттеры для полей

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
