package vn.datk.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.datk.jobhunter.domain.Resume;

public interface ResumeRepository extends
        JpaRepository<Resume, Long>,
        JpaSpecificationExecutor<Resume> {

}
