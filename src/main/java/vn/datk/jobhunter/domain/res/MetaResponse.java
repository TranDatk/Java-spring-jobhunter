package vn.datk.jobhunter.domain.res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetaResponse {
    private int current;
    private int pageSize;
    private int pages;
    private long total;
}
