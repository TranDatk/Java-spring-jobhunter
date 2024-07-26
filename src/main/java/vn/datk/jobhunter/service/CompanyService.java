package vn.datk.jobhunter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.datk.jobhunter.domain.Company;
import vn.datk.jobhunter.domain.User;
import vn.datk.jobhunter.domain.response.ResultPaginationResponse;
import vn.datk.jobhunter.repository.CompanyRepository;
import vn.datk.jobhunter.util.FormatResultPagaination;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public Company createCompany(Company company){
        return companyRepository.save(company);
    }

    public ResultPaginationResponse getAllCompany(Pageable pageable){
        Page<Company> companyPage = companyRepository.findAll(pageable);
        ResultPaginationResponse response = FormatResultPagaination.createPaginationResponse(companyPage);
        return response;
    }

    public Company updateCompany(Long id, Company company){
        Optional<Company> companyOptional = this.companyRepository.findById(id);
        if(companyOptional.isPresent()){
            Company currentCompany = companyOptional.get();
            currentCompany.setName(company.getName());
            currentCompany.setLogo(company.getLogo());
            currentCompany.setDescription(company.getDescription());
            currentCompany.setAddress(company.getAddress());
            return this.companyRepository.save(currentCompany);
        }
        return null;
    }

    public void deleteCompany(Long id){
        this.companyRepository.deleteById(id);
    }
}
