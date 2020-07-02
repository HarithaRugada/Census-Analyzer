package com.censusanalyser.service;

import com.censusanalyser.exception.CensusAndStateCodeAnalyserException;
import com.censusanalyser.model.IndiaCensusCSV;
import com.censusanalyser.model.IndiaStateCodeCSV;
import com.google.gson.Gson;
import csvbuilder.CSVBuilderException;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAndStateCodeAnalyser {
    List<IndiaCensusCSV> indiaCensusCSVList = null;
    List<IndiaStateCodeCSV> indiaStateCodeCSVList = null;

    public int loadIndiaCensusData(String csvFilePath) throws CensusAndStateCodeAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            indiaCensusCSVList = icsvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return indiaCensusCSVList.size();
        } catch (NoSuchFileException e) {
            throw new CensusAndStateCodeAnalyserException("No Such File Exists", CensusAndStateCodeAnalyserException.ExceptionType.NO_FILE);
        } catch (IllegalStateException e) {
            throw new CensusAndStateCodeAnalyserException(e.getMessage(), CensusAndStateCodeAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (IOException e) {
            throw new CensusAndStateCodeAnalyserException(e.getMessage(), CensusAndStateCodeAnalyserException.ExceptionType.FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAndStateCodeAnalyserException("Incorrect Delimiter or Incorrect Header", CensusAndStateCodeAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER_ISSUE);
        } catch (CSVBuilderException e) {
            throw new CensusAndStateCodeAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public int loadIndiaStateCode(String csvFilePath) throws CensusAndStateCodeAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            indiaStateCodeCSVList = icsvBuilder.getCSVFileList(reader, IndiaStateCodeCSV.class);
            return indiaStateCodeCSVList.size();
        } catch (NoSuchFileException e) {
            throw new CensusAndStateCodeAnalyserException("No Such File Exists", CensusAndStateCodeAnalyserException.ExceptionType.NO_FILE);
        } catch (IllegalStateException e) {
            throw new CensusAndStateCodeAnalyserException(e.getMessage(), CensusAndStateCodeAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (IOException e) {
            throw new CensusAndStateCodeAnalyserException(e.getMessage(), CensusAndStateCodeAnalyserException.ExceptionType.FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAndStateCodeAnalyserException("Incorrect Delimiter or Incorrect Header", CensusAndStateCodeAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER_ISSUE);
        } catch (CSVBuilderException e) {
            throw new CensusAndStateCodeAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numberOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numberOfEntries;
    }

    public String getStateWiseSortedCensusData() {
        indiaCensusCSVList.sort(((Comparator<IndiaCensusCSV>)
                (census1, census2) -> census2.state.compareTo(census1.state)).reversed());
        String sortedCensusData = new Gson().toJson(indiaCensusCSVList);
        return sortedCensusData;
    }

    public String getStateCodeSortedData() {
        indiaStateCodeCSVList.sort(((Comparator<IndiaStateCodeCSV>) (stateCode1, stateCode2) -> stateCode2
                .stateCode.compareTo(stateCode1.stateCode)).reversed());
        String sortedStateCodeData = new Gson().toJson(indiaStateCodeCSVList);
        return sortedStateCodeData;
    }

    public String getPopulationSortedData() {
        indiaCensusCSVList.sort(((Comparator<IndiaCensusCSV>)
                (census1, census2) -> census2.population.compareTo(census1.population)).reversed());
        String sortedCensusData = new Gson().toJson(indiaCensusCSVList);
        return sortedCensusData;
    }

//    public <E> void sort(Comparator<E> comparator, List<E> list) {
//        for (int i = 0; i < list.size() - 1; i++) {
//            for (int j = 0; j < list.size() - i - 1; j++) {
//                E csv1 = (E) list.get(j);
//                E csv2 = (E) list.get(j + 1);
//                if (comparator.compare(csv1, csv2) > 0) {
//                    list.set(j, csv2);
//                    list.set(j + 1, csv1);
//                }
//            }
//        }
//    }
}
