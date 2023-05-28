package com.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atm.model.CashNote;

public interface CashNoteRepository extends JpaRepository<CashNote, Long> {
    CashNote findByDenomination(int denomination);
}
