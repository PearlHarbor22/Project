package com.bank.publicinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtmDto {
    private Long id;
    private String address;
    private LocalTime startOfWork;
    private LocalTime endOfWork;
    private Boolean allHours;
    private Long branchId;
}
