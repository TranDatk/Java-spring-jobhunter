package vn.datk.jobhunter.service;

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

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

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
