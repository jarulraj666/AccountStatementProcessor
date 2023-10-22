# Account Statement Validator service
 
1. Java Library to validate account statement processor for Json/CSV file.
2. Use below maven dependency in your project

```
<dependency>
    <groupId>com.rabobank</groupId>
    <artifactId>customer-statement-validator</artifactId>
    <version>1.0.0</version>
</dependency>

```

3. Use AccountStatementValidator Public API to validate the account statement.
```
AccountStatementValidator validator = new AccountStatementValidatorImpl();
validator.validateAccountStatementFile(filePath);
```
