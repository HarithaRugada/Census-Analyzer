package com.censusanalyser.service;

import com.censusanalyser.exception.CensusAndStateCodeAnalyserException;
import com.censusanalyser.model.IndiaCensusCSV;
import com.censusanalyser.model.IndiaStateCodeCSV;
import com.censusanalyser.model.USCensusCSV;
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
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAndStateCodeAnalyser {
    List<IndiaCensusCSV> indiaCensusCSVList = null;
    List<IndiaStateCodeCSV> indiaStateCodeCSVList = null;
    List<USCensusCSV> usCensusCSVList = null;

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

    public int loadUSCensusData(String csvFilePath) throws CensusAndStateCodeAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            usCensusCSVList = icsvBuilder.getCSVFileList(reader, USCensusCSV.class);
            return usCensusCSVList.size();
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
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        indiaCensusCSVList.sort(((Comparator<IndiaCensusCSV>)
                (census1, census2) -> census2.population.compareTo(census1.population)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", indiaCensusCSVList);
        String sortedCensusData = new Gson().toJson(indiaCensusCSVList);
        return sortedCensusData;
    }

    public String getDensitySortedData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        indiaCensusCSVList.sort(((Comparator<IndiaCensusCSV>)
                (census1, census2) -> census2.densityPerSqKm.compareTo(census1.densityPerSqKm)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", indiaCensusCSVList);
        String sortedCensusData = new Gson().toJson(indiaCensusCSVList);
        return sortedCensusData;
    }

    public String getAreaSortedData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        indiaCensusCSVList.sort(((Comparator<IndiaCensusCSV>)
                (census1, census2) -> census2.areaInSqKm.compareTo(census1.areaInSqKm)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", indiaCensusCSVList);
        String sortedCensusData = new Gson().toJson(indiaCensusCSVList);
        return sortedCensusData;
    }

    public String getPopulationWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        usCensusCSVList.sort(((Comparator<USCensusCSV>)
                (census1, census2) -> census2.population.compareTo(census1.population)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/USCensusJSON.json", usCensusCSVList);
        String sortedUSCensusData = new Gson().toJson(usCensusCSVList);
        return sortedUSCensusData;
    }

    public String getPopulationDensityWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        usCensusCSVList.sort(((Comparator<USCensusCSV>)
                (census1, census2) -> census2.populationDensity.compareTo(census1.populationDensity)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/USCensusJSON.json", usCensusCSVList);
        String sortedUSCensusData = new Gson().toJson(usCensusCSVList);
        return sortedUSCensusData;
    }

    public String getHousingDensityWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        usCensusCSVList.sort(((Comparator<USCensusCSV>)
                (census1, census2) -> census2.housingDensity.compareTo(census1.housingDensity)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/USCensusJSON.json", usCensusCSVList);
        String sortedUSCensusData = new Gson().toJson(usCensusCSVList);
        return sortedUSCensusData;
    }

    public String getLandAreaWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        usCensusCSVList.sort(((Comparator<USCensusCSV>)
                (census1, census2) -> census2.landArea.compareTo(census1.landArea)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/USCensusJSON.json", usCensusCSVList);
        String sortedUSCensusData = new Gson().toJson(usCensusCSVList);
        return sortedUSCensusData;
    }
}
