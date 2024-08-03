package vn.datk.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.datk.jobhunter.domain.Skill;
import vn.datk.jobhunter.domain.res.ResultPaginationResponse;
import vn.datk.jobhunter.service.SkillService;
import vn.datk.jobhunter.util.annotation.ApiMessage;
import vn.datk.jobhunter.util.error.IdInvalidException;

@RequestMapping(path = "${apiPrefix}/skills")
@RequiredArgsConstructor
@RestController
public class SkillController {
    private final SkillService skillService;

    @PostMapping("")
    @ApiMessage("Create a skill")
    public ResponseEntity<Skill> create(@Valid @RequestBody Skill skill) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.skillService.create(skill));
    }

    @PutMapping("")
    @ApiMessage("Update a skill")
    public ResponseEntity<Skill> update(@Valid @RequestBody Skill skill) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(this.skillService.update(skill));
    }

    @GetMapping("")
    @ApiMessage("fetch all skills")
    public ResponseEntity<ResultPaginationResponse> getAll(
            @Filter Specification<Skill> spec,
            Pageable pageable
            ){
        return ResponseEntity.status(HttpStatus.OK).body(
                this.skillService.fetchAllSkill(spec, pageable)
        );
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete a skill")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws Exception{
        this.skillService.deleteSkill(id);
        return ResponseEntity.ok().body(null);
    }
}
