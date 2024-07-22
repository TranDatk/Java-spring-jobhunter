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
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message="Please provide a valid email address")
    @JsonProperty("email")
    private String email;


    @JsonProperty("address")
    private String address;

    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be null")
    private String password;

    @JsonProperty("retypePassword")
    private String retypePassword;

    @JsonProperty("dateOfBirth")
    private Date dateOfBirth;

//    @NotNull(message = "Role ID is required")
//    @JsonProperty("roleId")
//    private Long roleId;
}
