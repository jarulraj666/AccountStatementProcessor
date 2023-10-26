package com.rabobank.statement.processor;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CsvAccountStatementProcessorTest {

    AccountStatementProcessor csvAccountStatementProcessor;

    @Test
    public void WhenValidFile_ProcessFileWithValidatedResult() throws IOException {
        csvAccountStatementProcessor = new CsvAccountStatementProcessor();
        var result = csvAccountStatementProcessor.validateAccountStatementFile("src/test/resources/records.csv");
        assertNotNull(result);
        assertEquals(5, result.size());
    }

    @Test
    public void WhenValidFileWithValidContent_ProcessFileWithValidatedResult() throws IOException {
        csvAccountStatementProcessor = new CsvAccountStatementProcessor();
        var result = csvAccountStatementProcessor.validateAccountStatementFile("src/test/resources/records_all_valid.csv");
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
