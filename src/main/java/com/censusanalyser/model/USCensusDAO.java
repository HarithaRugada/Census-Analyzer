package com.censusanalyser.model;

public class USCensusDAO {
    public String stateId;
    public String state;
    public String population;
    public String housingUnits;
    public String totalArea;
    public String waterArea;
    public String landArea;
    public String populationDensity;
    public String housingDensity;

    public USCensusDAO(USCensusCSV usCensusCSV) {
        stateId=usCensusCSV.stateId;
        state=usCensusCSV.state;
        population=usCensusCSV.population;
        housingDensity=usCensusCSV.housingDensity;
        housingUnits=usCensusCSV.housingUnits;
        totalArea=usCensusCSV.totalArea;
        waterArea=usCensusCSV.waterArea;
        landArea=usCensusCSV.landArea;
        populationDensity=usCensusCSV.populationDensity;
    }
}
