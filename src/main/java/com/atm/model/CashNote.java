package com.atm.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CashNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int denomination;
    private int quantity;

    public CashNote(final int denomination, final int count) {
        this.denomination = denomination;
        this.quantity = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public int getDenomination() {
        return denomination;
    }

    public void setDenomination(final int denomination) {
        this.denomination = denomination;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public CashNote(final Long id, final int denomination, final int quantity) {
        this.id = id;
        this.denomination = denomination;
        this.quantity = quantity;
    }

    public CashNote() {
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashNote cashNote = (CashNote) o;
        return denomination == cashNote.denomination && quantity == cashNote.quantity && Objects.equals(id,
                                                                                                        cashNote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, denomination, quantity);
    }

    @Override
    public String toString() {
        return "CashNote{" +
                "id=" + id +
                ", denomination=" + denomination +
                ", quantity=" + quantity +
                '}';
    }
}
