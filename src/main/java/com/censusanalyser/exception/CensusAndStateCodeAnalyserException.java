package com.censusanalyser.exception;

public class CensusAndStateCodeAnalyserException extends Exception {

    public enum ExceptionType {
        FILE_PROBLEM, INCORRECT_DELIMITER_OR_HEADER_ISSUE, NO_CENSUS_DATA, INVALID_COUNTRY, UNABLE_TO_PARSE
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
