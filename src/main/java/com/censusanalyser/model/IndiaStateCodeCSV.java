package com.censusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {
    @CsvBindByName(column = "SrNo", required = true)
    public String srNo;

    @CsvBindByName(column = "State Name", required = true)
    public String stateName;

    @CsvBindByName(column = "TIN", required = true)
    public String tIN;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

    @Override
    public String toString() {
        return "IndiaStateCSV{" +
                "State='" + srNo + '\'' +
                ", Population='" + stateName + '\'' +
                ", AreaInSqKm='" + tIN + '\'' +
                ", DensityPerSqKm='" + stateCode + '\'' +
                '}';
    }
}
