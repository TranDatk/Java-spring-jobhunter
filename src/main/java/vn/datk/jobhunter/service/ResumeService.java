package vn.datk.jobhunter.service;

import com.turkraft.springfilter.converter.FilterSpecification;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import com.turkraft.springfilter.parser.FilterParser;
import com.turkraft.springfilter.parser.node.FilterNode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.datk.jobhunter.domain.Job;
import vn.datk.jobhunter.domain.Resume;
import vn.datk.jobhunter.domain.Skill;
import vn.datk.jobhunter.domain.User;
import vn.datk.jobhunter.domain.res.ResultPaginationResponse;
import vn.datk.jobhunter.domain.res.resume.CreatedResumeResponse;
import vn.datk.jobhunter.domain.res.resume.UpdatedResumeResponse;
import vn.datk.jobhunter.repository.JobRepository;
import vn.datk.jobhunter.repository.ResumeRepository;
import vn.datk.jobhunter.repository.UserRepository;
import vn.datk.jobhunter.util.convert.ResumeConvert;
import vn.datk.jobhunter.util.error.IdInvalidException;
import vn.datk.jobhunter.util.response.FormatResultPagaination;
import vn.datk.jobhunter.util.security.SecurityUtils;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final FilterParser filterParser;
    private final FilterSpecificationConverter filterSpecificationConverter;

    public CreatedResumeResponse create(Resume resume) throws Exception{
        if(!this.checkResumeExistByUserAndJob(resume)){
            throw new DataIntegrityViolationException("Job or User do not exist");
        }
        return ResumeConvert.convertToResCreatedResumeRes(this.resumeRepository.save(resume));
    }

    public Resume fetchResumelById(Long id) throws Exception {
        if(this.resumeRepository.existsById(id)){
            return this.resumeRepository.findById(id).get();
        }else{
            throw new IdInvalidException("The specified Resume ID is invalid");
        }
    }

    public UpdatedResumeResponse update(Resume resume) throws Exception{
        Resume currentResume = this.fetchResumelById(resume.getId());
        currentResume.setStatus(resume.getStatus());
        return ResumeConvert.convertToResUpdatedResumeRes(this.resumeRepository.save(currentResume));
    }

    public void delete(Long id) throws Exception {
        Resume currentResume = this.fetchResumelById(id);
        if(currentResume == null){
            throw new IdInvalidException("Resume ID is not found");
        }
        this.resumeRepository.deleteById(currentResume.getId());
    }

    public ResultPaginationResponse fetchAllResume(Specification<Resume> spec, Pageable pageable){
        Page<Resume> resumePage = this.resumeRepository.findAll(spec, pageable);
        ResultPaginationResponse response = FormatResultPagaination.createPaginateResumeRes(resumePage);
        return response;
    }

    public ResultPaginationResponse fetchResumeByUser(Pageable pageable){
        String email = SecurityUtils.getCurrentUserLogin().isPresent()
                ? SecurityUtils.getCurrentUserLogin().get()
                : "";
        FilterNode node = filterParser.parse("email='" + email + "'");
        FilterSpecification<Resume> spec = filterSpecificationConverter.convert(node);

        Page<Resume> resumePage = this.resumeRepository.findAll(spec, pageable);
        return FormatResultPagaination.createPaginateResumeRes(resumePage);
    }

    private boolean checkResumeExistByUserAndJob(Resume resume){
        if(resume.getUser().getId() == null){
            return false;
        }
        Optional<User> user = this.userRepository.findById(resume.getUser().getId());
        if(user.isEmpty()){
            return false;
        }
        if(resume.getJob().getId() == null){
            return false;
        }
        Optional<Job> job = this.jobRepository.findById(resume.getJob().getId());
        if(job.isEmpty()){
            return false;
        }
        return true;
    }
}
