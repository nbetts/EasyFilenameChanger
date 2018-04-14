package controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;

import model.File;
import model.TableFile;

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

    try {
      Files.walk(Paths.get(currentDirectory))
        .filter(Files::isRegularFile)
        .forEach((Path path) -> fileList.add(new File(path)));
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

  public ArrayList<TableFile> generateTableData() {
    int currentDirectoryLength = configController.config.getCurrentDirectory().length();
    boolean isUsingFileExtensions = configController.config.getIsUsingFileExtensions();
    String parentDirectory, fileName;

    ArrayList<TableFile> tableFileList = new ArrayList<>();

    for (File file : fileList) {
      parentDirectory = file.getPath().toString();
      parentDirectory = parentDirectory.substring(currentDirectoryLength,
                                        parentDirectory.lastIndexOf(FILE_SEPARATOR));
      fileName = file.getName(isUsingFileExtensions);

      tableFileList.add(new TableFile(parentDirectory, fileName));
    }

    return tableFileList;
  }

  // public void processTableData(String[][] tableData) {
  //   boolean isUsingFileExtensions = configController.config.getIsUsingFileExtensions();

  //   for (int i = 0; i < tableData.length; i++) {
  //     String newName = tableData[i][2];

  //     if (!newName.isEmpty()) {
  //       File file = fileList.get(i);
  //       file.setNewName(isUsingFileExtensions, newName);
  //       fileList.set(i, file);
  //     }
  //   }
  // }
}