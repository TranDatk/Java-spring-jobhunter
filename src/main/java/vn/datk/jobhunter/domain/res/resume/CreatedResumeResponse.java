package vn.datk.jobhunter.domain.res.resume;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CreatedResumeResponse {
    private long id;
    private Instant createdDate;
    private String createdBy;
}
