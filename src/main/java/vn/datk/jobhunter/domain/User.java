package vn.datk.jobhunter.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.List;

@Entity
@Table(name="users")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(value = { "refreshToken"}, allowGetters = true)
public class User extends AbstractAuditingEntity<Long> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name" ,length = 100)
    private String name;

    @Column(name = "age" ,length = 3)
    private int age;

    @Column(name = "email" ,length = 50, nullable = false)
    @NotBlank(message = "Email is required")
    @NotNull(message = "Email cannot be null")
    @Email(message="Please provide a valid email address")
    private String email;

    @Column(name = "address" ,length = 200)
    private String address;

    @Column(name = "password" ,length = 200)
    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be null")
    private String password;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Column(name = "refresh_token", columnDefinition = "MEDIUMTEXT")
    @JsonProperty("refresh_token")
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Resume> resumes;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
