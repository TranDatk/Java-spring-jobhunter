package vn.datk.jobhunter.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import vn.datk.jobhunter.util.constant.GenderEnum;

import java.time.Instant;

@Getter
@Setter
public class UpdateUserDTO {
    private Long id;

    private String name;

    private int age;

    private String address;

    private GenderEnum gender;
}
