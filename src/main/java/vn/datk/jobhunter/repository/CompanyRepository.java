package vn.datk.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.datk.jobhunter.domain.Company;
import vn.datk.jobhunter.domain.User;

import java.util.List;

public interface CompanyRepository
        extends
        JpaRepository<Company, Long>,
        JpaSpecificationExecutor<Company>
{
}
