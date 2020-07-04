package com.censusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {
    @CsvBindByName(column = "SrNo", required = true)
    public String SrNo;

    @CsvBindByName(column = "State Name", required = true)
    public String stateName;

    @CsvBindByName(column = "TIN", required = true)
    public String TIN;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

    @Override
    public String toString() {
        return "IndiaStateCSV{" +
                "State='" + SrNo + '\'' +
                ", Population='" + stateName + '\'' +
                ", AreaInSqKm='" + TIN + '\'' +
                ", DensityPerSqKm='" + stateCode + '\'' +
                '}';
    }
}
