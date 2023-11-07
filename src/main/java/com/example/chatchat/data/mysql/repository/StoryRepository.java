package com.example.chatchat.data.mysql.repository;


import com.example.chatchat.data.mysql.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StoryRepository extends JpaRepository<Story, Integer> {
//主键是id
}
