package com.censusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCSV {
    @CsvBindByName(column = "SrNo", required = true)
    public String SrNo;

    @CsvBindByName(column = "State Name", required = true)
    public int StateName;

    @CsvBindByName(column = "TIN", required = true)
    public int TIN;

    @CsvBindByName(column = "StateCode", required = true)
    public int StateCode;

    @Override
    public String toString() {
        return "IndiaStateCSV{" +
                "State='" + SrNo + '\'' +
                ", Population='" + StateName + '\'' +
                ", AreaInSqKm='" + TIN + '\'' +
                ", DensityPerSqKm='" + StateCode + '\'' +
                '}';
    }
}
