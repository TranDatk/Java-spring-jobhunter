package vn.datk.jobhunter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.datk.jobhunter.domain.Company;
import vn.datk.jobhunter.domain.response.ResultPaginationResponse;
import vn.datk.jobhunter.service.CompanyService;

import java.util.List;
import java.util.Optional;


@RequestMapping(path = "${apiPrefix}/companies")
@RestController
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping("")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.companyService.createCompany(company));
    }

    @GetMapping("")
    public ResponseEntity<ResultPaginationResponse> getAllCompany(
            @RequestParam("current") Optional<Integer> current,
            @RequestParam("pageSize") Optional<Integer> pageSize
    ){
        Pageable pageable = PageRequest.of(
                current.isPresent() ? current.get() - 1 : 0,
                pageSize.isPresent() ? pageSize.get() : 5
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.companyService.getAllCompany(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(
            @PathVariable("id") Long id,
            @Valid @RequestBody Company company
    ){
        return ResponseEntity.ok(this.companyService.updateCompany(id, company));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(
            @PathVariable("id") Long id
    ){
        this.companyService.deleteCompany(id);
        return ResponseEntity.ok(null);
    }
}
