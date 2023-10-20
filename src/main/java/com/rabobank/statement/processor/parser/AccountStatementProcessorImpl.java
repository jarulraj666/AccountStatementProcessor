package com.rabobank.statement.processor.parser;

import com.rabobank.statement.processor.exception.FileTypeNotSupportedException;
import com.rabobank.statement.processor.model.StatementValidatedRecord;

import java.io.IOException;
import java.util.List;

public final class AccountStatementProcessorImpl implements AccountStatementProcessor {
    @Override
    public List<StatementValidatedRecord> validateFile(String filePath) throws FileTypeNotSupportedException, IOException {
        StatementProcessor handler = StatementProcessHandlerFactory.createStatementProcessor(filePath);
        return handler.processFile(filePath);
    }
}
