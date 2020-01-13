package com.bcp.moneychange.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcp.moneychange.entity.Role;

/**
 * Created by suman.das on 11/28/18.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
