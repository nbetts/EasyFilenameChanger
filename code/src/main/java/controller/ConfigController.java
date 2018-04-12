package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import model.Config;

public class ConfigController {
  public final static String CONFIG_FILE_PATH = "config.txt";
  public final static String CONFIG_DELIMITER = ",";

  public Config config;

  public ConfigController() {
    config = new Config();
  }

  public void load() {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE_PATH));
      String[] configProperties = reader.readLine().split(CONFIG_DELIMITER);
      reader.close();

      config.setIsUsingFileExtensions(Boolean.parseBoolean(configProperties[0]));

      File directory = new File(configProperties[1]);

      if (directory.exists() && directory.isDirectory()) {
        config.setCurrentDirectory(configProperties[1]);
      } else {
        throw new Exception("Cannot find " + configProperties[1]);
      }
    } catch (FileNotFoundException e) {
      System.err.println("Error: cannot find " + CONFIG_FILE_PATH);
      System.exit(-1);
    } catch (IOException e) {
      System.err.println("Error: cannot read " + CONFIG_FILE_PATH);
      System.exit(-1);
    } catch (Exception e) {
      System.err.println("Error: cannot parse " + CONFIG_FILE_PATH + ", " + e.getMessage());
      System.exit(-1);
    }
  }

  public void save() {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE_PATH));
      writer.write(config.getIsUsingFileExtensions() + CONFIG_DELIMITER + config.getCurrentDirectory());
      writer.close();
    } catch (FileNotFoundException e) {
      System.err.println("Error: cannot find " + CONFIG_FILE_PATH);
    } catch (IOException e) {
      System.err.println("Error: cannot write " + CONFIG_FILE_PATH);
    }
  }
}