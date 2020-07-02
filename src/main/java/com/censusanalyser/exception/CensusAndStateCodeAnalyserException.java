package com.censusanalyser.exception;

public class CensusAndStateCodeAnalyserException extends Exception {

    public enum ExceptionType {
        FILE_PROBLEM, UNABLE_TO_PARSE, NO_FILE, INCORRECT_DELIMITER_OR_HEADER_ISSUE
    }

    public ExceptionType type;

    public CensusAndStateCodeAnalyserException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }

    public CensusAndStateCodeAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
