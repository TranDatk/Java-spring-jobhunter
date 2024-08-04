package vn.datk.jobhunter.util.response;

import org.springframework.data.domain.Page;
import vn.datk.jobhunter.domain.Resume;
import vn.datk.jobhunter.domain.User;
import vn.datk.jobhunter.domain.res.resume.FetchResumeResponse;
import vn.datk.jobhunter.domain.res.user.CompanyUser;
import vn.datk.jobhunter.domain.res.user.CreatedUserResponse;
import vn.datk.jobhunter.domain.res.MetaResponse;
import vn.datk.jobhunter.domain.res.ResultPaginationResponse;
import vn.datk.jobhunter.domain.res.user.UpdatedUserResponse;
import vn.datk.jobhunter.util.convert.ResumeConvert;
import vn.datk.jobhunter.util.convert.UserConvert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FormatResultPagaination {
    public static ResultPaginationResponse createPaginationResponse(Page page) {
        ResultPaginationResponse rs = new ResultPaginationResponse();
        MetaResponse mr = new MetaResponse();

        mr.setPage(page.getNumber() + 1);
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

        mr.setPage(page.getNumber() + 1);
        mr.setPageSize(page.getSize());
        mr.setPages(page.getTotalPages());
        mr.setTotal(page.getTotalElements());

        rs.setMeta(mr);

        List<CreatedUserResponse> listUser = page.getContent()
                .stream().map(UserConvert::convertToResCreatedUserRes)
                .collect(Collectors.toList());

        rs.setResult(listUser);

        return rs;
    }

    public static ResultPaginationResponse createPaginateResumeRes(Page<Resume> page) {
        ResultPaginationResponse rs = new ResultPaginationResponse();
        MetaResponse mr = new MetaResponse();

        mr.setPage(page.getNumber() + 1);
        mr.setPageSize(page.getSize());
        mr.setPages(page.getTotalPages());
        mr.setTotal(page.getTotalElements());

        rs.setMeta(mr);

        List<FetchResumeResponse> listResume = page.getContent()
                .stream().map(
                        ResumeConvert::convertToResFetchResumeRes
                ).toList();

        rs.setResult(listResume);

        return rs;
    }
}
