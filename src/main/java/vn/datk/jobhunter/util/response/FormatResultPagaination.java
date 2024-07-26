package vn.datk.jobhunter.util.response;

import org.springframework.data.domain.Page;
import vn.datk.jobhunter.domain.response.MetaResponse;
import vn.datk.jobhunter.domain.response.ResultPaginationResponse;

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
}
