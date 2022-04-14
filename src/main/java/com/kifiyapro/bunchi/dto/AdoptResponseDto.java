package com.kifiyapro.bunchi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdoptResponseDto {
    private String status;
    private Long Adoption_id;
}
