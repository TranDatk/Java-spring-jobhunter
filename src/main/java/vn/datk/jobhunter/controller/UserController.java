package vn.datk.jobhunter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.datk.jobhunter.domain.User;
import vn.datk.jobhunter.service.UserService;

@RequestMapping(path = "${apiPrefix}/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws Exception {
        User newUser = this.userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
