package vn.datk.jobhunter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.datk.jobhunter.domain.Company;
import vn.datk.jobhunter.service.CompanyService;


@RequestMapping(path = "${apiPrefix}/companies")
@RestController
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    @PostMapping("/")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.companyService.createCompany(company));
    }
}
