package com.example.chatchat.data.mysql.repository;


import com.example.chatchat.data.mysql.model.Story;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StoryRepository extends CrudRepository<Story, Integer> {
    List<Story> findAllByOwner(String owner);
}
