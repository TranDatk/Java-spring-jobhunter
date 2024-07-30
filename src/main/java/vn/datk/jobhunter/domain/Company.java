package vn.datk.jobhunter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "companies")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class Company extends AbstractAuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name" ,length = 100, nullable = false)
    @JsonProperty("name")
    @NotBlank(message = "Name cannot be blank")
    @NotNull(message = "Name cannot be null")
    private String name;

    @Column(name = "description" , columnDefinition = "MEDIUMTEXT")
    @JsonProperty("description")
    private String description;

    @Column(name = "address")
    @JsonProperty("address")
    private String address;

    @Column(name = "logo")
    @JsonProperty("logo")
    private String logo;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    @JsonIgnore
    List<User> users;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Job> jobs;
}
