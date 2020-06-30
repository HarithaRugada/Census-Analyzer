package com.censusanalyser.exception;

public class CSVBuilderException extends Exception {
    public enum ExceptionType {
        FILE_PROBLEM, UNABLE_TO_PARSE, NO_FILE, INCORRECT_DELIMITER_OR_HEADER_ISSUE
    }

    public ExceptionType type;

    public CSVBuilderException(String message, ExceptionType type)
    {
        super(message);
        this.type=type;
    }
}
