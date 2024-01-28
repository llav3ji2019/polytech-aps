package com.polytech.aps.utils.file;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileSaverUtils {
    public static final String FILENAME = "statistic.txt";

    public static void saveData(List<String> data) {
        try(FileWriter writer = new FileWriter(FILENAME, false))
        {
            for (String el : data) {
                writer.write(el + '\n');
                writer.flush();
            }
        } catch(IOException ex){
            System.err.println(ex.getMessage());
        }
    }
}
