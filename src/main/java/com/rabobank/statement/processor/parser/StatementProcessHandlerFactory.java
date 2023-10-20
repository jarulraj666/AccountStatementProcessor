package com.rabobank.statement.processor.parser;

import com.rabobank.statement.processor.exception.FileTypeNotSupportedException;


class StatementProcessHandlerFactory {
    public static StatementProcessor createStatementProcessor(String filePath) throws FileTypeNotSupportedException {
        if (filePath.endsWith(".json")) {
            return new JsonFileProcessor();
        } else if (filePath.endsWith(".csv")) {
            return new CsvFileProcessor();
        }
        // Add more file type checks if needed

        throw new FileTypeNotSupportedException("File format not supported");
    }
}
