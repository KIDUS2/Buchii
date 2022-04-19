package com.kifiyapro.bunchi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetSearchDto {

    private String type;
    private String age;
    private String size;
    private Boolean goodWithChildren;
    private String gender;
    private Long limit;
}
