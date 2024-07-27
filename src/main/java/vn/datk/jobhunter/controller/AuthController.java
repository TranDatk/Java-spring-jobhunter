package vn.datk.jobhunter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.datk.jobhunter.domain.User;
import vn.datk.jobhunter.domain.dto.LoginDTO;
import vn.datk.jobhunter.domain.res.auth.LoginResponse;
import vn.datk.jobhunter.service.SecurityService;
import vn.datk.jobhunter.service.UserService;
import vn.datk.jobhunter.util.annotation.ApiMessage;

@RequestMapping(path = "${apiPrefix}/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityService securityService;
    private final UserService userService;

    @PostMapping(path = "/login")
    @ApiMessage("Login by credential")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        Authentication authentication =
                this.authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);


        LoginResponse loginResponse = new LoginResponse();

        User currentUserDB = this.userService.handleGetUserByUsername(loginDTO.getUsername());
        if(currentUserDB != null){
            LoginResponse.UserLogin userLogin = new LoginResponse.UserLogin(
                    currentUserDB.getId(),
                    currentUserDB.getEmail(),
                    currentUserDB.getName()
            );
            loginResponse.setUser(userLogin);
        }

        loginResponse.setAccessToken(this.securityService.createToken(authentication));
        return ResponseEntity.ok().body(loginResponse);
    }
}
