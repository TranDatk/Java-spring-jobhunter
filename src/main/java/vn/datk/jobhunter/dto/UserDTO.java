package vn.datk.jobhunter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class UserDTO {
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "Phone number is required")
    @Size(min = 5, message = "Phone number must be at least 5 characters")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message="Please provide a valid email address")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId;

    @JsonProperty("google_account_id")
    private int googleAccountId;

    @NotNull(message = "Role ID is required")
    @JsonProperty("role_id")
    private Long roleId;
}
