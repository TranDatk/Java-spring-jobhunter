package vn.datk.jobhunter.util.convert;

import vn.datk.jobhunter.domain.Job;
import vn.datk.jobhunter.domain.Skill;
import vn.datk.jobhunter.domain.User;
import vn.datk.jobhunter.domain.res.job.CreatedJobResponse;
import vn.datk.jobhunter.domain.res.job.UpdatedJobResponse;
import vn.datk.jobhunter.domain.res.user.CompanyUser;
import vn.datk.jobhunter.domain.res.user.CreatedUserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class JobConvert {
    public static CreatedJobResponse convertToResCreatedJobRes(Job job){
        CreatedJobResponse res = new CreatedJobResponse();

        res.setId(job.getId());
        res.setName(job.getName());
        res.setSalary(job.getSalary());
        res.setQuantity(job.getQuantity());
        res.setLocation(job.getLocation());
        res.setStartDate(job.getStartDate());
        res.setEndDate(job.getEndDate());
        res.setActive(job.isActive());
        res.setLevel(job.getLevel());
        res.setCreatedDate(job.getCreatedDate());
        res.setCreatedBy(job.getCreatedBy());

        if(job.getSkills() != null){
            List<String> skills = job.getSkills()
                    .stream().map(Skill::getName)
                    .collect(Collectors.toList());
            res.setSkills(skills);
        }
        return res;
    }

    public static UpdatedJobResponse convertToResUpdatedJobRes(Job job){
        UpdatedJobResponse res = new UpdatedJobResponse();

        res.setId(job.getId());
        res.setName(job.getName());
        res.setSalary(job.getSalary());
        res.setQuantity(job.getQuantity());
        res.setLocation(job.getLocation());
        res.setStartDate(job.getStartDate());
        res.setEndDate(job.getEndDate());
        res.setActive(job.isActive());
        res.setLevel(job.getLevel());
        res.setLastModifiedDate(job.getLastModifiedDate());
        res.setLastModifiedBy(job.getLastModifiedBy());

        if(job.getSkills() != null){
            List<String> skills = job.getSkills()
                    .stream().map(Skill::getName)
                    .collect(Collectors.toList());
            res.setSkills(skills);
        }
        return res;
    }
}
