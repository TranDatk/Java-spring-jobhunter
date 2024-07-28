package vn.datk.jobhunter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import vn.datk.jobhunter.domain.User;
import vn.datk.jobhunter.domain.dto.LoginDTO;
import vn.datk.jobhunter.domain.res.auth.LoginResponse;
import vn.datk.jobhunter.util.error.IdInvalidException;
import vn.datk.jobhunter.util.security.SecurityUtils;
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
    private final SecurityUtils securityUtils;

    @Value("${datk.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

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

        //set access token
        loginResponse.setAccessToken(this.securityService.createAccessToken(authentication.getName(), loginResponse));
        //create refresh token
        String refreshToken = this.securityService.createRefreshToken(authentication.getName(), loginResponse);
        //update user
        this.userService.updateUserToken(refreshToken, loginDTO.getUsername());
        //set cookies
        ResponseCookie responseCookie = ResponseCookie
                .from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(loginResponse);
    }

    @GetMapping("/account")
    @ApiMessage("Get user information")
    public ResponseEntity<LoginResponse.UserLogin> getAccount(){
        String email = SecurityUtils.getCurrentUserLogin().isPresent()
                ? SecurityUtils.getCurrentUserLogin().get()
                : "";
        User currentUserDB = this.userService.handleGetUserByUsername(email);
        LoginResponse.UserLogin userLogin = new LoginResponse.UserLogin();
        if(currentUserDB != null){
            userLogin.setId(currentUserDB.getId());
            userLogin.setEmail(currentUserDB.getEmail());
            userLogin.setName(currentUserDB.getName());
        }
        return ResponseEntity.ok().body(userLogin);
    }

    @GetMapping("/refresh")
    @ApiMessage("Get user information")
    public ResponseEntity<LoginResponse> getRefreshToken(
            @CookieValue("refresh_token") String refreshToken
    ) throws IdInvalidException {
        Jwt decodedToken = this.securityUtils.checkValidRefreshToken(refreshToken);
        String email = decodedToken.getSubject();
        User user = this.userService.getUserByRefreshTokenAndEmail(refreshToken, email);
        if(user != null){
            LoginResponse loginResponse = new LoginResponse();
            LoginResponse.UserLogin userLogin = new LoginResponse.UserLogin(
                    user.getId(),
                    user.getEmail(),
                    user.getName()
            );
            loginResponse.setUser(userLogin);

            //set access token
            loginResponse.setAccessToken(this.securityService.createAccessToken(email, loginResponse));
            //create refresh token
            String new_refresh_token = this.securityService.createRefreshToken(email, loginResponse);
            //update user
            this.userService.updateUserToken(new_refresh_token, email);
            //set cookies
            ResponseCookie responseCookie = ResponseCookie
                    .from("refresh_token", new_refresh_token)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(refreshTokenExpiration)
                    .build();
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body(loginResponse);
        }else{
            throw new IdInvalidException("Refresh token is not valid");
        }
    }

    @PostMapping("/logout")
    @ApiMessage("Logout user")
    public ResponseEntity<Void> logout() throws IdInvalidException{
        String email = SecurityUtils.getCurrentUserLogin().isPresent()
                ? SecurityUtils.getCurrentUserLogin().get()
                : "";
        if(email.equals("")){
            throw new IdInvalidException("Access token is not valid");
        }
        this.userService.updateUserToken(null, email);

        ResponseCookie deleteSpringCookie = ResponseCookie
                .from("refresh_token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteSpringCookie.toString())
                .body(null);
    }
}
