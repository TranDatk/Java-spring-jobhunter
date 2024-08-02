package vn.datk.jobhunter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import vn.datk.jobhunter.util.constant.LevelEnum;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name="jobs")
@Getter
@Setter
public class Job extends AbstractAuditingEntity<Long>{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    private double salary;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private LevelEnum level;

    private boolean active;

    private Instant startDate;

    private Instant endDate;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"jobs"})
    @JoinTable(name = "job_skill", joinColumns = @JoinColumn(name = "job_id"),
    inverseJoinColumns = @JoinColumn(name="skill_id"))
    private List<Skill> skills;

    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Resume> resumes;
}
