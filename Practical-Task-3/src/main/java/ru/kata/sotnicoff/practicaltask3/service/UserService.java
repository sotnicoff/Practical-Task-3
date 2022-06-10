package ru.kata.sotnicoff.practicaltask3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.sotnicoff.practicaltask3.model.User;
import ru.kata.sotnicoff.practicaltask3.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(long id) {
        return userRepository.getReferenceById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.saveAndFlush(user);
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
