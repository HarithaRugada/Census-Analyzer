package com.censusanalyser;

import com.censusanalyser.exception.CensusAndStateCodeAnalyserException;
import com.censusanalyser.model.IndiaCensusCSV;
import com.censusanalyser.model.IndiaStateCodeCSV;
import com.censusanalyser.service.CensusAndStateCodeAnalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

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

    CensusAndStateCodeAnalyser censusAndStateCodeAnalyser = new CensusAndStateCodeAnalyser();

    //TC-1.1
    @Test
    public void givenIndianCensusCSVFile_ReturnsCorrectRecords() {
        try {
            int numOfRecords = censusAndStateCodeAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAndStateCodeAnalyserException e) {
        }
    }

    //TC-1.2
    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            //ExpectedException exceptionRule = ExpectedException.none();
            //exceptionRule.expect(CensusAndStateCodeAnalyserException.class);
            censusAndStateCodeAnalyser.loadIndiaCensusData(WRONG_INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAndStateCodeAnalyserException e) {
            Assert.assertEquals(CensusAndStateCodeAnalyserException.ExceptionType.NO_FILE, e.type);
            System.out.println(e.getMessage());
        }
    }

    //TC-1.3
    @Test
    public void givenIndiaCensusData_WithWrongType_ShouldThrowException() {
        try {
            censusAndStateCodeAnalyser.loadIndiaCensusData(WRONG_INDIA_CENSUS_FILE_TYPE);
        } catch (CensusAndStateCodeAnalyserException e) {
            Assert.assertEquals(CensusAndStateCodeAnalyserException.ExceptionType.NO_FILE, e.type);
            System.out.println(e.getMessage());
        }
    }

    //TC-1.4
    @Test
    public void givenIndiaCensusData_WithWrongDelimiterFile_ShouldThrowException() {
        try {
            //ExpectedException expectedException = ExpectedException.none();
            //expectedException.expect(CensusAndStateCodeAnalyserException.class);
            censusAndStateCodeAnalyser.loadIndiaCensusData(WRONG_INDIA_CENSUS_DELIMITER_FILE);
        } catch (CensusAndStateCodeAnalyserException e) {
            Assert.assertEquals(CensusAndStateCodeAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER_ISSUE, e.type);
            System.out.println(e.getMessage());
        }
    }

    //1.5
    @Test
    public void givenIndiaCensusData_WithWrongHeader_ShouldThrowException() {
        try {
            //ExpectedException expectedException = ExpectedException.none();
            //expectedException.expect(CensusAndStateCodeAnalyserException.class);
            censusAndStateCodeAnalyser.loadIndiaCensusData(WRONG_INDIA_CENSUS_HEADER_FILE);
        } catch (CensusAndStateCodeAnalyserException e) {
            Assert.assertEquals(CensusAndStateCodeAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER_ISSUE, e.type);
            System.out.println(e.getMessage());
        }
    }

    //TC-2.1
    @Test
    public void givenIndianStateCodeCSVFile_ReturnsCorrectRecords() {
        try {
            int numOfRecords = censusAndStateCodeAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37, numOfRecords);
        } catch (CensusAndStateCodeAnalyserException e) {
        }
    }

    //TC-2.2
    @Test
    public void givenIndiaStateCodeCSVFile_WithWrongFile_ShouldThrowException() {
        try {
            //ExpectedException exceptionRule = ExpectedException.none();
            //exceptionRule.expect(CensusAndStateCodeAnalyserException.class);
            censusAndStateCodeAnalyser.loadIndiaStateCode(WRONG_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAndStateCodeAnalyserException e) {
            Assert.assertEquals(CensusAndStateCodeAnalyserException.ExceptionType.NO_FILE, e.type);
            System.out.println(e.getMessage());
        }
    }

    //TC-2.3
    @Test
    public void givenIndiaStateCodeCSVFile_WithWrongType_ShouldThrowException() {
        try {
            censusAndStateCodeAnalyser.loadIndiaStateCode(WRONG_STATE_CODE_FILE_TYPE);
        } catch (CensusAndStateCodeAnalyserException e) {
            Assert.assertEquals(CensusAndStateCodeAnalyserException.ExceptionType.NO_FILE, e.type);
            System.out.println(e.getMessage());
        }
    }

    //TC-2.4
    @Test
    public void givenIndiaStateCodeCSVFile_WithWrongDelimiterFile_ShouldThrowException() {
        try {
            //ExpectedException expectedException = ExpectedException.none();
            //expectedException.expect(CensusAndStateCodeAnalyserException.class);
            censusAndStateCodeAnalyser.loadIndiaStateCode(WRONG_STATE_CODE_DELIMITER_FILE);
        } catch (CensusAndStateCodeAnalyserException e) {
            Assert.assertEquals(CensusAndStateCodeAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER_ISSUE, e.type);
            System.out.println(e.getMessage());
        }
    }

    //2.5
    @Test
    public void givenIndiaStateCodeCSVFile_WithWrongHeader_ShouldThrowException() {
        try {
            //ExpectedException expectedException = ExpectedException.none();
            //expectedException.expect(CensusAndStateCodeAnalyserException.class);
            censusAndStateCodeAnalyser.loadIndiaStateCode(WRONG_STATE_CODE_HEADER_FILE);
        } catch (CensusAndStateCodeAnalyserException e) {
            Assert.assertEquals(CensusAndStateCodeAnalyserException.ExceptionType.INCORRECT_DELIMITER_OR_HEADER_ISSUE, e.type);
            System.out.println(e.getMessage());
        }
    }

    //3
    @Test
    public void givenIndiaCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            censusAndStateCodeAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAndStateCodeAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", indiaCensusCSV[0].state);
        } catch (CensusAndStateCodeAnalyserException e) {
            e.printStackTrace();
        }
    }

    //4
    @Test
    public void givenIndiaStateCodeData_WhenSortedByStateCode_ShouldReturnSortedResult() {
        try {
            censusAndStateCodeAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedStateCodeData = censusAndStateCodeAnalyser.getStateCodeSortedData();
            IndiaStateCodeCSV[] indiaStateCSVList = new Gson().fromJson(sortedStateCodeData, IndiaStateCodeCSV[].class);
            Assert.assertEquals("AD", indiaStateCSVList[0].stateCode);
        } catch (CensusAndStateCodeAnalyserException e) {
            e.printStackTrace();
        }
    }

    //5
    @Test
    public void givenIndiaCensusData_WhenSortedByPopulation_ShouldReturnSortedResult() {
        try {
            censusAndStateCodeAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAndStateCodeAnalyser.getPopulationSortedData();
            IndiaCensusCSV[] indiaCensusCSVList = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("10116752", indiaCensusCSVList[0].population);
        } catch (CensusAndStateCodeAnalyserException e) {
            e.printStackTrace();
        }
    }

    //6
    @Test
    public void givenIndiaCensusData_WhenSortedByDensity_ShouldReturnSortedResult() {
        try {
            censusAndStateCodeAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAndStateCodeAnalyser.getDensitySortedData();
            IndiaCensusCSV[] indiaCensusCSVList = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("1029", indiaCensusCSVList[0].densityPerSqKm);
        } catch (CensusAndStateCodeAnalyserException e) {
            e.printStackTrace();
        }
    }

}
