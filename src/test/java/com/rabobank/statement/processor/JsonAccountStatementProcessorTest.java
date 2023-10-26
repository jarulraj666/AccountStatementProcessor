package com.rabobank.statement.processor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

public class JsonAccountStatementProcessorTest {

    JsonAccountStatementProcessor jsonAccountStatementProcessor;

    @Test
    public void WhenValidFile_ProcessFileWithValidatedResult() throws IOException {
        jsonAccountStatementProcessor = new JsonAccountStatementProcessor();
        var result = jsonAccountStatementProcessor.validateAccountStatementFile("src/test/resources/records.json");
        assertNotNull(result);
        assertEquals(12, result.size());
    }

    @Test
    public void WhenValidFileWithValidContent_ProcessFileWithValidatedResult() throws IOException {
        jsonAccountStatementProcessor = new JsonAccountStatementProcessor();
        var result = jsonAccountStatementProcessor.validateAccountStatementFile("src/test/resources/records_all_valid.json");
        assertNotNull(result);
        assertEquals(0, result.size());
    }

}
