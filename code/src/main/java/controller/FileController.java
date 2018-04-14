package controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;

import model.File;

public class FileController {
  public final static String FILE_SEPARATOR = System.getProperty("file.separator");

  private ConfigController configController;
  private ArrayList<File> fileList;

  public FileController() {
    configController = new ConfigController();
    fileList = new ArrayList<>();
  }

  public void load() {
    configController.load();

    String currentDirectory = configController.config.getCurrentDirectory();
    int currentDirectoryLength = currentDirectory.length();
    boolean isUsingFileExtensions = configController.config.getIsUsingFileExtensions();

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
    configController.save();

    for (File file : fileList) {
      String newName = file.getNewName();
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

  public ArrayList<File> getFileList() {
    return fileList;
  }
}