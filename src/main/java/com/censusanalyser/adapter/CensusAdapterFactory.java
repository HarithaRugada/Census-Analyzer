package com.censusanalyser.adapter;


import com.censusanalyser.exception.CensusAndStateCodeAnalyserException;
import com.censusanalyser.model.CensusDTO;
import com.censusanalyser.service.CensusAndStateCodeAnalyser;

import java.util.Map;

public class CensusAdapterFactory {
    public static Map<String, CensusDTO> getCensusAdapter(CensusAndStateCodeAnalyser.Country country, String... csvFilePath) throws CensusAndStateCodeAnalyserException {
        if (country.equals(CensusAndStateCodeAnalyser.Country.INDIA))
            return new IndiaCensusAdapter().loadCensusData(csvFilePath);
        else if (country.equals(CensusAndStateCodeAnalyser.Country.US))
            return new USCensusAdapter().loadCensusData(csvFilePath);
        else
            throw new CensusAndStateCodeAnalyserException("Invalid Country", (CensusAndStateCodeAnalyserException.ExceptionType.INVALID_COUNTRY));
    }
}

