package com.censusanalyser.service;

import com.censusanalyser.exception.CensusAndStateCodeAnalyserException;
import com.censusanalyser.model.*;
import com.censusanalyser.utility.CensusAndStateCodeAnalyserUtility;
import com.google.gson.Gson;
import csvbuilder.CSVBuilderException;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAndStateCodeAnalyser {
    //List<IndiaCensusCSV> indiaCensusCSVList = null;
    //List<IndiaStateCodeCSV> indiaStateCodeCSVList = null;
    //List<USCensusCSV> usCensusCSVList = null;
    List<IndiaCensusDAO> indiaCensusList = null;
    List<IndiaCensusDAO> indiaStateCodeList = null;
    List<USCensusDAO> usCensusList = null;

    public CensusAndStateCodeAnalyser() {
        this.indiaCensusList = new ArrayList<>();
        this.indiaStateCodeList = new ArrayList<>();
        this.usCensusList = new ArrayList<>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAndStateCodeAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            //indiaCensusCSVList = icsvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            Iterator<IndiaCensusCSV> csvIterator = icsvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (csvIterator.hasNext()) {
                this.indiaCensusList.add(new IndiaCensusDAO(csvIterator.next()));
            }
            return indiaCensusList.size();
        } catch (NoSuchFileException e) {
            throw new CensusAndStateCodeAnalyserException("No Such File Exists", CensusAndStateCodeAnalyserException.ExceptionType.NO_FILE);
        } catch (IOException e) {
            throw new CensusAndStateCodeAnalyserException("Problem with File", CensusAndStateCodeAnalyserException.ExceptionType.FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAndStateCodeAnalyserException("Incorrect Delimiter or Incorrect Header", CensusAndStateCodeAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER_ISSUE);
        } catch (CSVBuilderException e) {
            throw new CensusAndStateCodeAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public int loadIndiaStateCode(String csvFilePath) throws CensusAndStateCodeAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            //indiaStateCodeCSVList = icsvBuilder.getCSVFileList(reader, IndiaStateCodeCSV.class);
            Iterator<IndiaStateCodeCSV> csvIterator = icsvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            while (csvIterator.hasNext()) {
                this.indiaStateCodeList.add(new IndiaCensusDAO(csvIterator.next()));
            }
            return indiaStateCodeList.size();
        } catch (NoSuchFileException e) {
            throw new CensusAndStateCodeAnalyserException("No Such File Exists", CensusAndStateCodeAnalyserException.ExceptionType.NO_FILE);
        } catch (IOException e) {
            throw new CensusAndStateCodeAnalyserException("Problem With File", CensusAndStateCodeAnalyserException.ExceptionType.FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAndStateCodeAnalyserException("Incorrect Delimiter or Incorrect Header", CensusAndStateCodeAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER_ISSUE);
        } catch (CSVBuilderException e) {
            throw new CensusAndStateCodeAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public int loadUSCensusData(String csvFilePath) throws CensusAndStateCodeAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            //usCensusCSVList = icsvBuilder.getCSVFileList(reader, USCensusCSV.class);
            Iterator<USCensusCSV> csvIterator = icsvBuilder.getCSVFileIterator(reader, USCensusCSV.class);
            while (csvIterator.hasNext()) {
                this.usCensusList.add(new USCensusDAO(csvIterator.next()));
            }
            return usCensusList.size();
        } catch (NoSuchFileException e) {
            throw new CensusAndStateCodeAnalyserException("No Such File Exists", CensusAndStateCodeAnalyserException.ExceptionType.NO_FILE);
        } catch (IOException e) {
            throw new CensusAndStateCodeAnalyserException("Problem with File", CensusAndStateCodeAnalyserException.ExceptionType.FILE_PROBLEM);
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
        indiaCensusList.sort(((Comparator<IndiaCensusDAO>)
                (census1, census2) -> census2.state.compareTo(census1.state)).reversed());
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    public String getStateCodeSortedData() {
        indiaStateCodeList.sort(((Comparator<IndiaCensusDAO>) (stateCode1, stateCode2) -> stateCode2
                .stateCode.compareTo(stateCode1.stateCode)).reversed());
        String sortedStateCodeData = new Gson().toJson(indiaStateCodeList);
        return sortedStateCodeData;
    }

    public String getPopulationSortedData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        indiaCensusList.sort(((Comparator<IndiaCensusDAO>)
                (census1, census2) -> census2.population.compareTo(census1.population)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", indiaCensusList);
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    public String getDensitySortedData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        indiaCensusList.sort(((Comparator<IndiaCensusDAO>)
                (census1, census2) -> census2.densityPerSqKm.compareTo(census1.densityPerSqKm)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", indiaCensusList);
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    public String getAreaSortedData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        indiaCensusList.sort(((Comparator<IndiaCensusDAO>)
                (census1, census2) -> census2.areaInSqKm.compareTo(census1.areaInSqKm)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", indiaCensusList);
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    public String getPopulationWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        usCensusList.sort(((Comparator<USCensusDAO>)
                (census1, census2) -> census2.population.compareTo(census1.population)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/USCensusJSON.json", usCensusList);
        String sortedUSCensusData = new Gson().toJson(usCensusList);
        return sortedUSCensusData;
    }

    public String getPopulationDensityWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        usCensusList.sort(((Comparator<USCensusDAO>)
                (census1, census2) -> census2.populationDensity.compareTo(census1.populationDensity)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/USCensusJSON.json", usCensusList);
        String sortedUSCensusData = new Gson().toJson(usCensusList);
        return sortedUSCensusData;
    }

    public String getHousingDensityWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        usCensusList.sort(((Comparator<USCensusDAO>)
                (census1, census2) -> census2.housingDensity.compareTo(census1.housingDensity)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/USCensusJSON.json", usCensusList);
        String sortedUSCensusData = new Gson().toJson(usCensusList);
        return sortedUSCensusData;
    }

    public String getLandAreaWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        usCensusList.sort(((Comparator<USCensusDAO>)
                (census1, census2) -> census2.landArea.compareTo(census1.landArea)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/USCensusJSON.json", usCensusList);
        String sortedUSCensusData = new Gson().toJson(usCensusList);
        return sortedUSCensusData;
    }
}
