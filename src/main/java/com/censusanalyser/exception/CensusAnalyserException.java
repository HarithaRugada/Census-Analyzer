package com.censusanalyser.exception;

public class CensusAnalyserException extends Exception {

    public enum ExceptionType {
        FILE_PROBLEM, UNABLE_TO_PARSE, NO_FILE, INCORRECT_DELIMITER_OR_HEADER_ISSUE
    }

    public ExceptionType type;

    public CensusAnalyserException(String message, String name) {
        super(message);
        this.type=ExceptionType.valueOf(name);
    }

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
