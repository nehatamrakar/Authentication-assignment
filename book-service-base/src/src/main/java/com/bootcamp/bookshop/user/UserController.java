package com.bootcamp.bookshop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ResponseEntity<UserResponse> create(@RequestBody CreateUserRequest userRequest) throws InvalidEmailException {
        User user = userService.create(userRequest);
        return new ResponseEntity<>(new UserResponse(user), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UpdateUserRequest updateUserRequest) throws UserNotFoundException {
        userService.update(id, updateUserRequest);
        return ResponseEntity.accepted().build();
    }
}
