package vn.datk.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.datk.jobhunter.domain.User;
import vn.datk.jobhunter.domain.response.ResultPaginationResponse;
import vn.datk.jobhunter.service.UserService;
import vn.datk.jobhunter.util.annotation.ApiMessage;

import java.util.List;
import java.util.Optional;

@RequestMapping(path = "${apiPrefix}/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("")
    @ApiMessage("Create a user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws Exception {
        User newUser = this.userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("")
    @ApiMessage("Fetch all user data")
    public ResponseEntity<ResultPaginationResponse> getAllUser(
            @Filter Specification<User> spec,
            Pageable pageable
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.userService.getAllUser(pageable, spec));
    }
}
