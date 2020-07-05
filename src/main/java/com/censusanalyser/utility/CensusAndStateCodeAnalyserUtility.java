package com.censusanalyser.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CensusAndStateCodeAnalyserUtility {
    //Creating Json File
    public String createJsonFile(String filePath, Map csvMap) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(csvMap, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }
}
