package com.rabobank.statement.processor.parser;

import com.rabobank.statement.processor.model.StatementRecord;
import com.rabobank.statement.processor.model.StatementValidatedRecord;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

class StatementRecordValidator {

    public static Optional<StatementValidatedRecord> validatedRecord(StatementRecord statementRecord, HashSet<Integer> referenceSets) {
        return validateRecord(statementRecord, referenceSets);
    }

    public static Optional<StatementValidatedRecord> validateRecord(StatementRecord statementRecord, HashSet<Integer> referenceSets) {
        StatementValidatedRecord statementValidatedRecord = null;
        List<String> errors = new ArrayList<>(10);
        // Multiple References
        if (!referenceSets.add(statementRecord.reference())) {
            errors.add("Reference number " + statementRecord.reference() + " is duplicated");
            return Optional.of(new StatementValidatedRecord(statementRecord.reference(), statementRecord.description(), errors.toArray(new String[0])));
        }

        // End Balance Validator
        if (statementRecord.startBalance().add(statementRecord.mutation()).compareTo(statementRecord.endBalance()) != 0) {
            errors.add("End Balance" + statementRecord.endBalance() + " is invalid.");
        }
        // Validation for Start Balance > 0
        if (statementRecord.startBalance().doubleValue() < 0) {
            errors.add("Start Balance is " + statementRecord.startBalance() + ". Should be greater than zero.");
        }
        // Validation for End Balance > 0
        if (statementRecord.endBalance().doubleValue() < 0) {
            errors.add("End Balance is " + statementRecord.endBalance() + ". Should be greater than zero");
        }
        // Transaction date should be future Date
        if (isFutureDatePredicate.test(statementRecord.transactionDate())) {
            errors.add("Transaction Date " + statementRecord.transactionDate() + " is of future Date");
        }

        if (!errors.isEmpty()) {
            statementValidatedRecord = new StatementValidatedRecord(statementRecord.reference(), statementRecord.description(), errors.toArray(new String[0]));
        }

        return Optional.ofNullable(statementValidatedRecord);
    }

    public static Predicate<String> isFutureDatePredicate = date -> {
        // Parse the date string to a LocalDate using a specific format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateToValidate = LocalDate.parse(date, formatter);

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Check if the date is in the future
        return dateToValidate.isAfter(currentDate);
    };


}
