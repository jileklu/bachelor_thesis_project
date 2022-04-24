package com.jileklu2.bakalarska_prace_app.handlers;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileHandlerTest {

    private static File outputFile01;

    static File inputFile01;
    private static JSONObject jsonTestObject01;

    @BeforeAll
    public static void setUp() {
        outputFile01 = new File("src/test/java/com/jileklu2/bakalarska_prace_app/resources/out_01.json");
        inputFile01 = new File("src/test/java/com/jileklu2/bakalarska_prace_app/resources/in_01.json");
        jsonTestObject01 = new JSONObject("{\"test\":\"test\"}");
    }

    @Test
    public void writingJsonFile(){
        FileHandler.createJsonFile(outputFile01.getPath(), jsonTestObject01);
        StringBuilder data = new StringBuilder();
        JSONObject json = null;
        try {
            Scanner reader = new Scanner(outputFile01);

            while (reader.hasNextLine()) {
                data.append(reader.nextLine());
            }
            reader.close();
            json = new JSONObject(data.toString());
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assertions.assertNotNull(json);
        Assertions.assertEquals(json.toString(),jsonTestObject01.toString());
    }

    @Test void readingJsonFile(){
        try {
            JSONObject object = FileHandler.readJsonFile(inputFile01.getPath());
            Assertions.assertEquals(object.toString(),jsonTestObject01.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }
}
