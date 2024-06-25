package com.example.booksmanagerager.model;

import java.util.HashMap;

public class SanPham {
    private String id;
    private String bookname;
    private String author;
    private int quantity;
    private float price;

    public SanPham() {
    }

    public SanPham(String id, String bookname, String author, int quantity, float price) {
        this.id = id;
        this.bookname = bookname;
        this.author = author;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public HashMap<String, Object> convertHashMap(){
        HashMap<String, Object> work = new HashMap<>();
        work.put("id", id);
        work.put("bookname", bookname);
        work.put("author", author);
        work.put("quantity", quantity);
        work.put("price", price);
        return work;
    }
}
