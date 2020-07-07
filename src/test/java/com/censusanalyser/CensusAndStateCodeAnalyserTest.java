package com.censusanalyser;

import com.censusanalyser.exception.CensusAndStateCodeAnalyserException;
import com.censusanalyser.model.IndiaCensusCSV;
import com.censusanalyser.model.IndiaStateCodeCSV;
import com.censusanalyser.model.USCensusCSV;
import com.censusanalyser.service.CensusAndStateCodeAnalyser;
import com.censusanalyser.utility.SortField;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAndStateCodeAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_INDIA_CENSUS_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_INDIA_CENSUS_FILE_TYPE = "./src/test/resources/IndiaStateCensusData.txt";
    private static final String WRONG_INDIA_CENSUS_DELIMITER_FILE = "./src/test/resources/IndiaStateCensusDataWrongDelimiter.csv";
    private static final String WRONG_INDIA_CENSUS_HEADER_FILE = "./src/test/resources/IndiaStateCensusDataWrongHeader.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CODE_CSV_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CODE_FILE_TYPE = "./src/test/resources/IndiaStateCode.txt";
    private static final String WRONG_STATE_CODE_DELIMITER_FILE = "./src/test/resources/IndiaStateCodeWrongDelimiter.csv";
    private static final String WRONG_STATE_CODE_HEADER_FILE = "./src/test/resources/IndiaStateCodeWrongHeader.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

    CensusAndStateCodeAnalyser censusAndStateCodeAnalyser = new CensusAndStateCodeAnalyser();

    @Test
    public void givenIndianCensusCSVFile_ShouldReturnCorrectRecords() {
        try {
            int numOfRecords = censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAndStateCodeAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAndStateCodeAnalyserException.class);
            censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.INDIA, WRONG_INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAndStateCodeAnalyserException e) {
            Assert.assertEquals(CensusAndStateCodeAnalyserException.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongType_ShouldThrowException() {
        try{
            censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.INDIA, WRONG_INDIA_CENSUS_FILE_TYPE);
        }catch (CensusAndStateCodeAnalyserException e) {
            Assert.assertEquals(CensusAndStateCodeAnalyserException.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenCSVFile_WhenDelimiterIncorrect_ShouldThrowException() {
        try{
            censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.INDIA, WRONG_INDIA_CENSUS_DELIMITER_FILE);
        }catch (CensusAndStateCodeAnalyserException e) {
            Assert.assertEquals(CensusAndStateCodeAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER_ISSUE, e.type);
        }
    }

    @Test
    public void givenIndianStateCSV_ShouldReturnExactCount() {
        try {
            int indiaStateCode =censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29, indiaStateCode);
        } catch (CensusAndStateCodeAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAndStateCodeAnalyser.getStateWiseSortedCensusData(SortField.STATE);
            IndiaCensusCSV[] censusCSV= new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[censusCSV.length-1].state);
        } catch (CensusAndStateCodeAnalyserException e) {

        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnStateCode_ShouldReturnSortedResult() throws CensusAndStateCodeAnalyserException {
        try {
            censusAndStateCodeAnalyser = new CensusAndStateCodeAnalyser(CensusAndStateCodeAnalyser.Country.INDIA);
            censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAndStateCodeAnalyser.getStateWiseSortedCensusData(SortField.STATE_CODE);
            IndiaStateCodeCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCodeCSV[].class);
            Assert.assertEquals("AP", censusCSV[censusCSV.length - 1].stateCode);
        }catch (CensusAndStateCodeAnalyserException e){
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() throws CensusAndStateCodeAnalyserException {
        censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
        String sortedCensusData = censusAndStateCodeAnalyser.getStateWiseSortedCensusData(SortField.POPULATION);
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("91347736", censusCSV[0].population);
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() throws CensusAndStateCodeAnalyserException {
        censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
        String sortedCensusData = censusAndStateCodeAnalyser.getStateWiseSortedCensusData(SortField.POPULATION_DENSITY);
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("86", censusCSV[0].densityPerSqKm);
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnTotalArea_ShouldReturnSortedResult() throws CensusAndStateCodeAnalyserException {
        censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
        String sortedCensusData = censusAndStateCodeAnalyser.getStateWiseSortedCensusData(SortField.TOTAL_AREA);
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("94163", censusCSV[0].areaInSqKm);
    }

    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecord() {
        try {
            int censusDataCount = censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, censusDataCount);
        }catch (CensusAndStateCodeAnalyserException e){

        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnState_ShouldReturnSortedResult() throws CensusAndStateCodeAnalyserException {
        censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAndStateCodeAnalyser.getStateWiseSortedCensusData(SortField.STATE);
        USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
        Assert.assertEquals("Wyoming", censusCSV[0].state);
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() throws CensusAndStateCodeAnalyserException {
        censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAndStateCodeAnalyser.getStateWiseSortedCensusData(SortField.POPULATION);
        USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
        Assert.assertEquals("989415", censusCSV[0].population);
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() throws CensusAndStateCodeAnalyserException {
        censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAndStateCodeAnalyser.getStateWiseSortedCensusData(SortField.POPULATION_DENSITY);
        System.out.println(sortedCensusData);
        USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
        Assert.assertEquals("92.32", censusCSV[0].densityPerSqKm);
    }

    @Test
    public void givenUSCensusData_WhenSortedOnTotalArea_ShouldReturnSortedResult() throws CensusAndStateCodeAnalyserException {
        censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAndStateCodeAnalyser.getStateWiseSortedCensusData(SortField.TOTAL_AREA);
        System.out.println(sortedCensusData);
        USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
        Assert.assertEquals("94326.27", censusCSV[0].areaInSqKm);
    }

    @Test
    public void givenUSCensusData_WhenSortedOnStateCode_ShouldReturnSortedResult() throws CensusAndStateCodeAnalyserException {
        censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAndStateCodeAnalyser.getStateWiseSortedCensusData(SortField.STATE_CODE);
        System.out.println(sortedCensusData);
        USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
        Assert.assertEquals("WY", censusCSV[0].stateCode);
    }

    @Test
    public void givenIndiaAndUSCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() throws CensusAndStateCodeAnalyserException {
        censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAndStateCodeAnalyser.getStateWiseSortedCensusData(SortField.STATE_CODE);
        USCensusCSV[] usCensusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
        Assert.assertEquals("WY", usCensusCSV[0].stateCode);

        censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
        String sortedIndiaCensusData = censusAndStateCodeAnalyser.getStateWiseSortedCensusData(SortField.POPULATION_DENSITY);
        IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortedIndiaCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("86", indiaCensusCSV[0].densityPerSqKm);
    }
}