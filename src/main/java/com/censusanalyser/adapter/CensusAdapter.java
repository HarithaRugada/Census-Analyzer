package com.censusanalyser.adapter;

import com.censusanalyser.exception.CensusAndStateCodeAnalyserException;
import com.censusanalyser.model.CensusDAO;
import com.censusanalyser.model.IndiaCensusCSV;
import com.censusanalyser.model.USCensusCSV;
import csvbuilder.CSVBuilderException;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class CensusAdapter {
    public abstract Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAndStateCodeAnalyserException;

    public <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String csvFilePath) throws CensusAndStateCodeAnalyserException {
        Map<String, CensusDAO> censusMap = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<E> csvIterable = () -> csvIterator;
            if (censusCSVClass.getName().equals("com.censusanalyser.model.IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(loadCensus -> censusMap.put(loadCensus.state, new CensusDAO(loadCensus)));
            } else if (censusCSVClass.getName().equals("com.censusanalyser.model.USCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(loadCensus -> censusMap.put(loadCensus.state, new CensusDAO(loadCensus)));
            }
            return censusMap;
        } catch (IOException e) {
            throw new CensusAndStateCodeAnalyserException(e.getMessage(),
                    CensusAndStateCodeAnalyserException.ExceptionType.FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            //throw new CSVBuilderException(CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
            throw new CensusAndStateCodeAnalyserException(e.getMessage(), e.type.name());
        } catch (RuntimeException e) {
            throw new CensusAndStateCodeAnalyserException(e.getMessage(),
                    CensusAndStateCodeAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER_ISSUE);
        }
    }
}
