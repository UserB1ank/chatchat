package com.example.chatchat.data.mysql.repository;

import com.example.chatchat.data.mysql.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    //主键是id
    Comment findByid(Integer id);

    boolean existsByid(Integer id);
}
