package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import model.SystemFile;

public class FileController {
  public final static String FILE_SEPARATOR = System.getProperty("file.separator");
  public final static String CONFIG_FILE_PATH = "config.txt";
  public final static String CONFIG_DELIMITER = ",";

  private ArrayList<SystemFile> fileList;
  private int idCounter;
  private boolean isUsingFileExtensions;
  private String currentDirectory;

  public FileController() {
    fileList = new ArrayList<>();
    idCounter = 0;
    isUsingFileExtensions = false;
    currentDirectory = System.getProperty("user.home");

    loadConfig();
  }

  public void toggleFileExtensions() {
    isUsingFileExtensions = !isUsingFileExtensions;
  }

  public void load() {
    loadFiles(currentDirectory);
  }

  private void loadFiles(String directory) {
    if (!directory.substring(directory.length() - 1).equals(FILE_SEPARATOR)) {
      directory += FILE_SEPARATOR;
    }

    File[] files = new File(directory).listFiles();

    for (int i = 0; i < files.length; i++) {
      if (files[i].isFile()) {
        fileList.add(new SystemFile(idCounter++, files[i]));
      } else if (files[i].isDirectory()) {
        loadFiles(directory + files[i].getName() + FILE_SEPARATOR);
      }
    }
  }

  public String[][] generateTableData() {
    String[][] tableData = new String[fileList.size()][3];

    for (int i = 0; i < tableData.length; i++) {
      SystemFile file = fileList.get(i);
      tableData[i][0] = Integer.toString(file.getId());
      tableData[i][1] = file.getName(isUsingFileExtensions);
      tableData[i][2] = "";
    }

    return tableData;
  }

  public void sort() {
    Collections.sort(fileList);
  }

  private void loadConfig() {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE_PATH));
      String[] config = reader.readLine().split(CONFIG_DELIMITER);
      reader.close();

      isUsingFileExtensions = Boolean.parseBoolean(config[0]);
      File directory = new File(config[1]);

      if (directory.exists() && directory.isDirectory()) {
        currentDirectory = config[1];
      } else {
        throw new Exception("Cannot find " + config[1]);
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

  public void saveConfig() {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE_PATH));
      writer.write(isUsingFileExtensions + CONFIG_DELIMITER + currentDirectory);
      writer.close();
    } catch (FileNotFoundException e) {
      System.err.println("Error: cannot find " + CONFIG_FILE_PATH);
    } catch (IOException e) {
      System.err.println("Error: cannot write " + CONFIG_FILE_PATH);
    }
  }
}