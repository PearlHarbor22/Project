package com.bank.publicinfo.entities;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "bank_details", schema = "publicinfo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bik", unique = true, nullable = false)
    private Long bik;

    @Column(name = "inn", unique = true, nullable = false)
    private Long inn;

    @Column(name = "kpp", unique = true, nullable = false)
    private Long kpp;

    @Column(name = "cor_account", unique = true, nullable = false)
    private String corAccount;

    @Column(nullable = false)
    private String city;

    @Column(name = "joint_stock_company", nullable = false)
    private String jointStockCompany;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "bankDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Branch> branches;

    @OneToMany(mappedBy = "bankDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<License> licenses;

    @OneToMany(mappedBy = "bankDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certificate> certificates;
}
