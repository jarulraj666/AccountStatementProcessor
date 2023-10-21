package com.rabobank.statement.processor.parser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabobank.statement.processor.model.StatementRecord;
import com.rabobank.statement.processor.model.AccountStatementValidatedRecord;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

final class JsonAccountStatementProcessor implements AccountStatementProcessor {

    @Override
    public List<AccountStatementValidatedRecord> validateAccountStatementFile(String filePath) throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        File jsonFile = new File(filePath);

        ObjectMapper objectMapper = new ObjectMapper();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<CompletableFuture<AccountStatementValidatedRecord>> completableFutures = new ArrayList<>();
        HashSet<Integer> referenceSets = new HashSet<>();
        try (JsonParser jsonParser = jsonFactory.createParser(jsonFile)) {
            if (jsonParser.nextToken() == JsonToken.START_ARRAY) {
                // Read json in streams to avoid loading entire file into memory
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    StatementRecord data = objectMapper.readValue(jsonParser, StatementRecord.class);
                    // Process StatementRecord asynchronously on multiple threads to perform parallel processing
                    CompletableFuture<AccountStatementValidatedRecord> processingFutures = CompletableFuture.supplyAsync(() -> {
                        var statementRecordValidatedOp = AccountStatementRecordValidator.validatedRecord(data, referenceSets);
                        return statementRecordValidatedOp.orElse(null);
                    }, executor);
                    completableFutures.add(processingFutures);
                }
            }

            return completableFutures.stream().map(CompletableFuture::join).filter(Objects::nonNull).collect(Collectors.toList());
        }
        finally {
            executor.shutdown();
        }

    }
}
