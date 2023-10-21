package com.rabobank.statement.processor.parser;

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
        assertEquals(4, result.size());
    }
}
