package com.censusanalyser;

import com.censusanalyser.exception.CensusAnalyserException;
import com.censusanalyser.service.CensusAnalyser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.stream.StreamSupport;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "IndiaStateCensusData.csv";
    private static final String WRONG_FILE_TYPE = "IndiaStateCensusData.txt";

    //TC-1.1
    @Test
    public void givenIndianCensusCSVFile_ReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    //TC-1.2
    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    //TC-1.3
    @Test
    public void givenIndiaCensusData_WithWrongType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(WRONG_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    //TC-1.4
    @Test
    public void givenIndiaCensusData_WithWrongDelimiterFile_ShouldThrowException()
    {
        try
        {
            CensusAnalyser censusAnalyser=new CensusAnalyser();
            ExpectedException expectedException=ExpectedException.none();
            expectedException.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        }catch (CensusAnalyserException e)
        {
            e.printStackTrace();
        }
    }
}
