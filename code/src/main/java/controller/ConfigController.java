package controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigController {
  public final static String CONFIG_FILE_NAME = "config.txt";
  public final static String CONFIG_FILE_DELIMITER = ",";
  public final static String DEFAULT_CURRENT_DIRECTORY = System.getProperty("user.home");

  private Path configPath;
  private boolean isUsingFileExtensions;
  private String currentDirectory;

  public ConfigController() {
    try {
      configPath = Paths.get(this.getClass().getClassLoader().getResource(CONFIG_FILE_NAME).toURI());
    } catch (Exception e) {
      configPath = null;
    }
  }

  public boolean getIsUsingFileExtensions() {
    return isUsingFileExtensions;
  }

  public void setIsUsingFileExtensions(boolean isUsingFileExtensions) {
    this.isUsingFileExtensions = isUsingFileExtensions;
  }

  public String getCurrentDirectory() {
    return currentDirectory;
  }

  public void setCurrentDirectory(String currentDirectory) {
    Path directory = Paths.get(currentDirectory);

    if (Files.exists(directory) && Files.isDirectory(directory)) {
      this.currentDirectory = directory.toString();
    } else {
      this.currentDirectory = DEFAULT_CURRENT_DIRECTORY;
    }
  }

  public void load() {
    try {
      String line = Files.lines(configPath).findFirst().get();
      String[] configProperties = line.split(CONFIG_FILE_DELIMITER);

      isUsingFileExtensions = Boolean.parseBoolean(configProperties[0]);
      setCurrentDirectory(configProperties[1]);
    } catch (Exception e) {
      isUsingFileExtensions = false;
      currentDirectory = DEFAULT_CURRENT_DIRECTORY;
    }
  }

  public void save() {
    try {
      String configProperties = isUsingFileExtensions + CONFIG_FILE_DELIMITER + currentDirectory;
      Files.write(configPath, configProperties.getBytes());
    } catch (Exception e) {}
  }
}