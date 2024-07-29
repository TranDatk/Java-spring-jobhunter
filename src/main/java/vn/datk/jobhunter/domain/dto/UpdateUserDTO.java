package vn.datk.jobhunter.domain.dto;

import lombok.Getter;
import lombok.Setter;
import vn.datk.jobhunter.domain.Company;
import vn.datk.jobhunter.util.constant.GenderEnum;


@Getter
@Setter
public class UpdateUserDTO {
    private Long id;

    private String name;

    private int age;

    private String address;

    private GenderEnum gender;

    private Company company;
}
