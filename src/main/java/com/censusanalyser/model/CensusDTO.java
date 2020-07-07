package com.censusanalyser.model;

public class CensusDTO {
    public String state;
    public String population;
    public String areaInSqKm;
    public String densityPerSqKm;
    //public String SrNo;
    //public String stateName;
    //public String TIN;
    public String stateCode;
    //public String UsStateId;
//    public String UsState;
//    public String UPopulation;
//    public String UsHousingUnits;
//    public String UsTotalArea;
//    public String UsWaterArea;
//    public String UsLandArea;
//    public String UsPopulationDensity;
//    public String UsHousingDensity;

    public CensusDTO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
    }

    public CensusDTO(IndiaStateCodeCSV indiaStateCodeCSV) {
//        SrNo=indiaStateCodeCSV.SrNo;
//        stateName=indiaStateCodeCSV.stateName;
//        TIN=indiaStateCodeCSV.TIN;
        stateCode = indiaStateCodeCSV.stateCode;
    }

    public CensusDTO(USCensusCSV usCensusCSV) {
//        UsStateId=usCensusCSV.UsStateId;
//        UsState=usCensusCSV.UsState;
//        UsPopulation=usCensusCSV.UsPopulation;
//        UsHousingDensity=usCensusCSV.UsHousingDensity;
//        UsHousingUnits=usCensusCSV.UsHousingUnits;
//        UsTotalArea=usCensusCSV.UsTotalArea;
//        UsWaterArea=usCensusCSV.UsWaterArea;
//        UsLandArea=usCensusCSV.UsLandArea;
//        UsPopulationDensity=usCensusCSV.UsPopulationDensity;
        state = usCensusCSV.state;
        population = usCensusCSV.population;
        areaInSqKm = usCensusCSV.areaInSqKm;
        densityPerSqKm = usCensusCSV.densityPerSqKm;
        stateCode = usCensusCSV.stateCode;
    }
}

