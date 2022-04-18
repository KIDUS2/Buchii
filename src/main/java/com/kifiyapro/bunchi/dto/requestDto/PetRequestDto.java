package com.kifiyapro.bunchi.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PetRequestDto {
    private String type;
    private Long age;
    private String size;
    private Boolean goodWithChildren;
    private String gender;
    private  MultipartFile[] files;
    private List<String> photo;
}
