package com.censusanalyser.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CensusAndStateCodeAnalyserUtility {
    public String createJsonFile(String filePath, List csvList) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(csvList, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }
}
