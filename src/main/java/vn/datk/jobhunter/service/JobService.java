package vn.datk.jobhunter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.datk.jobhunter.domain.Company;
import vn.datk.jobhunter.domain.Job;
import vn.datk.jobhunter.domain.Skill;
import vn.datk.jobhunter.domain.res.ResultPaginationResponse;
import vn.datk.jobhunter.domain.res.job.CreatedJobResponse;
import vn.datk.jobhunter.domain.res.job.UpdatedJobResponse;
import vn.datk.jobhunter.repository.CompanyRepository;
import vn.datk.jobhunter.repository.JobRepository;
import vn.datk.jobhunter.repository.SkillRepository;
import vn.datk.jobhunter.util.convert.JobConvert;
import vn.datk.jobhunter.util.error.IdInvalidException;
import vn.datk.jobhunter.util.response.FormatResultPagaination;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;
    private final CompanyRepository companyRepository;

    public CreatedJobResponse create(Job job){
        if(job.getSkills() != null){
            List<Long> reqSkills = job.getSkills()
                    .stream().map(Skill::getId)
                    .collect(Collectors.toList());
            List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkills);
            job.setSkills(dbSkills);
        }

        if(job.getCompany() != null){
            Optional<Company> companyOptional = this.companyRepository.findById(job.getCompany().getId());
            companyOptional.ifPresent(job::setCompany);
        }

        Job currentJob = this.jobRepository.save(job);
        return JobConvert.convertToResCreatedJobRes(currentJob);
    }

    public UpdatedJobResponse update(Job job) throws Exception{
        if(job.getId() == null){
            throw new IdInvalidException("Job ID not found");
        }
        Job jobInDB = this.fetchJobById(job.getId());

        if(job.getSkills() != null){
            List<Long> reqSkills = job.getSkills()
                    .stream().map(Skill::getId)
                    .collect(Collectors.toList());
            List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkills);
            jobInDB.setSkills(dbSkills);
        }

        if(job.getCompany() != null){
            Optional<Company> companyOptional = this.companyRepository.findById(job.getCompany().getId());
            companyOptional.ifPresent(jobInDB::setCompany);
        }

        Job currentJob = this.jobRepository.save(jobInDB);
        return JobConvert.convertToResUpdatedJobRes(currentJob);
    }

    public void delete(Long id) throws Exception{
        if(!this.jobRepository.existsById(id)){
            throw new IdInvalidException("Job not found");
        }
        this.jobRepository.deleteById(id);
    }

    public Job fetchJobById(Long id) throws Exception{
        Optional<Job> currentJob = this.jobRepository.findById(id);
        if(!currentJob.isPresent()){
            throw new IdInvalidException("Job not found");
        }
        return currentJob.get();
    }

    public ResultPaginationResponse fetchAllJob(Specification<Job> spec, Pageable pageable){
        Page<Job> jobPage = this.jobRepository.findAll(spec, pageable);
        ResultPaginationResponse response = FormatResultPagaination.createPaginationResponse(jobPage);
        return response;
    }
}
