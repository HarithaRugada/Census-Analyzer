package com.censusanalyser.model;

public class CensusDAO {
    public String state;
    public String population;
    public String areaInSqKm;
    public String densityPerSqKm;
    public String stateCode;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
    }

    public CensusDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
        stateCode = indiaStateCodeCSV.stateCode;
    }

    public CensusDAO(USCensusCSV usCensusCSV) {
        state = usCensusCSV.state;
        population = usCensusCSV.population;
        areaInSqKm = usCensusCSV.areaInSqKm;
        densityPerSqKm = usCensusCSV.densityPerSqKm;
        stateCode = usCensusCSV.stateCode;
    }

//    public Object getCensusDTOS(CensusAndStateCodeAnalyser.Country country) {
//        if (country.equals(CensusAndStateCodeAnalyser.Country.US))
//            return new USCensusCSV(state, stateCode, population, densityPerSqKm, areaInSqKm);
//        return new IndiaCensusCSV(state, population, densityPerSqKm, areaInSqKm);
//    }
}

