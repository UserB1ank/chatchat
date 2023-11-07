package com.example.chatchat.data.mysql.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Story {
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public LocalDate getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDate create_date) {
        this.create_date = create_date;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    private String content;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String img;
    private int like;
    private LocalDate create_date;
    private String owner;
}
