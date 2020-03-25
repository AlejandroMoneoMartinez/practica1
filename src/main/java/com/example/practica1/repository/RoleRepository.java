package com.example.practica1.repository;

import com.example.practica1.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    @Override
    List<Role> findAll();
    Optional<Role> findByName(String name);
}