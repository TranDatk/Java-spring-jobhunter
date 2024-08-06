package vn.datk.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.datk.jobhunter.domain.Job;
import vn.datk.jobhunter.domain.Skill;

import java.util.List;

@Repository
public interface JobRepository extends
        JpaRepository<Job, Long>,
        JpaSpecificationExecutor<Job> {
    List<Job> findBySkillsIn(List<Skill> skills);
}
