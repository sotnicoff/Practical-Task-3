package ru.kata.sotnicoff.practicaltask3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.sotnicoff.practicaltask3.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}