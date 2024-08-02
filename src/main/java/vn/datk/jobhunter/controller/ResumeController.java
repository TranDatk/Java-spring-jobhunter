package vn.datk.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.datk.jobhunter.domain.Resume;
import vn.datk.jobhunter.domain.Skill;
import vn.datk.jobhunter.domain.res.ResultPaginationResponse;
import vn.datk.jobhunter.domain.res.resume.CreatedResumeResponse;
import vn.datk.jobhunter.domain.res.resume.FetchResumeResponse;
import vn.datk.jobhunter.domain.res.resume.UpdatedResumeResponse;
import vn.datk.jobhunter.service.ResumeService;
import vn.datk.jobhunter.util.annotation.ApiMessage;
import vn.datk.jobhunter.util.convert.ResumeConvert;

@RequestMapping(path = "${apiPrefix}/resumes")
@RequiredArgsConstructor
@RestController
public class ResumeController {
    private final ResumeService resumeService;

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
        return ResponseEntity.status(HttpStatus.OK).body(
                this.resumeService.fetchAllResume(spec, pageable)
        );
    }
}
