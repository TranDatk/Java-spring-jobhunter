package vn.datk.jobhunter.config;

import org.springframework.data.domain.AuditorAware;
import vn.datk.jobhunter.util.security.SecurityUtils;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUserLogin().orElse("system"));
    }
}
