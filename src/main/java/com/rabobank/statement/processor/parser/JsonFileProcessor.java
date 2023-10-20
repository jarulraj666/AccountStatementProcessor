package com.rabobank.statement.processor.parser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabobank.statement.processor.model.StatementRecord;
import com.rabobank.statement.processor.model.StatementValidatedRecord;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

final class JsonFileProcessor implements StatementProcessor {

    @Override
    public List<StatementValidatedRecord> processFile(String filePath) throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        File jsonFile = new File(filePath);

        ObjectMapper objectMapper = new ObjectMapper();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<CompletableFuture<StatementValidatedRecord>> completableFutures = new ArrayList<>();
        HashSet<Integer> referenceSets = new HashSet<>();
        try (JsonParser jsonParser = jsonFactory.createParser(jsonFile)) {
            if (jsonParser.nextToken() == JsonToken.START_ARRAY) {
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    StatementRecord data = objectMapper.readValue(jsonParser, StatementRecord.class);
                    CompletableFuture<StatementValidatedRecord> processingFutures = CompletableFuture.supplyAsync(() -> {
                        var statementRecordValidatedOp = StatementRecordValidator.validatedRecord(data, referenceSets);
                        if (statementRecordValidatedOp.isPresent()) {
                            return statementRecordValidatedOp.get();
                        } else {
                            return null;
                        }
                    }, executor);
                    completableFutures.add(processingFutures);
                }
            }

            return completableFutures.stream()
                    .map(CompletableFuture::join)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
