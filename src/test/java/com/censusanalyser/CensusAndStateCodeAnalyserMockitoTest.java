package com.censusanalyser;

import com.censusanalyser.exception.CensusAndStateCodeAnalyserException;
import com.censusanalyser.service.CensusAndStateCodeAnalyser;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class CensusAndStateCodeAnalyserMockitoTest {
    public class CensusAnalyserMockitoTest {

        private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
        private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
        private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
        @Mock
        CensusAndStateCodeAnalyser censusAndStateCodeAnalyser;

        @Rule
        public MockitoRule mockitoRule = MockitoJUnit.rule();


        @Test
        public void givenIndiaCensusCSVFile_ShouldReturnCorrectRecords() {
            try {
                int numOfRecords = censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
                Assert.assertEquals(29, numOfRecords);
            } catch (CensusAndStateCodeAnalyserException e) {
            }
        }

        @Test
        public void givenIndiaStateCodeCSVFile_ShouldReturnCorrectRecords() {
            try {
                int numOfRecords = censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
                Assert.assertEquals(29, numOfRecords);
            } catch (CensusAndStateCodeAnalyserException e) {
            }
        }

        @Test
        public void givenUSCensusCSVFile_ShouldReturnCorrectRecords() {
            try {
                int censusDataCount = censusAndStateCodeAnalyser.loadCensusData(CensusAndStateCodeAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
                Assert.assertEquals(51, censusDataCount);
            } catch (CensusAndStateCodeAnalyserException e) {
            }
        }
    }
}
