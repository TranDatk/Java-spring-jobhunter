package vn.datk.jobhunter.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultPaginationResponse {
    private MetaResponse meta;
    private Object result;
}
