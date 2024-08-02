package vn.datk.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.datk.jobhunter.domain.Job;
import vn.datk.jobhunter.domain.res.ResultPaginationResponse;
import vn.datk.jobhunter.domain.res.job.CreatedJobResponse;
import vn.datk.jobhunter.domain.res.job.UpdatedJobResponse;
import vn.datk.jobhunter.service.JobService;
import vn.datk.jobhunter.util.annotation.ApiMessage;

@RequestMapping(path = "${apiPrefix}/jobs")
@RequiredArgsConstructor
@RestController
public class JobController {
    private final JobService jobService;

    @PostMapping("")
    @ApiMessage("Create a job")
    public ResponseEntity<CreatedJobResponse> create(@Valid @RequestBody Job job){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                this.jobService.create(job)
        );
    }

    @PutMapping("")
    @ApiMessage("Update a job")
    public ResponseEntity<UpdatedJobResponse> update(@Valid @RequestBody Job job) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(this.jobService.update(job));
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete job by id")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws Exception{
        this.jobService.delete(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/{id}")
    @ApiMessage("Get job by id")
    public ResponseEntity<Job> getJob(@PathVariable("id") Long id) throws Exception{
        return ResponseEntity.ok().body(this.jobService.fetchJobById(id));
    }

    @GetMapping("")
    @ApiMessage("fetch all skills")
    public ResponseEntity<ResultPaginationResponse> getAll(
            @Filter Specification<Job> spec,
            Pageable pageable
    ){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.jobService.fetchAllJob(spec, pageable)
        );
    }
}
