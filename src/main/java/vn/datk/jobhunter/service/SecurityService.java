package vn.datk.jobhunter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import vn.datk.jobhunter.domain.res.auth.LoginResponse;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecurityService {
    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;
    private final JwtEncoder jwtEncoder;

    @Value("${datk.jwt.base64-secret}")
    private String jwtKey;

    @Value("${datk.jwt.access-token-validity-in-seconds}")
    private long accessTokenExpiration;

    @Value("${datk.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    public String createAccessToken(Authentication authentication, LoginResponse loginResponse) {
        Instant now = Instant.now();
        Instant validity = now.plus(this.accessTokenExpiration, ChronoUnit.SECONDS);

        // Lấy các authorities từ Authentication
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Lấy thông tin bổ sung từ principal (nếu có)
        Object principal = authentication.getPrincipal();
        String email = (principal instanceof UserDetails)
                ? ((UserDetails) principal).getUsername()
                : authentication.getName();

        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(authentication.getName())
                .claim("roles", roles)
                .claim("user", loginResponse.getUser())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public String createRefreshToken(Authentication authentication, LoginResponse loginResponse) {
        Instant now = Instant.now();
        Instant validity = now.plus(this.refreshTokenExpiration, ChronoUnit.SECONDS);

        // Lấy các authorities từ Authentication
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Lấy thông tin bổ sung từ principal (nếu có)
        Object principal = authentication.getPrincipal();
        String email = (principal instanceof UserDetails)
                ? ((UserDetails) principal).getUsername()
                : authentication.getName();

        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(authentication.getName())
                .claim("roles", roles)
                .claim("user", loginResponse.getUser())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
}
