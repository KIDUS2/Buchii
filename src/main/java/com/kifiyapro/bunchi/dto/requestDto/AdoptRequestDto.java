package com.kifiyapro.bunchi.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdoptRequestDto {
    private Long customer_Id;
    private Long pet_Id;
}
