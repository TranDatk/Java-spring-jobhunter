package vn.datk.jobhunter.util.response;

import org.springframework.data.domain.Page;
import vn.datk.jobhunter.domain.User;
import vn.datk.jobhunter.domain.res.user.CompanyUser;
import vn.datk.jobhunter.domain.res.user.CreatedUserResponse;
import vn.datk.jobhunter.domain.res.MetaResponse;
import vn.datk.jobhunter.domain.res.ResultPaginationResponse;
import vn.datk.jobhunter.domain.res.user.UpdatedUserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class FormatResultPagaination {
    public static ResultPaginationResponse createPaginationResponse(Page page) {
        ResultPaginationResponse rs = new ResultPaginationResponse();
        MetaResponse mr = new MetaResponse();

        mr.setCurrent(page.getNumber() + 1);
        mr.setPageSize(page.getSize());
        mr.setPages(page.getTotalPages());
        mr.setTotal(page.getTotalElements());

        rs.setMeta(mr);
        rs.setResult(page.getContent());

        return rs;
    }

    public static ResultPaginationResponse createPaginateUserRes(Page<User> page) {
        ResultPaginationResponse rs = new ResultPaginationResponse();
        MetaResponse mr = new MetaResponse();

        mr.setCurrent(page.getNumber() + 1);
        mr.setPageSize(page.getSize());
        mr.setPages(page.getTotalPages());
        mr.setTotal(page.getTotalElements());

        rs.setMeta(mr);

        List<CreatedUserResponse> listUser = page.getContent()
                .stream().map(item -> new CreatedUserResponse(
                        item.getId(),
                        item.getName(),
                        item.getAge(),
                        item.getPhoneNumber(),
                        item.getEmail(),
                        item.getAddress(),
                        item.getGender(),
                        item.getCreatedDate(),
                        new CompanyUser(
                                item.getCompany() != null ? item.getCompany().getId() : 0,
                                item.getCompany() != null ? item.getCompany().getName() : null
                        )
                ))
                .collect(Collectors.toList());


        rs.setResult(listUser);

        return rs;
    }
}
