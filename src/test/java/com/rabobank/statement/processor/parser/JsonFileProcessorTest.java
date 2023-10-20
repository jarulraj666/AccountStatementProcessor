package com.rabobank.statement.processor.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

public class JsonFileProcessorTest {

    JsonFileProcessor jsonFileProcessor;


    @Test
    public void WhenValidFile_ProcessFileWithValidatedResult() throws IOException {
        jsonFileProcessor = new JsonFileProcessor();
        var result = jsonFileProcessor.processFile("src/test/resources/records.json");
        assertNotNull(result);
    }
}
