package com.example.chatchat.data;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.List;

public class Story_safe {
    public Story_safe(Integer id, String content, List<String> img, LocalDateTime create_date, String owner, Integer likes) {
        this.id = id;
        this.content = content;
        this.img = img;
        this.likes = likes;
        this.createDate = create_date;
        this.owner = owner;
    }

    public Story_safe() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int like) {
        this.likes = like;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime create_date) {
        this.createDate = create_date;
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
    private Integer id;
    private List<String> img;
    private int likes;
    private LocalDateTime createDate;
    private String owner;
}
