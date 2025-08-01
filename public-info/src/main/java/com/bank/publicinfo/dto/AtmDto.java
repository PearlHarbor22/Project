package com.bank.publicinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalTime;

@Getter
@Setter
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
