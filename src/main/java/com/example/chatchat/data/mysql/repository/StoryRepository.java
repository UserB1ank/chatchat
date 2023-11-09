package com.example.chatchat.data.mysql.repository;


import com.example.chatchat.data.mysql.model.Story;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Set;


public interface StoryRepository extends JpaRepository<Story, Integer> {
    //主键是id
    Set<Story> findAllByOwner(String owner, PageRequest pageable);
    List<Story> findAllSortedBy(Sort by, PageRequest pageRequest);
}
