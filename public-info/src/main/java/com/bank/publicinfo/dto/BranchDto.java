package com.bank.publicinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto {
    private Long id;
    private String address;
    private Long phoneNumber;
    private String city;
    private LocalTime startOfWork;
    private LocalTime endOfWork;
    private Long bankDetailsId;
}
