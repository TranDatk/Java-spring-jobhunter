package vn.datk.jobhunter.domain.res.resume;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.datk.jobhunter.domain.Job;
import vn.datk.jobhunter.domain.User;
import vn.datk.jobhunter.util.constant.ResumeStateEnum;

import java.time.Instant;

@Getter
@Setter
public class FetchResumeResponse {
    private long id;

    private String email;

    private String url;

    @Enumerated(EnumType.STRING)
    private ResumeStateEnum status;

    private Instant createdDate;
    private Instant lastModifiedDate;
    private String lastModifiedBy;
    private String createdBy;
    private String companyName;

    private UserResume user;
    private JobResume job;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserResume{
        private long id;
        private String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JobResume{
        private long id;
        private String name;
    }
}
