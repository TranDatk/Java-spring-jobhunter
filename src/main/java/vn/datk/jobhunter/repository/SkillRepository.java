package vn.datk.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.datk.jobhunter.domain.Skill;

import java.util.List;

public interface SkillRepository extends
        JpaRepository<Skill, Long>,
        JpaSpecificationExecutor<Skill> {
    boolean existsByName(String name);
    List<Skill> findByIdIn(List<Long> id);
}