package com.censusanalyser.service;

import com.censusanalyser.exception.CensusAnalyserException;
import com.censusanalyser.model.IndiaCensusCSV;
import com.censusanalyser.model.IndiaStateCSV;
import com.google.gson.Gson;
import csvbuilder.CSVBuilderException;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.ICSVBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAndStateCodeAnalyser {
    List<IndiaCensusCSV> indiaCensusCSVList = null;
    List<IndiaStateCSV> indiaStateCSVList = null;

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            indiaCensusCSVList = icsvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return indiaCensusCSVList.size();
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
            indiaStateCSVList = icsvBuilder.getCSVFileList(reader, IndiaStateCSV.class);
            return indiaStateCSVList.size();
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

    public String getStateWiseSortedCensusData() {

        Comparator<IndiaCensusCSV> indiaCensusCSVComparator = Comparator.comparing(census -> census.state);
        this.sort(indiaCensusCSVComparator,indiaCensusCSVList);
        String sortedStateCensusJson = new Gson().toJson(indiaCensusCSVList);
        return sortedStateCensusJson;
    }

    public String getStateCodeSortedData() {
        Comparator<IndiaStateCSV> indiaStateCodeComparator = Comparator.comparing(state -> state.StateCode);
        this.sort(indiaStateCodeComparator,indiaStateCSVList);
        String sortedStateCensusJson = new Gson().toJson(indiaCensusCSVList);
        return sortedStateCensusJson;
    }

    public<E> void sort(Comparator<E> comparator,List<E> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                E csv1 = (E) list.get(j);
                E csv2 = (E) list.get(j+1);
                if (comparator.compare(csv1,csv2) > 0) {
                    list.set(j, csv2);
                    list.set(j + 1, csv1);
                }
            }
        }
    }
}
