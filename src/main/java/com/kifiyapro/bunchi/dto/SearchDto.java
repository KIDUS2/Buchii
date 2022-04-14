package com.kifiyapro.bunchi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {
    private Instant from_date;
    private Instant to_date;
}
