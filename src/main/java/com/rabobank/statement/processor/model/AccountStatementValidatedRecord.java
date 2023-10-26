package com.rabobank.statement.processor.model;

import java.util.List;

/**
 * Processes the Statement Record and provides the validated result
 */
public record AccountStatementValidatedRecord(Integer reference, String description, List<String> errors) {
}
