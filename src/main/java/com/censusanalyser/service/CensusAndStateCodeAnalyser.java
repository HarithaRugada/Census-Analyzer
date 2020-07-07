package com.censusanalyser.service;

import com.censusanalyser.adapter.CensusAdapterFactory;
import com.censusanalyser.exception.CensusAndStateCodeAnalyserException;
import com.censusanalyser.model.CensusDTO;
import com.censusanalyser.utility.CensusAndStateCodeAnalyserUtility;
import com.censusanalyser.utility.SortField;
import com.google.gson.Gson;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CensusAndStateCodeAnalyser {
    private Country country;

    public enum Country {INDIA, US}

    List<CensusDTO> censusList = null;
    Map<String, CensusDTO> censusMap = null;
    Map<SortField, Comparator<CensusDTO>> sortMap = null;

    public CensusAndStateCodeAnalyser() {
        this.sortMap = new HashMap<>();
        this.sortMap.put(SortField.STATE, Comparator.comparing(census -> census.state));
        this.sortMap.put(SortField.POPULATION, Comparator.comparing(census -> census.population));
        this.sortMap.put(SortField.POPULATION_DENSITY, Comparator.comparing(census -> census.densityPerSqKm));
        this.sortMap.put(SortField.TOTAL_AREA, Comparator.comparing(census -> census.areaInSqKm));
        this.sortMap.put(SortField.STATE_CODE, Comparator.comparing(census -> census.stateCode));

    }

    public CensusAndStateCodeAnalyser(Country country) {
        this.sortMap = new HashMap<>();
        this.country = country;
        this.sortMap.put(SortField.STATE, Comparator.comparing(census -> census.state));
        this.sortMap.put(SortField.POPULATION, Comparator.comparing(census -> census.population));
        this.sortMap.put(SortField.POPULATION_DENSITY, Comparator.comparing(census -> census.densityPerSqKm));
        this.sortMap.put(SortField.TOTAL_AREA, Comparator.comparing(census -> census.areaInSqKm));
        //this.sortMap.put(SortField.STATE_ID, Comparator.comparing(census -> census.));
        this.sortMap.put(SortField.STATE_CODE, Comparator.comparing(census -> census.stateCode));
    }

    public int loadCensusData(Country country, String... csvFilePath) throws CensusAndStateCodeAnalyserException {

        censusMap = CensusAdapterFactory.getCensusAdapter(country, csvFilePath);
        censusList = censusMap.values().stream().collect(Collectors.toList());
        return censusMap.size();
    }

    public String getStateWiseSortedCensusData(SortField sortField) throws CensusAndStateCodeAnalyserException {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAndStateCodeAnalyserException("No Census Data", CensusAndStateCodeAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        censusList = censusMap.values().stream().collect(Collectors.toList());
        this.sort(this.sortMap.get(sortField).reversed());
        new CensusAndStateCodeAnalyserUtility().createJsonFile("./src/test/resources/IndiaCensusJSON.json", censusMap);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    private void sort(Comparator<CensusDTO> censusComparator) {
        for (int i = 0; i < this.censusList.size() - 1; i++) {
            for (int j = 0; j < this.censusList.size() - i - 1; j++) {
                CensusDTO census1 = this.censusList.get(j);
                CensusDTO census2 = this.censusList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    this.censusList.set(j, census2);
                    this.censusList.set(j + 1, census1);
                }
            }
        }
    }
}