package com.tc.training.model;

import com.tc.training.utils.Tenures;
import com.tc.training.utils.TypeOfSlab;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;


@Table(name = "slabs")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Slabs {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Tenures tenures;

    private String interestRate;

    private TypeOfSlab typeOfTransaction;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Tenures getTenures() {
        return tenures;
    }

    public void setTenures(Tenures tenures) {
        this.tenures = tenures;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public TypeOfSlab getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(TypeOfSlab typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }
}
