package com.kifiyapro.bunchi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Baselist<T> {
    private List<T> datas;
    private Long count;
    private ResponseDto responseDto;
}
