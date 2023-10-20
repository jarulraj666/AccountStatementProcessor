package com.rabobank.statement.processor.parser;

import com.rabobank.statement.processor.model.StatementValidatedRecord;

import java.io.IOException;
import java.util.List;

sealed interface StatementProcessor permits JsonFileProcessor, CsvFileProcessor {
    List<StatementValidatedRecord> processFile(String filePath) throws IOException;
}

