package ru.kata.sotnicoff.springrestful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.sotnicoff.springrestful.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
    Set<Role> findByIdIn(List<Long> ids);
}
