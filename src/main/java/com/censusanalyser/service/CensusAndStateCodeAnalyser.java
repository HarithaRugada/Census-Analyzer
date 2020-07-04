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

public class CensusAndStateCodeAnalyser {
    List<IndiaCensusDAO> indiaCensusList = null;
    List<IndiaCensusDAO> indiaStateCodeList = null;
    List<USCensusDAO> usCensusList = null;

    public CensusAndStateCodeAnalyser() {
        this.indiaCensusList = new ArrayList<>();
        this.indiaStateCodeList = new ArrayList<>();
        this.usCensusList = new ArrayList<>();
    }

    //Loading the India Census Data File
    public int loadIndiaCensusData(String csvFilePath) throws CensusAndStateCodeAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvIterator = icsvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (csvIterator.hasNext()) {
                this.indiaCensusList.add(new IndiaCensusDAO(csvIterator.next()));
            }
            return indiaCensusList.size();
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
            //indiaStateCodeCSVList = icsvBuilder.getCSVFileList(reader, IndiaStateCodeCSV.class);
            Iterator<IndiaStateCodeCSV> csvIterator = icsvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            while (csvIterator.hasNext()) {
                this.indiaStateCodeList.add(new IndiaCensusDAO(csvIterator.next()));
            }
            return indiaStateCodeList.size();
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
            while (csvIterator.hasNext()) {
                this.usCensusList.add(new USCensusDAO(csvIterator.next()));
            }
            return usCensusList.size();
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
        indiaCensusList.sort(((Comparator<IndiaCensusDAO>)
                (census1, census2) -> census2.state.compareTo(census1.state)).reversed());
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    //Sorting the StateCode
    public String getStateCodeSortedData() {
        indiaStateCodeList.sort(((Comparator<IndiaCensusDAO>) (stateCode1, stateCode2) -> stateCode2
                .stateCode.compareTo(stateCode1.stateCode)).reversed());
        String sortedStateCodeData = new Gson().toJson(indiaStateCodeList);
        return sortedStateCodeData;
    }

    //Sorting the population
    public String getPopulationSortedData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        indiaCensusList.sort(((Comparator<IndiaCensusDAO>)
                (census1, census2) -> census2.population.compareTo(census1.population)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", indiaCensusList);
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    //Sorting the Density
    public String getDensitySortedData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        indiaCensusList.sort(((Comparator<IndiaCensusDAO>)
                (census1, census2) -> census2.densityPerSqKm.compareTo(census1.densityPerSqKm)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", indiaCensusList);
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    //Sorting the Area
    public String getAreaSortedData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        indiaCensusList.sort(((Comparator<IndiaCensusDAO>)
                (census1, census2) -> census2.areaInSqKm.compareTo(census1.areaInSqKm)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/IndiaCensusJSON.json", indiaCensusList);
        String sortedCensusData = new Gson().toJson(indiaCensusList);
        return sortedCensusData;
    }

    //Sorting Population of USCensus Data
    public String getPopulationWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        usCensusList.sort(((Comparator<USCensusDAO>)
                (census1, census2) -> census2.population.compareTo(census1.population)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/USCensusJSON.json", usCensusList);
        String sortedUSCensusData = new Gson().toJson(usCensusList);
        return sortedUSCensusData;
    }

    //Sorting Population Density of USCensus Data
    public String getPopulationDensityWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        usCensusList.sort(((Comparator<USCensusDAO>)
                (census1, census2) -> census2.populationDensity.compareTo(census1.populationDensity)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/USCensusJSON.json", usCensusList);
        String sortedUSCensusData = new Gson().toJson(usCensusList);
        return sortedUSCensusData;
    }

    //Sorting Housing Density of USCensus Data
    public String getHousingDensityWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        usCensusList.sort(((Comparator<USCensusDAO>)
                (census1, census2) -> census2.housingDensity.compareTo(census1.housingDensity)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/USCensusJSON.json", usCensusList);
        String sortedUSCensusData = new Gson().toJson(usCensusList);
        return sortedUSCensusData;
    }

    //Sorting LandArea of USCensus Data
    public String getLandAreaWiseSortedUSCensusData() {
        CensusAndStateCodeAnalyserUtility censusAndStateCodeAnalyserUtility = new CensusAndStateCodeAnalyserUtility();
        usCensusList.sort(((Comparator<USCensusDAO>)
                (census1, census2) -> census2.landArea.compareTo(census1.landArea)).reversed());
        censusAndStateCodeAnalyserUtility.createJsonFile("./src/test/resources/USCensusJSON.json", usCensusList);
        String sortedUSCensusData = new Gson().toJson(usCensusList);
        return sortedUSCensusData;
    }

    //Sorting the Population Density of USCensus and IndiaCensus Data
    public String getPopulationDensityWiseSortedUSCensusDataAndIndiaCensusData() {
        usCensusList.sort(((Comparator<USCensusDAO>)
                (census1, census2) -> census2.populationDensity.compareTo(census1.populationDensity)).reversed());
        String usPopulous = usCensusList.get(0).populationDensity;
        indiaCensusList.sort(((Comparator<IndiaCensusDAO>)
                (census1, census2) -> census2.population.compareTo(census1.population)).reversed());
        String indiaPopulous = indiaCensusList.get(0).densityPerSqKm;
        double d1 = Double.parseDouble(usPopulous);
        double d2 = Double.parseDouble(indiaPopulous);
        if (d1 > d2)
            return usCensusList.get(0).state;
        return indiaCensusList.get(0).state;
    }
}