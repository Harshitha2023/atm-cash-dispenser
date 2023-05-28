package com.atm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.atm.model.CashNote;
import com.atm.service.CashDispenserService;

@RestController
@RequestMapping("/cash")
public class CashDispenserController {
    private final CashDispenserService cashDispenserService;

    @Autowired
    public CashDispenserController(CashDispenserService cashDispenserService) {
        this.cashDispenserService = cashDispenserService;
    }

    @PostMapping("/initialize")
    public void initializeCashNotes(@RequestBody List<CashNote> cashNotes) {
        cashDispenserService.initializeCashNotes(cashNotes);
    }

    @GetMapping("/notes")
    public List<CashNote> getCashNotes() {
        return cashDispenserService.getCashNotes();
    }

    @PostMapping("/withdraw/{amount}")
    public boolean dispenseCash(@PathVariable int amount) {
        return cashDispenserService.dispenseCash(amount);
    }

    @PostMapping("/add")
    public void addCashNotes(@RequestBody CashNote cashNotes) {
        cashDispenserService.addCashNotes(cashNotes);
    }

    @PostMapping("/remove")
    public void removeCashNotes(@RequestBody CashNote cashNotes) {
        cashDispenserService.removeCashNotes(cashNotes);
    }
}
