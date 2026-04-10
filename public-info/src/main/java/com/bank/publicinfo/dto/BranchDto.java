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
public class BranchDto {
    private Long id;
    private String address;
    private Long phoneNumber;
    private String city;
    private LocalTime startOfWork;
    private LocalTime endOfWork;
    private Long bankDetailsId;
}
