package com.rabobank.statement.processor;

import com.rabobank.statement.processor.model.StatementRecord;
import com.rabobank.statement.processor.model.AccountStatementValidatedRecord;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

/**
 * Static class to validate {@link StatementRecord} and return validated response
 */
class AccountStatementRecordValidator {

    /**
     * Method responsible to validate {@link StatementRecord}
     * @param statementRecord Account Statement Record
     * @param referenceSets Hold unique Reference Ids to find out duplicates
     * @return AccountStatementValidatedRecord
     */
    public static Optional<AccountStatementValidatedRecord> validatedRecord(StatementRecord statementRecord, HashSet<Integer> referenceSets) {
        AccountStatementValidatedRecord accountStatementValidatedRecord = null;
        List<String> errors = new ArrayList<>(10);

        // Multiple References
        if (!referenceSets.add(statementRecord.reference())) {
            errors.add("Reference number " + statementRecord.reference() + " is duplicated");
            return Optional.of(new AccountStatementValidatedRecord(statementRecord.reference(), statementRecord.description(), errors.toArray(new String[0])));
        }

        // End Balance Validator
        if (isNotValidBalancePredicate.test(statementRecord)) {
            errors.add("End Balance" + statementRecord.endBalance() + " is invalid.");
        }

        // Validation for Start Balance > 0
        if (isNotValidStartBalancePredicate.test(statementRecord)) {
            errors.add("Start Balance is " + statementRecord.startBalance() + ". Should be greater than zero.");
        }

        // Validation for End Balance > 0
        if (isNotValidEndBalancePredicate.test(statementRecord)) {
            errors.add("End Balance is " + statementRecord.endBalance() + ". Should be greater than zero");
        }

        // Transaction date should be future Date
        if (isFutureDatePredicate.test(statementRecord.transactionDate())) {
            errors.add("Transaction Date " + statementRecord.transactionDate() + " is of future Date");
        }

        if (!errors.isEmpty()) {
            accountStatementValidatedRecord = new AccountStatementValidatedRecord(statementRecord.reference(), statementRecord.description(), errors.toArray(new String[0]));
        }

        return Optional.ofNullable(accountStatementValidatedRecord);
    }

    /**
     * Predicate to Check whether the provides date is of future date
     */
    public static Predicate<String> isFutureDatePredicate = date -> {
        // Parse the date string to a LocalDate using a specific format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateToValidate = LocalDate.parse(date, formatter);

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Check if the date is in the future
        return dateToValidate.isAfter(currentDate);
    };

    // Predicate to check whether Valid Balance
    public static Predicate<StatementRecord> isNotValidBalancePredicate = statementRecord -> statementRecord.startBalance().add(statementRecord.mutation()).compareTo(statementRecord.endBalance()) != 0;

    // Predicate to check whether valid start Balance
    public static Predicate<StatementRecord> isNotValidStartBalancePredicate = statementRecord -> statementRecord.startBalance().doubleValue() < 0;

    // Predicate to check whether valid End Balance
    public static Predicate<StatementRecord> isNotValidEndBalancePredicate = statementRecord -> statementRecord.endBalance().doubleValue() < 0;

}
