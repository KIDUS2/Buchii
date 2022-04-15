package com.kifiyapro.bunchi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetSearchDto {

    private String type;
    private Long age;
    private String size;
    private Boolean good_with_children;
    private String gender;
    private Long limit;
}
