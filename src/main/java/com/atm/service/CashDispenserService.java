package com.atm.service;

import java.util.List;

import com.atm.model.CashNote;

public interface CashDispenserService {
    void initializeCashNotes(List<CashNote> cashNotes);

    List<CashNote> getCashNotes();

    boolean dispenseCash(int amount);

    void addCashNotes(CashNote cashNote);

    void removeCashNotes(CashNote cashNote);
}
