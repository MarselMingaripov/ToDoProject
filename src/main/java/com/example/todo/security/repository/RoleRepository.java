package com.example.todo.security.repository;

import com.example.todo.security.model.Erole;
import com.example.todo.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(Erole name);
}
