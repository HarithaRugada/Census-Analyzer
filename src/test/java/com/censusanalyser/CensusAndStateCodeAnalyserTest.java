package com.censusanalyser;

import com.censusanalyser.exception.CensusAnalyserException;
import com.censusanalyser.model.IndiaCensusCSV;
import com.censusanalyser.model.IndiaStateCSV;
import com.censusanalyser.service.CensusAndStateCodeAnalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAndStateCodeAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "IndiaStateCensusData.csv";
    private static final String WRONG_INDIA_CENSUS_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_INDIA_CENSUS_FILE_TYPE = "IndiaStateCensusData.txt";
    private static final String WRONG_INDIA_CENSUS_DELIMITER_FILE = "IndiaStateCensusDataWrongDelimiter.csv";
    private static final String WRONG_INDIA_CENSUS_HEADER_FILE = "IndiaStateCensusDataWrongHeader.csv";

    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "IndiaStateCode.csv";
    private static final String WRONG_STATE_CODE_CSV_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CODE_FILE_TYPE = "IndiaStateCode.txt";
    private static final String WRONG_STATE_CODE_DELIMITER_FILE = "IndiaStateCodeWrongDelimiter.csv";
    private static final String WRONG_STATE_CODE_HEADER_FILE = "IndiaStateCodeWrongHeader.csv";

    //TC-1.1
    @Test
    public void givenIndianCensusCSVFile_ReturnsCorrectRecords() {
        try {
            CensusAndStateCodeAnalyser censusAnalyser = new CensusAndStateCodeAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    //TC-1.2
    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAndStateCodeAnalyser censusAnalyser = new CensusAndStateCodeAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    //TC-1.3
    @Test
    public void givenIndiaCensusData_WithWrongType_ShouldThrowException() {
        try {
            CensusAndStateCodeAnalyser censusAnalyser = new CensusAndStateCodeAnalyser();
            censusAnalyser.loadIndiaCensusData(WRONG_INDIA_CENSUS_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    //TC-1.4
    @Test
    public void givenIndiaCensusData_WithWrongDelimiterFile_ShouldThrowException() {
        try {
            CensusAndStateCodeAnalyser censusAnalyser = new CensusAndStateCodeAnalyser();
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_INDIA_CENSUS_DELIMITER_FILE);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    //1.5
    @Test
    public void givenIndiaCensusData_WithWrongHeader_ShouldThrowException() {
        try {
            CensusAndStateCodeAnalyser censusAnalyser = new CensusAndStateCodeAnalyser();
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_INDIA_CENSUS_HEADER_FILE);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    //TC-2.1
    @Test
    public void givenIndianStateCodeCSVFile_ReturnsCorrectRecords() {
        try {
            CensusAndStateCodeAnalyser censusAndStateCodeAnalyser = new CensusAndStateCodeAnalyser();
            int numOfRecords = censusAndStateCodeAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    //TC-2.2
    @Test
    public void givenIndiaStateCodeCSVFile_WithWrongFile_ShouldThrowException() {
        try {
            CensusAndStateCodeAnalyser censusAndStateCodeAnalyser = new CensusAndStateCodeAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAndStateCodeAnalyser.loadIndiaStateCode(WRONG_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    //TC-2.3
    @Test
    public void givenIndiaStateCodeCSVFile_WithWrongType_ShouldThrowException() {
        try {
            CensusAndStateCodeAnalyser censusAndStateCodeAnalyser = new CensusAndStateCodeAnalyser();
            censusAndStateCodeAnalyser.loadIndiaStateCode(WRONG_STATE_CODE_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    //TC-2.4
    @Test
    public void givenIndiaStateCodeCSVFile_WithWrongDelimiterFile_ShouldThrowException() {
        try {
            CensusAndStateCodeAnalyser censusAndStateCodeAnalyser = new CensusAndStateCodeAnalyser();
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyserException.class);
            censusAndStateCodeAnalyser.loadIndiaStateCode(WRONG_STATE_CODE_DELIMITER_FILE);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    //2.5
    @Test
    public void givenIndiaStateCodeCSVFile_WithWrongHeader_ShouldThrowException() {
        try {
            CensusAndStateCodeAnalyser censusAndStateCodeAnalyser = new CensusAndStateCodeAnalyser();
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(CensusAnalyserException.class);
            censusAndStateCodeAnalyser.loadIndiaStateCode(WRONG_STATE_CODE_HEADER_FILE);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
        }
    }

    //3
    @Test
    public void givenIndiaCensusData_WhenSortedOnState_ShouldReturnSortedResult(){
        try {
            CensusAndStateCodeAnalyser censusAndStateCodeAnalyser = new CensusAndStateCodeAnalyser();
            censusAndStateCodeAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAndStateCodeAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", indiaCensusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    //4
    @Test
    public void givenIndiaStateCodeData_WhenSorted_ShouldReturnSortedResult(){
        try {
            CensusAndStateCodeAnalyser censusAndStateCodeAnalyser = new CensusAndStateCodeAnalyser();
            censusAndStateCodeAnalyser.loadIndiaCensusData(INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedStateCodeData = censusAndStateCodeAnalyser.getStateCodeSortedData();
            IndiaStateCSV[] indiaStateCSVList = new Gson().fromJson(sortedStateCodeData, IndiaStateCSV[].class);
            Assert.assertEquals("AD", indiaStateCSVList[0].StateCode);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}
