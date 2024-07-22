package vn.datk.jobhunter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.datk.jobhunter.dto.LoginDTO;

@RequestMapping(path = "${apiPrefix}/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @PostMapping(path = "/login")
    public ResponseEntity<Void> login(@RequestBody LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
//xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication =
                this.authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return ResponseEntity.ok().body(null);
    }
}
