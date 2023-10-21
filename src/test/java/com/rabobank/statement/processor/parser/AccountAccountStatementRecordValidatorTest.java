package com.rabobank.statement.processor.parser;

import com.rabobank.statement.processor.model.StatementRecord;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountAccountStatementRecordValidatorTest {

    @Test
    public void whenDate_isNotFutureDate_thenReturnFalse() {
        boolean result = AccountStatementRecordValidator.isFutureDatePredicate.test("2020-12-12");
        assertFalse(result);
    }

    @Test
    public void whenDate_isFutureDate_thenReturnTrue() {
        boolean result = AccountStatementRecordValidator.isFutureDatePredicate.test("2099-12-12");
        assertTrue(result);
    }

    @Test
    public void whenDate_isCurrentDate_thenReturnFalse() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = formatter.format(LocalDate.now());
        boolean result = AccountStatementRecordValidator.isFutureDatePredicate.test(date);
        assertFalse(result);
    }

    @Test
    public void whenValidBalance_thenReturnFalse() {
        StatementRecord statementRecord = new StatementRecord(1, "", "", new BigDecimal(1),
                new BigDecimal(2), new BigDecimal(3), "");
        boolean result = AccountStatementRecordValidator.isNotValidBalancePredicate.test(statementRecord);
        assertFalse(result);
    }

    @Test
    public void whenNotValidBalance_thenReturnTrue() {
        StatementRecord statementRecord = new StatementRecord(1, "", "", new BigDecimal(1),
                new BigDecimal(2), new BigDecimal(4), "");
        boolean result = AccountStatementRecordValidator.isNotValidBalancePredicate.test(statementRecord);
        assertTrue(result);
    }

    @Test
    public void whenNotValidStartBalance_thenReturnTrue() {
        StatementRecord statementRecord = new StatementRecord(1, "", "", new BigDecimal(-1),
                new BigDecimal(2), new BigDecimal(3), "");
        boolean result = AccountStatementRecordValidator.isNotValidBalancePredicate.test(statementRecord);
        assertTrue(result);
    }

    @Test
    public void whenValidStartBalance_thenReturnFalse() {
        StatementRecord statementRecord = new StatementRecord(1, "", "", new BigDecimal(1),
                new BigDecimal(2), new BigDecimal(3), "");
        boolean result = AccountStatementRecordValidator.isNotValidBalancePredicate.test(statementRecord);
        assertFalse(result);
    }

    @Test
    public void whenNotValidEndBalance_thenReturnTrue() {
        StatementRecord statementRecord = new StatementRecord(1, "", "", new BigDecimal(1),
                new BigDecimal(2), new BigDecimal(-3), "");
        boolean result = AccountStatementRecordValidator.isNotValidBalancePredicate.test(statementRecord);
        assertTrue(result);
    }

    @Test
    public void whenValidEndBalance_thenReturnFalse() {
        StatementRecord statementRecord = new StatementRecord(1, "", "", new BigDecimal(1),
                new BigDecimal(2), new BigDecimal(3), "");
        boolean result = AccountStatementRecordValidator.isNotValidBalancePredicate.test(statementRecord);
        assertFalse(result);
    }

    @Test
    public void whenInvalidStatementRecord_thenErrorValidatedStatementRecord() {
        StatementRecord statementRecord = new StatementRecord(1, "", "", new BigDecimal(-1),
                new BigDecimal(-2), new BigDecimal(3), "2022-10-10");

        AccountStatementRecordValidator.validatedRecord(statementRecord, new HashSet<>());

    }

}
