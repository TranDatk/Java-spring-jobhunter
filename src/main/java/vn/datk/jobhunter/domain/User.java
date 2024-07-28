package vn.datk.jobhunter.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import vn.datk.jobhunter.util.constant.GenderEnum;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="users")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(value = { "refreshToken"}, allowGetters = true)
public class User extends AbstractAuditingEntity<Long> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name" ,length = 100)
    @JsonProperty("name")
    private String name;

    @Column(name = "age" ,length = 3)
    @JsonProperty("age")
    private int age;

    @Column(name = "phone_number" ,length = 20, nullable = false)
    @NotBlank(message = "Phone number is required")
    @Size(min = 5, message = "Phone number must be at least 5 characters")
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @Column(name = "email" ,length = 50, nullable = false)
    @NotBlank(message = "Email is required")
    @NotNull(message = "Email cannot be null")
    @Email(message="Please provide a valid email address")
    @JsonProperty("email")
    private String email;

    @Column(name = "address" ,length = 200)
    @JsonProperty("address")
    private String address;

    @Column(name = "password" ,length = 200)
    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be null")
    private String password;

    @Column(name = "gender")
    @JsonProperty("gender")
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Column(name = "refresh_token", columnDefinition = "MEDIUMTEXT")
    @JsonProperty("refreshToken")
    private String refreshToken;
}
