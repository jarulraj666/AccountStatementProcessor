package com.rabobank.statement.processor.parser;

import com.rabobank.statement.processor.model.StatementRecord;
import com.rabobank.statement.processor.model.AccountStatementValidatedRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

final class CsvAccountStatementProcessor implements AccountStatementProcessor {

    public static final String REFERENCE = "Reference";
    public static final String ACCOUNT_NUMBER = "Account Number";
    public static final String DESCRIPTION = "Description";
    public static final String START_BALANCE = "Start Balance";
    public static final String MUTATION = "Mutation";
    public static final String END_BALANCE = "End Balance";
    public static final String TRANSACTION_DATE = "Transaction Date";

    @Override
    public List<AccountStatementValidatedRecord> validateAccountStatementFile(String filePath) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<CompletableFuture<AccountStatementValidatedRecord>> completableFutures;
        HashSet<Integer> referenceSets = new HashSet<>();
        try (FileReader reader = new FileReader(filePath)) {

            CSVParser parser = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader);

            completableFutures = parser.stream().map(record -> {
                StatementRecord statementRecord = new StatementRecord(Integer.valueOf(record.get(REFERENCE)), record.get(ACCOUNT_NUMBER), record.get(DESCRIPTION), new BigDecimal(record.get(START_BALANCE)), new BigDecimal(record.get(MUTATION)), new BigDecimal(record.get(END_BALANCE)), record.get(TRANSACTION_DATE));

                return CompletableFuture.supplyAsync(() -> {
                    var statementRecordValidatedOp = AccountStatementRecordValidator.validatedRecord(statementRecord, referenceSets);
                    return statementRecordValidatedOp.orElse(null);
                }, executor);
            }).collect(Collectors.toList());

            return completableFutures.stream().map(CompletableFuture::join).filter(Objects::nonNull).collect(Collectors.toList());
        } finally {
            executor.shutdown();
        }
    }
}
