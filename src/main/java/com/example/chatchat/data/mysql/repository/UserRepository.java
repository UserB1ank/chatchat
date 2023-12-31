package com.example.chatchat.data.mysql.repository;


import com.example.chatchat.data.mysql.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User, String> {
    //User类的主键是account

    boolean existsByAccountAndPassword(String Account, String Password);

    boolean existsByAccount(String username);

    User findByAccount(String account);
}
