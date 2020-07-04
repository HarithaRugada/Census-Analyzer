package com.censusanalyser.model;

public class IndiaCensusDAO {
    public String state;
    public String population;
    public String areaInSqKm;
    public String densityPerSqKm;
    public String SrNo;
    public String stateName;
    public String TIN;
    public String stateCode;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
    }

    public IndiaCensusDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
        SrNo=indiaStateCodeCSV.SrNo;
        stateName=indiaStateCodeCSV.stateName;
        TIN=indiaStateCodeCSV.TIN;
        stateCode=indiaStateCodeCSV.stateCode;
    }
}
