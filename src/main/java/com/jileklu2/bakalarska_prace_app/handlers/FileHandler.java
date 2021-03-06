package com.jileklu2.bakalarska_prace_app.handlers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 */
public class FileHandler {
    /**
     *
     * @param filePath
     * @param content
     */
    public static void createJsonFile(String filePath, JSONObject content){
        try (
            PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            out.write(content.toString(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param filePath
     * @param content
     */
    public static void createGpxFile(String filePath, String content) {
        try (
            PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            out.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param filePath
     * @return
     * @throws FileNotFoundException
     * @throws JSONException
     */
    public static JSONObject readJsonFile(String filePath) throws FileNotFoundException, JSONException {
        StringBuilder data = new StringBuilder();
        JSONObject json = null;

        File file = new File(filePath);
        Scanner reader = new Scanner(file);

        while (reader.hasNextLine()) {
            data.append(reader.nextLine());
        }
        reader.close();
        json = new JSONObject(data.toString());
        
        return json;
    }
}
