package com.censusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {
    @CsvBindByName(column = "State Id", required = true)
    public String stateId;

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public String population;

    @CsvBindByName(column = "Housing units", required = true)
    public int housingUnits;

    @CsvBindByName(column = "Total area", required = true)
    public Double totalArea;

    @CsvBindByName(column = "Water area", required = true)
    public Double waterArea;

    @CsvBindByName(column = "Land area", required = true)
    public Double landArea;

    @CsvBindByName(column = "Population Density", required = true)
    public Double populationDensity;

    @CsvBindByName(column = "Housing Density", required = true)
    public Double housingDensity;

    @Override
    public String toString() {
        return "usCensusCSV{" +
                "State Id='" + stateId + '\'' +
                ", State ='" + state + '\'' +
                ", Population='" + population + '\'' +
                ", Housing Units='" + housingUnits + '\'' +
                ", Total Area='" + totalArea + '\'' +
                ", Water Area='" + waterArea + '\'' +
                ", Land Area='" + landArea + '\'' +
                ", Population Density='" + populationDensity + '\'' +
                ", Housing Density='" + housingDensity + '\'' +
                '}';
    }
}
