package vn.datk.jobhunter.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.datk.jobhunter.util.constant.LevelEnum;

@Entity
@Table(name="jobs")
@Getter
@Setter
public class Job extends AbstractAuditingEntity<Long>{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private double salary;
    private int quantity;
    private LevelEnum level;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

}
