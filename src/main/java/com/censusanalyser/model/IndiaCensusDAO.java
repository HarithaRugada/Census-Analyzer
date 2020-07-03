package com.censusanalyser.model;

public class IndiaCensusDAO {
    public String state;
    public String population;
    public String areaInSqKm;
    public String densityPerSqKm;
    public String srNo;
    public String stateName;
    public String tIN;
    public String stateCode;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
    }

    public IndiaCensusDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
        srNo=indiaStateCodeCSV.srNo;
        stateName=indiaStateCodeCSV.stateName;
        tIN=indiaStateCodeCSV.tIN;
        stateCode=indiaStateCodeCSV.stateCode;
    }
}
