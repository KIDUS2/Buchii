package com.kifiyapro.bunchi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {
    //    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date from_date;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date to_date;
}
