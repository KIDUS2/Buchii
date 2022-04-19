package com.kifiyapro.bunchi.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdoptResponseDtos {
    private CustomerResponseDto customerResponseDto;
    private PetResponseDto petResponseDto;
    private Long Adopt_Id;
    private Date created_on;
    private  Instant updated_on;
}
