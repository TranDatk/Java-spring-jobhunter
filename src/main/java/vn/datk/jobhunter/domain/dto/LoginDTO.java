package vn.datk.jobhunter.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank(message = "Email is required")
    @Email(message="Please provide a valid email address")
    @JsonProperty("username")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}
