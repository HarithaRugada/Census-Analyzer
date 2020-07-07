package com.censusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {
    @CsvBindByName(column = "State Id", required = true)
    public String stateCode;

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public String population;

    @CsvBindByName(column = "Housing units", required = true)
    public String UsHousingUnits;

    @CsvBindByName(column = "Total area", required = true)
    public String areaInSqKm;

    @CsvBindByName(column = "Water area", required = true)
    public String UsWaterArea;

    @CsvBindByName(column = "Land area", required = true)
    public String UsLandArea;

    @CsvBindByName(column = "Population Density", required = true)
    public String densityPerSqKm;

    @CsvBindByName(column = "Housing Density", required = true)
        public String UsHousingDensity;

        @Override
        public String toString() {
            return "usCensusCSV{" +
                    "State Id='" + stateCode + '\'' +
                    ", State ='" + state + '\'' +
                    ", Population='" + population + '\'' +
                    ", Housing Units='" + UsHousingUnits + '\'' +
                    ", Total Area='" + areaInSqKm + '\'' +
                    ", Water Area='" + UsWaterArea + '\'' +
                    ", Land Area='" + UsLandArea + '\'' +
                    ", Population Density='" + densityPerSqKm + '\'' +
                    ", Housing Density='" + UsHousingDensity + '\'' +
                '}';
    }
}
