package com.kifiyapro.bunchi.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseDto<T> {
    private boolean status;
    private String msg;


}
