package com.kifiyapro.bunchi.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PetResponseDto {
    private Long petId;
    private String type;
    private Long age;
    private String size;
    private Boolean status;
    private String gender;
    private Instant createdOn;
    private Instant updatedOn;

}
