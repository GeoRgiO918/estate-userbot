package org.userbot;


import org.userbot.utils.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DataSaver {

    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    public final String pathString;

    public DataSaver(String pathString){
        this.pathString = pathString;
    }

    public  synchronized boolean tryToAddNewData(String text){
        text = StringUtils.trimString(text);

        String formattedLine = "\"" + text + "\",0";

        Path path = Path.of(pathString);

        if(!Files.exists(path)){
            try {
                Files.createFile(path);
            }
            catch (Exception e){
                System.out.println("Error while trying to create saved_data file: " + e.getLocalizedMessage());
                return false;
            }
        };

        if(!contains(formattedLine)) {
            try {
                appendLine(pathString, formattedLine);
            } catch (Exception e) {
                System.out.println("Error while trying to add new line to the file: " + e.getLocalizedMessage());
            }
            return true;
        }else{
            return false;
        }
    }

    private  synchronized boolean contains(String text){
        Path path = Path.of(pathString);
        if(Files.exists(path)) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(pathString));
                String line;
                while ((line = reader.readLine()) != null) {
                    if(line.equals(text)){
                        return true;
                    }
                }
                reader.close();
                return false;
            }
            catch (Exception e){
                System.out.println("Error while trying to check file contains: " + e.getLocalizedMessage());
                return  true;
            }
        }else{
            return true;
        }
    }

    public static void appendLine(String filePath, String lineToAdd) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(lineToAdd);
            writer.newLine(); // добавляет символ перевода строки
        }
    }
}
