package com.censusanalyser;

import com.censusanalyser.exception.CensusAndStateCodeAnalyserException;
import com.censusanalyser.model.CensusDAO;
import com.censusanalyser.service.CensusAndStateCodeAnalyser;
import com.censusanalyser.utility.SortField;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Comparator;
import java.util.Map;

public class CensusAndStateCodeAnalyserMockitoTest {
    public class CensusAnalyserMockitoTest {

        private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
        private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
        private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
        @Mock
        CensusAndStateCodeAnalyser censusAndStateCodeAnalyser;

        @Rule
        public MockitoRule mockitoRule = MockitoJUnit.rule();


        public class CensusAnalyserTestWithMockito {


            private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
            private static final String INDIA_STATE_CODE_CSV_PATH = "./src/test/resources/IndiaStateCode.csv";
            private static final String US_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

            Map<String, CensusDAO> censusMap;
            Map<SortField, Comparator> sortParameterComparator;

//            public void setUp() throws Exception {
//                this.censusMap = new HashMap<>();
//                sortParameterComparator = new HashMap<>();
//                this.censusMap.put("Odisha", new CensusDAO("Odisha", 298757, 2987.56, 5678.98, "OD"));
//                this.censusMap.put("Maharastra", new CensusDAO("Maharastra", 5489364, 89564.9, 768.44, "MH"));
//                this.censusMap.put("Karnataka", new CensusDAO("Karnataka", 7689699, 7686.8, 7568.8, "KA"));
//                Comparator<CensusDAO> populationComparator = Comparator.comparing(censusCSV -> censusCSV.population);
//                sortParameterComparator.put(SortField.POPULATION, populationComparator);
//
//                MockitoAnnotations.initMocks(this);
//            }
//
//            @Test
//            public void givenIndiaCensusCSVFile_ShouldReturnCorrectRecords() {
//                try {
//                    CensusAndStateCodeAnalyser censusAndStateCodeAnalyser = mock(CensusAndStateCodeAnalyser.class);
//                    when(censusAndStateCodeAnalyser.loadCountryCensusData(CensusAndStateCodeAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH)).thenReturn(this.censusDAOMap.size());
//                    int censusRecords = censusAndStateCodeAnalyser.loadCountryCensusData(CensusAndStateCodeAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
//                    Assert.assertEquals(3, censusRecords);
//                } catch (CensusAndStateCodeAnalyserException e) {
//                    e.printStackTrace();
//                }
//            }

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
}
