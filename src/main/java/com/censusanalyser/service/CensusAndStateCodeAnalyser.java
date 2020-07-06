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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAndStateCodeAnalyser {
    Map<String, IndiaCensusDAO> indiaCensusMap = null;
    Map<String, IndiaCensusDAO> indiaStateCodeMap = null;
    Map<String, USCensusDAO> usCensusMap = null;

    public CensusAndStateCodeAnalyser() {
        this.indiaCensusMap = new HashMap<>();
        this.indiaStateCodeMap = new HashMap<>();
        this.usCensusMap = new HashMap<>();
    }

    //Loading the India Census Data File
    public int loadIndiaCensusData(String csvFilePath) throws CensusAndStateCodeAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvIterator = icsvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> CSVIterable = () -> csvIterator;
            StreamSupport.stream(CSVIterable.spliterator(), false)
                    .forEach(censusDAO -> indiaCensusMap.put(censusDAO.state, new IndiaCensusDAO(censusDAO)));
            return indiaCensusMap.size();
        } catch (NoSuchFileException e) {
            throw new CensusAndStateCodeAnalyserException("No Such File Exists", CensusAndStateCodeAnalyserException.ExceptionType.NO_FILE);
        } catch (IOException e) {
            throw new CensusAndStateCodeAnalyserException(e.getMessage(), CensusAndStateCodeAnalyserException.ExceptionType.FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAndStateCodeAnalyserException("Incorrect Delimiter or Incorrect Header", CensusAndStateCodeAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER_ISSUE);
        } catch (CSVBuilderException e) {
            throw new CensusAndStateCodeAnalyserException(e.getMessage(), e.type.name());
        }
    }

    //Loading the India State Code File
    public int loadIndiaStateCode(String csvFilePath) throws CensusAndStateCodeAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> csvIterator = icsvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> CSVIterable = () -> csvIterator;
            StreamSupport.stream(CSVIterable.spliterator(), false)
                    .forEach(censusDAO -> indiaStateCodeMap.put(censusDAO.stateName, new IndiaCensusDAO(censusDAO)));
            return indiaStateCodeMap.size();
        } catch (NoSuchFileException e) {
            throw new CensusAndStateCodeAnalyserException("No Such File Exists", CensusAndStateCodeAnalyserException.ExceptionType.NO_FILE);
        } catch (IOException e) {
            throw new CensusAndStateCodeAnalyserException(e.getMessage(), CensusAndStateCodeAnalyserException.ExceptionType.FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAndStateCodeAnalyserException("Incorrect Delimiter or Incorrect Header", CensusAndStateCodeAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER_ISSUE);
        } catch (CSVBuilderException e) {
            throw new CensusAndStateCodeAnalyserException(e.getMessage(), e.type.name());
        }
    }

    //Loading the US Census Data File
    public int loadUSCensusData(String csvFilePath) throws CensusAndStateCodeAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<USCensusCSV> csvIterator = icsvBuilder.getCSVFileIterator(reader, USCensusCSV.class);
            Iterable<USCensusCSV> CSVIterable = () -> csvIterator;
            StreamSupport.stream(CSVIterable.spliterator(), false)
                    .forEach(censusDAO -> usCensusMap.put(censusDAO.state, new USCensusDAO(censusDAO)));
            return usCensusMap.size();
        } catch (NoSuchFileException e) {
            throw new CensusAndStateCodeAnalyserException("No Such File Exists", CensusAndStateCodeAnalyserException.ExceptionType.NO_FILE);
        } catch (IOException e) {
            throw new CensusAndStateCodeAnalyserException(e.getMessage(), CensusAndStateCodeAnalyserException.ExceptionType.FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAndStateCodeAnalyserException("Incorrect Delimiter or Incorrect Header", CensusAndStateCodeAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER_ISSUE);
        } catch (CSVBuilderException e) {
            throw new CensusAndStateCodeAnalyserException(e.getMessage(), e.type.name());
        }
    }

    //Sorting the India Census Data according to State
    public String getStateWiseSortedCensusData() {
        List<IndiaCensusDAO> indiaCensusList = indiaCensusMap.values().stream().sorted(((Comparator<IndiaCensusDAO>)
                (census1, census2) -> census2.state.compareTo(census1.state)).reversed()).collect(Collectors.toList());
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    //Sorting the population
    public String getPopulationSortedData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        List<IndiaCensusDAO> indiaCensusList = indiaCensusMap.values().stream().sorted(((Comparator<IndiaCensusDAO>)
                (census1, census2) -> census2.population.compareTo(census1.population)).reversed()).collect(Collectors.toList());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", indiaCensusMap);
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    //Sorting the Density
    public String getDensitySortedData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        List<IndiaCensusDAO> indiaCensusList = indiaCensusMap.values().stream().sorted(((Comparator<IndiaCensusDAO>)
                (census1, census2) -> census2.densityPerSqKm.compareTo(census1.densityPerSqKm)).reversed()).collect(Collectors.toList());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", indiaCensusMap);
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    //Sorting the Area
    public String getAreaSortedData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        List<IndiaCensusDAO> indiaCensusList = indiaCensusMap.values().stream().sorted(((Comparator<IndiaCensusDAO>)
                (census1, census2) -> census2.areaInSqKm.compareTo(census1.areaInSqKm)).reversed()).collect(Collectors.toList());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", indiaCensusMap);
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    //Sorting the StateCode
    public String getStateCodeSortedData() {
        List<IndiaCensusDAO> indiaCensusList = indiaStateCodeMap.values().stream().sorted(((Comparator<IndiaCensusDAO>)
                (census1, census2) -> census2.stateCode.compareTo(census1.stateCode)).reversed()).collect(Collectors.toList());
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    //Sorting the TIN
    public String getTINSortedData() {
        List<IndiaCensusDAO> indiaCensusList = indiaStateCodeMap.values().stream().sorted(((Comparator<IndiaCensusDAO>)
                (census1, census2) -> census2.TIN.compareTo(census1.TIN)).reversed()).collect(Collectors.toList());
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    //Sorting Population of USCensus Data
    public String getPopulationWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        List<USCensusDAO> indiaCensusList = usCensusMap.values().stream().sorted(((Comparator<USCensusDAO>)
                (census1, census2) -> census2.population.compareTo(census1.population)).reversed()).collect(Collectors.toList());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", usCensusMap);
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    //Sorting Population Density of USCensus Data
    public String getPopulationDensityWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        List<USCensusDAO> indiaCensusList = usCensusMap.values().stream().sorted(((Comparator<USCensusDAO>)
                (census1, census2) -> census2.populationDensity.compareTo(census1.populationDensity)).reversed()).collect(Collectors.toList());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", usCensusMap);
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    //Sorting Housing Density of USCensus Data
    public String getHousingDensityWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        List<USCensusDAO> indiaCensusList = usCensusMap.values().stream().sorted(((Comparator<USCensusDAO>)
                (census1, census2) -> census2.housingDensity.compareTo(census1.housingDensity)).reversed()).collect(Collectors.toList());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", usCensusMap);
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    //Sorting LandArea of USCensus Data
    public String getLandAreaWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        List<USCensusDAO> indiaCensusList = usCensusMap.values().stream().sorted(((Comparator<USCensusDAO>)
                (census1, census2) -> census2.landArea.compareTo(census1.landArea)).reversed()).collect(Collectors.toList());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", usCensusMap);
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    //Sorting WaterArea of USCensus Data
    public String getWaterAreaWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        List<USCensusDAO> indiaCensusList = usCensusMap.values().stream().sorted(((Comparator<USCensusDAO>)
                (census1, census2) -> census2.waterArea.compareTo(census1.waterArea)).reversed()).collect(Collectors.toList());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", usCensusMap);
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    //Sorting state of USCensus Data
    public String getStateWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        List<USCensusDAO> indiaCensusList = usCensusMap.values().stream().sorted(((Comparator<USCensusDAO>)
                (census1, census2) -> census2.state.compareTo(census1.state)).reversed()).collect(Collectors.toList());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", usCensusMap);
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    //Sorting Housing Units of USCensus Data
    public String getHousingUnitsWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        List<USCensusDAO> indiaCensusList = usCensusMap.values().stream().sorted(((Comparator<USCensusDAO>)
                (census1, census2) -> census2.housingUnits.compareTo(census1.housingUnits)).reversed()).collect(Collectors.toList());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", usCensusMap);
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    //Sorting TotalArea of USCensus Data
    public String getTotalAreaWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        List<USCensusDAO> indiaCensusList = usCensusMap.values().stream().sorted(((Comparator<USCensusDAO>)
                (census1, census2) -> census2.totalArea.compareTo(census1.totalArea)).reversed()).collect(Collectors.toList());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", usCensusMap);
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    //Sorting Population Density of USCensus Data
    public String getMaximumPopulationDensityOfUSCensusDataAndIndiaCensusData() {
        List<USCensusDAO> usCensusList = usCensusMap.values().stream().sorted(((Comparator<USCensusDAO>)
                (census1, census2) -> census2.populationDensity.compareTo(census1.populationDensity))).collect(Collectors.toList());
        String usDensity = usCensusList.get(0).populationDensity;
        List<IndiaCensusDAO> indiaCensusList = indiaCensusMap.values().stream().sorted(((Comparator<IndiaCensusDAO>)
                (census1, census2) -> census2.densityPerSqKm.compareTo(census1.densityPerSqKm))).collect(Collectors.toList());
        String indiaPopulous = indiaCensusList.get(0).densityPerSqKm;
        double d1 = Double.parseDouble(usDensity);
        double d2 = Double.parseDouble(indiaPopulous);
        return d2 > d1 ? indiaCensusList.get(0).state : usCensusList.get(0).state;
    }
}