package com.marlo.orders.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@ToString
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean validated;

    private LocalDate creationDate;

    private LocalDate validationDate;

    public Long getId() {
        return this.id;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public void setValidationDate(LocalDate validationDate) {
        this.validationDate = validationDate;
    }
}
