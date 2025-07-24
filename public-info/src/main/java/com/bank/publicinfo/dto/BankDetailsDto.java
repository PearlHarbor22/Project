package com.bank.publicinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDetailsDto {
    private Long id;
    private Long bik;
    private Long inn;
    private Long kpp;
    private String corAccount;
    private String city;
    private String jointStockCompany;
    private String name;
}
