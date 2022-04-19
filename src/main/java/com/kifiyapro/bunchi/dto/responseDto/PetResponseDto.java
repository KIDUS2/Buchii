package com.kifiyapro.bunchi.dto.responseDto;

import com.kifiyapro.bunchi.modle.Photos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PetResponseDto {
    private Long petId;
    private String type;
    private String age;
    private  Photos[] photos;
    private String size;
    private Boolean status;
    private String gender;


}
