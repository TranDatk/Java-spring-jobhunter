package vn.datk.jobhunter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.datk.jobhunter.domain.User;
import vn.datk.jobhunter.domain.response.ResultPaginationResponse;
import vn.datk.jobhunter.service.UserService;

import java.util.List;
import java.util.Optional;

@RequestMapping(path = "${apiPrefix}/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws Exception {
        User newUser = this.userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("")
    public ResponseEntity<ResultPaginationResponse> getAllUser(
            @RequestParam("current") Optional<Integer> current,
            @RequestParam("pageSize") Optional<Integer> pageSize
            ){
        Pageable pageable = PageRequest.of(
                current.isPresent() ? current.get() - 1 : 0,
                pageSize.isPresent() ? pageSize.get() : 5
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.userService.getAllUser(pageable));
    }
}
