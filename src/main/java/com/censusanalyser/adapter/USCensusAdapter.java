package com.censusanalyser.adapter;

import com.censusanalyser.exception.CensusAndStateCodeAnalyserException;
import com.censusanalyser.model.CensusDTO;
import com.censusanalyser.model.USCensusCSV;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {

    @Override
    public Map<String, CensusDTO> loadCensusData(String... csvFilePath) throws CensusAndStateCodeAnalyserException {
        Map<String, CensusDTO> censusMap = super.loadCensusData(USCensusCSV.class, csvFilePath[0]);
        return censusMap;
    }
}