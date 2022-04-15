package com.kifiyapro.bunchi.dto.responseDto;

import com.kifiyapro.bunchi.Service.Datas;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponseDto {
private String status;
private Datas data;

}
