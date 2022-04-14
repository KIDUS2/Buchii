package com.kifiyapro.bunchi.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdoptResponseDtos {
    private CustomerResponseDto customerResponseDto;
    private PetResponseDto petResponseDto;
    private Long Adopt_Id;
    private Instant created_on;
    private  Instant updated_on;
}
