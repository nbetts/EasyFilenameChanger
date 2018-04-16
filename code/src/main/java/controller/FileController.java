package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import model.File;

public class FileController {
  public final static String FILE_SEPARATOR = System.getProperty("file.separator");

  private ConfigController configController;
  private ArrayList<File> fileList;

  public FileController() {
    configController = new ConfigController();
    configController.load();
    fileList = new ArrayList<>();
  }

  public ArrayList<File> getFileList() {
    return fileList;
  }

  public int getFileCount() {
    return fileList.size();
  }

  public boolean getIsUsingFileExtensions() {
    return configController.getIsUsingFileExtensions();
  }

  public void setIsUsingFileExtensions(boolean isUsingFileExtensions) {
    configController.setIsUsingFileExtensions(isUsingFileExtensions);
    configController.save();

    for (File file : fileList) {
      file.setIsUsingFileExtensions(isUsingFileExtensions);
    }
  }

  public String getCurrentDirectory() {
    return configController.getCurrentDirectory();
  }

  public void setCurrentDirectory(String directory) {
    configController.setCurrentDirectory(directory);
    configController.save();
    load();
  }

  public void load() {
    fileList.clear();

    boolean isUsingFileExtensions = configController.getIsUsingFileExtensions();
    String currentDirectory = configController.getCurrentDirectory();
    int currentDirectoryLength = currentDirectory.length();

    try {
      Files.walk(Paths.get(currentDirectory))
        .filter(Files::isRegularFile)
        .forEach((Path path) -> {
          String parentDirectory = path.toString();
          parentDirectory = parentDirectory.substring(currentDirectoryLength,
                            parentDirectory.lastIndexOf(FILE_SEPARATOR));

          fileList.add(new File(path, isUsingFileExtensions, parentDirectory));
        });
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  public void save() {
    for (File file : fileList) {
      String newName = file.getNewNameWithExtension();
      Path path = file.getPath();

      if (!newName.isEmpty()) {
        try {
          Files.move(path, path.resolveSibling(newName));
        } catch (IOException e) {
          System.err.println(e.getMessage());
        }
      }
    }
  }
}