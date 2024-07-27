package vn.datk.jobhunter.domain.res.auth;

import lombok.*;

@Getter
@Setter
public class LoginResponse {
    private String accessToken;
    private UserLogin user;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLogin{
        private Long id;
        private String email;
        private String name;
    }
}
