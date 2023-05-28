package com.atm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.atm.model.CashNote;
import com.atm.repository.CashNoteRepository;

@Service
public class CashDispenserServiceImpl implements CashDispenserService {
    private final CashNoteRepository cashNoteRepository;

    @Autowired
    public CashDispenserServiceImpl(CashNoteRepository cashNoteRepository) {
        this.cashNoteRepository = cashNoteRepository;
    }

    @Override
    public void initializeCashNotes(List<CashNote> cashNotes) {
        cashNoteRepository.saveAll(cashNotes);
    }

    @Override
    public List<CashNote> getCashNotes() {
        return cashNoteRepository.findAll();
    }

    @Override
    public boolean dispenseCash(int amount) {
        List<CashNote> cashNotes = cashNoteRepository.findAll();
        List<CashNote> dispensableNotes = new ArrayList<>();
        int remainingAmount = amount;

        // Sort cash notes in descending order based on denomination
        cashNotes.sort(Comparator.comparingInt(CashNote::getDenomination).reversed());

        if (canDispenseCash(cashNotes, dispensableNotes, remainingAmount)) {
            cashNoteRepository.saveAll(cashNotes);
            return true;
        }

        return false;
    }

    private boolean canDispenseCash(List<CashNote> cashNotes, List<CashNote> dispensableNotes, int amount) {
        if (amount == 0) {
            return true;
        }
        //use greedy algorithm
        for (int i = 0; i < cashNotes.size(); i++) {
            CashNote note = cashNotes.get(i);
            int denomination = note.getDenomination();
            int quantity = note.getQuantity();

            if (denomination <= amount && quantity > 0) {
                int count = Math.min(quantity, amount / denomination);

                for (int j = count; j >= 1; j--) {
                    note.setQuantity(quantity - j);
                    dispensableNotes.add(new CashNote(denomination, j));

                    if (canDispenseCash(cashNotes.subList(i + 1, cashNotes.size()), dispensableNotes, amount - (j * denomination))) {
                        return true;
                    }

                    dispensableNotes.remove(dispensableNotes.size() - 1);
                    note.setQuantity(quantity);
                }
            }
        }

        return false;
    }

    @Override
    public void addCashNotes(CashNote cashNote) {
        CashNote existingCashNote = cashNoteRepository.findByDenomination(cashNote.getDenomination());
        if (existingCashNote != null) {
            existingCashNote.setQuantity(existingCashNote.getQuantity() + cashNote.getQuantity());
            cashNoteRepository.save(existingCashNote);
        } else {
            cashNoteRepository.save(cashNote);
        }
    }

    @Override
    public void removeCashNotes(CashNote cashNote) {
        CashNote existingCashNote = cashNoteRepository.findByDenomination(cashNote.getDenomination());
        if (existingCashNote != null) {
            int newQuantity = existingCashNote.getQuantity() - cashNote.getQuantity();
            if (newQuantity <= 0) {
                cashNoteRepository.delete(existingCashNote);
            } else {
                existingCashNote.setQuantity(newQuantity);
                cashNoteRepository.save(existingCashNote);
            }
        }
    }
}
