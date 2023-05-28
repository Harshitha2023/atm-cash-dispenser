package com.atm.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.atm.model.CashNote;
import com.atm.repository.CashNoteRepository;

@RunWith(MockitoJUnitRunner.class)
public class CashDispenserServiceTest {

    @Mock
    private CashNoteRepository cashNoteRepository;

    @InjectMocks
    private CashDispenserServiceImpl cashDispenserService;

    @Test
    public void testDispenseCash_SuccessfulDispense() {
        // Arrange
        List<CashNote> cashNotes = new ArrayList<>();
        cashNotes.add(new CashNote(50, 3));
        cashNotes.add(new CashNote(20, 8));

        when(cashNoteRepository.findAll()).thenReturn(cashNotes);

        // Act
        boolean result = cashDispenserService.dispenseCash(200);

        // Assert
        assertTrue(result);
        // Verify that the cash note quantities have been updated
        verify(cashNoteRepository, times(1)).saveAll(cashNotes);
    }

    @Test
    public void testDispenseCash_UnsuccessfulDispense() {
        // Arrange
        List<CashNote> cashNotes = new ArrayList<>();
        cashNotes.add(new CashNote(50, 3));
        cashNotes.add(new CashNote(20, 8));

        when(cashNoteRepository.findAll()).thenReturn(cashNotes);

        // Act
        boolean result = cashDispenserService.dispenseCash(300);

        // Assert
        assertFalse(result);
        // Verify that the cash note quantities have not been updated
        verify(cashNoteRepository, never()).saveAll(anyList());
    }
}
