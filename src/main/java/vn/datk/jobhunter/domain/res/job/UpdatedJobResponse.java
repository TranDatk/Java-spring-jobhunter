package vn.datk.jobhunter.domain.res.job;

import lombok.Getter;
import lombok.Setter;
import vn.datk.jobhunter.util.constant.LevelEnum;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class UpdatedJobResponse {
    private Long id;
    private String name;
    private String location;
    private double salary;
    private int quantity;
    private LevelEnum level;
    private boolean isActive;
    private String description;
    private Instant startDate;
    private Instant endDate;
    private String lastModifiedBy;
    private Instant lastModifiedDate;
    private List<String> skills;
}
