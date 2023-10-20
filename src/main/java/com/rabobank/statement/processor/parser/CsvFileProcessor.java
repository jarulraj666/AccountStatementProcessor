package com.rabobank.statement.processor.parser;

import com.rabobank.statement.processor.model.StatementRecord;
import com.rabobank.statement.processor.model.StatementValidatedRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

final class CsvFileProcessor implements StatementProcessor {

    public static final String REFERENCE = "Reference";
    public static final String ACCOUNT_NUMBER = "Account Number";
    public static final String DESCRIPTION = "Description";
    public static final String START_BALANCE = "Start Balance";
    public static final String MUTATION = "Mutation";
    public static final String END_BALANCE = "End Balance";
    public static final String TRANSACTION_DATE = "Transaction Date";

    @Override
    public List<StatementValidatedRecord> processFile(String filePath) {
        return null;
        //return StatementRecordValidator.validatedRecords(readStatementCsvFile(filePath));
    }

    private List<StatementRecord> readStatementCsvFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            // Create a CSVParser with the desired format
            CSVParser parser = CSVFormat.DEFAULT
                    .parse(reader);
            return parser.stream().parallel().map(record -> {
                StatementRecord statementRecord = new StatementRecord(
                        Integer.valueOf(record.get(REFERENCE)), record.get(ACCOUNT_NUMBER), record.get(DESCRIPTION),
                        new BigDecimal(record.get(START_BALANCE)), new BigDecimal(record.get(MUTATION)),
                        new BigDecimal(record.get(END_BALANCE)), record.get(TRANSACTION_DATE));

                return statementRecord;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
