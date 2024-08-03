package vn.datk.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.datk.jobhunter.domain.Company;
import vn.datk.jobhunter.domain.User;

import java.util.List;

@Repository
public interface CompanyRepository
        extends
        JpaRepository<Company, Long>,
        JpaSpecificationExecutor<Company>
{
}
