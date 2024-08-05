package vn.datk.jobhunter.util.convert;

import vn.datk.jobhunter.domain.User;
import vn.datk.jobhunter.domain.res.user.CompanyUser;
import vn.datk.jobhunter.domain.res.user.CreatedUserResponse;
import vn.datk.jobhunter.domain.res.user.RoleUser;
import vn.datk.jobhunter.domain.res.user.UpdatedUserResponse;

public class UserConvert {
    public static CreatedUserResponse convertToResCreatedUserRes(User user){
        CreatedUserResponse res = new CreatedUserResponse();
        CompanyUser companyUser = new CompanyUser();
        RoleUser roleUser = new RoleUser();

        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setAddress(user.getAddress());
        res.setAge(user.getAge());
        res.setCreatedDate(user.getCreatedDate());
        res.setGender(user.getGender());
        res.setName(user.getName());

        if(user.getCompany() != null){
            companyUser.setId(user.getCompany().getId());
            companyUser.setName(user.getCompany().getName());
            res.setCompany(companyUser);
        }
        if(user.getRole() != null){
            roleUser.setId(user.getRole().getId());
            roleUser.setName(user.getRole().getName());
            res.setRole(roleUser);
        }
        return res;
    }

    public static UpdatedUserResponse convertToResUpdatedUserRes(User user){
        UpdatedUserResponse res = new UpdatedUserResponse();
        CompanyUser companyUser = new CompanyUser();
        RoleUser roleUser = new RoleUser();

        res.setId(user.getId());
        res.setAddress(user.getAddress());
        res.setAge(user.getAge());
        res.setGender(user.getGender());
        res.setName(user.getName());
        res.setLastModifiedDate(user.getLastModifiedDate());
        if(user.getCompany() != null){
            companyUser.setId(user.getCompany().getId());
            companyUser.setName(user.getCompany().getName());
            res.setCompany(companyUser);
        }
        if(user.getRole() != null){
            roleUser.setId(user.getRole().getId());
            roleUser.setName(user.getRole().getName());
            res.setRole(roleUser);
        }
        return res;
    }
}
