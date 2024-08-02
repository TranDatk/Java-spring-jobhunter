package vn.datk.jobhunter.domain.res.resume;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UpdatedResumeResponse {
    private Instant lastModifiedDate;
    private String lastModifiedBy;
}
