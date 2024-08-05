package vn.datk.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import com.turkraft.springfilter.builder.FilterBuilder;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.datk.jobhunter.domain.*;
import vn.datk.jobhunter.domain.res.ResultPaginationResponse;
import vn.datk.jobhunter.domain.res.resume.CreatedResumeResponse;
import vn.datk.jobhunter.domain.res.resume.FetchResumeResponse;
import vn.datk.jobhunter.domain.res.resume.UpdatedResumeResponse;
import vn.datk.jobhunter.service.ResumeService;
import vn.datk.jobhunter.service.UserService;
import vn.datk.jobhunter.util.annotation.ApiMessage;
import vn.datk.jobhunter.util.convert.ResumeConvert;
import vn.datk.jobhunter.util.security.SecurityUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(path = "${apiPrefix}/resumes")
@RequiredArgsConstructor
@RestController
public class ResumeController {
    private final ResumeService resumeService;
    private final UserService userService;
    private final FilterSpecificationConverter filterSpecificationConverter;
    private final FilterBuilder filterBuilder;

    @PostMapping("")
    @ApiMessage("Create a resume")
    public ResponseEntity<CreatedResumeResponse> create(@Valid @RequestBody Resume resume) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.resumeService.create(resume));
    }

    @PutMapping("")
    @ApiMessage("Update a resume")
    public ResponseEntity<UpdatedResumeResponse> update(@Valid @RequestBody Resume resume) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.resumeService.update(resume));
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete a resume")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws Exception{
        this.resumeService.delete(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/{id}")
    @ApiMessage("Fetch a resume by id")
    public ResponseEntity<FetchResumeResponse> fetchById(@PathVariable("id") Long id) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(
                ResumeConvert.convertToResFetchResumeRes(this.resumeService.fetchResumelById(id))
        );
    }

    @GetMapping("")
    @ApiMessage("fetch all resume")
    public ResponseEntity<ResultPaginationResponse> getAll(
            @Filter Specification<Resume> spec,
            Pageable pageable
    ){
        List<Long> arrJobIds = null;
        String email = SecurityUtils.getCurrentUserLogin().isPresent()
                ? SecurityUtils.getCurrentUserLogin().get()
                : "";
        User currentUser = this.userService.handleGetUserByUsername(email);
        if (currentUser != null) {
            Company userCompany = currentUser.getCompany();
            if (userCompany != null) {
                List<Job> companyJobs = userCompany.getJobs();
                if (companyJobs != null && !companyJobs.isEmpty()) {
                    arrJobIds = companyJobs.stream().map(Job::getId)
                            .collect(Collectors.toList());
                }
            }
        }

        Specification<Resume> jobInSpec = filterSpecificationConverter.convert(filterBuilder.field("job")
                .in(filterBuilder.input(arrJobIds)).get());

        Specification<Resume> finalSpec = jobInSpec.and(spec);

        return ResponseEntity.ok().body(this.resumeService.fetchAllResume(finalSpec, pageable));
    }

    @PostMapping("/by-user")
    @ApiMessage("Get list resumes by user")
    public ResponseEntity<ResultPaginationResponse> fetchResumeByUser(Pageable pageable) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(this.resumeService.fetchResumeByUser(pageable));
    }
}
