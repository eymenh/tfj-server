package org.example.tfj.server.controller;

import org.example.tfj.common.bean.User;
import org.example.tfj.common.exception.ResourceNotFoundException;
import org.example.tfj.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private Map<Integer, User> userMap = new HashMap<>();

    private User retrieveUserById(Integer id) {
        if(userMap.containsKey(id)) {
            return userMap.get(id);
        }
        return null;
    }

    /*
     * Create
     */
    @PostMapping
    public User createUser(@RequestBody User user) {
        Integer key = new Integer(1);

        for (Integer k : userMap.keySet()) {
            key = k + 1;
        }

        user.setId(key);
        userMap.put(key, user);

        return user;
    }

    /*
     * Read
     */
    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<User>(userMap.values());
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(this.retrieveUserById(id));
    }

    /*
     * Update
     */
    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        User updateUser = this.retrieveUserById(id);

        updateUser.setLastName(user.getLastName());
        updateUser.setFirstName(user.getFirstName());
        updateUser.setEmail(user.getEmail());

        userMap.put(id, updateUser);

        return ResponseEntity.ok(updateUser);
    }

    /*
     * Delete
     */
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer id) {
        User user = this.retrieveUserById(id);

        userMap.remove(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
