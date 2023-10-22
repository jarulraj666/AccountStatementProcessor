package com.rabobank.statement.processor;

import com.rabobank.statement.processor.model.AccountStatementValidatedRecord;

import java.io.IOException;
import java.util.List;

/**
 * AccountStatementProcessor Interface support to validate different file formats.
 * Currently, supported for Json, CSV file formats. Can be extended to other file formats based on the requirements
 */
sealed interface AccountStatementProcessor permits JsonAccountStatementProcessor, CsvAccountStatementProcessor {
    List<AccountStatementValidatedRecord> validateAccountStatementFile(String filePath) throws IOException;
}

