package org.example.tfj.server.controller;

import org.example.tfj.common.bean.User;
import org.example.tfj.common.exception.ResourceNotFoundException;
import org.example.tfj.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    private User retrieveUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id: " + id));
        return user;
    }

    /*
     * Create
     */
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    /*
     * Read
     */
    @GetMapping
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(this.retrieveUserById(id));
    }

    /*
     * Update
     */
    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updateUser = this.retrieveUserById(id);

        updateUser.setLastName(user.getLastName());
        updateUser.setFirstName(user.getFirstName());
        updateUser.setEmail(user.getEmail());

        userRepository.save(updateUser);

        return ResponseEntity.ok(updateUser);
    }

    /*
     * Delete
     */
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        User user = this.retrieveUserById(id);

        userRepository.delete(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
