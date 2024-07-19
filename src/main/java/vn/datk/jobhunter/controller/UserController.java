package vn.datk.jobhunter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.datk.jobhunter.service.UserService;

@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public String getHelloWorld() {
        return "Hello World (Hỏi Dân IT & Eric)";
    }
}
