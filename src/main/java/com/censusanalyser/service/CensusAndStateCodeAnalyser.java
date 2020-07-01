package com.censusanalyser.service;
import csvbuilder.*;
import com.censusanalyser.exception.CensusAnalyserException;
import com.censusanalyser.model.IndiaCensusCSV;
import com.censusanalyser.model.IndiaStateCSV;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAndStateCodeAnalyser {

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> censusCSVIterator = icsvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            return this.getCount(censusCSVIterator);
        } catch (FileNotFoundException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.NO_FILE);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER_ISSUE);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public int loadIndiaStateCode(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCSV> stateCSVIterator = icsvBuilder.getCSVFileIterator(reader, IndiaStateCSV.class);
            return getCount(stateCSVIterator);
        } catch (FileNotFoundException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.NO_FILE);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER_ISSUE);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numberOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numberOfEntries;
    }
}
