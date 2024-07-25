package vn.datk.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.datk.jobhunter.domain.Company;
import vn.datk.jobhunter.domain.User;

public interface CompanyRepository extends JpaRepository<Company, Long> { }
