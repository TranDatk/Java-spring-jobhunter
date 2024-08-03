package vn.datk.jobhunter.domain.res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetaResponse {
    private int page;
    private int pageSize;
    private int pages;
    private long total;
}
