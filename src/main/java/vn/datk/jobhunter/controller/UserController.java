package vn.datk.jobhunter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import vn.datk.jobhunter.domain.RestResponse;
import vn.datk.jobhunter.domain.User;
import vn.datk.jobhunter.dto.UserDTO;
import vn.datk.jobhunter.service.UserService;

import java.util.List;

@RequestMapping(path = "${apiPrefix}/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) throws Exception {
        User user = modelMapper.map(userDTO, User.class);
        User newUser = this.userService.createUser(user);
        UserDTO newUserDTO = modelMapper.map(newUser, UserDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUserDTO);
    }
}
