package com.bcp.moneychange.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcp.moneychange.entity.User;

/**
 * Created by suman.das on 11/28/18.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
