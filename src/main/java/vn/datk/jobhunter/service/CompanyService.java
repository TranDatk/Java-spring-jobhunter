package vn.datk.jobhunter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.datk.jobhunter.domain.Company;
import vn.datk.jobhunter.repository.CompanyRepository;

@RequiredArgsConstructor
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public Company createCompany(Company company){
        return companyRepository.save(company);
    }
}
