package com.tc.training.smallfinance.model;

import com.tc.training.smallfinance.utils.Tenures;
import com.tc.training.smallfinance.utils.TypeOfSlab;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Slabs {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Tenures tenures;

    private String interestRate;

    @Enumerated(EnumType.STRING)
    private TypeOfSlab typeOfTransaction;
}
