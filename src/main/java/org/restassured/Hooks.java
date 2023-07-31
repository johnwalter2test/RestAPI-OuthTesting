package org.restassured;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.restassured.javabeans.CustomData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Hooks extends ProjectBase {

    public static PrintStream printStream;

    @Before
    public void sessionIn() {
    }

    @After
    public void sessionOut() {
    }

    @Before
    public static void loadProperties() {
        try {
            properties = new Properties();
            properties.load(Files.newInputStream(Paths.get("./src/main/resources/config.properties")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
