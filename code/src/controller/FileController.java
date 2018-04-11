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
    configController.load();

    fileList = new ArrayList<>();
  }

  public void load() {
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
    //
  }

  public String[][] generateTableData() {
    String[][] tableData = new String[fileList.size()][3];

    int currentDirectoryLength = configController.config.getCurrentDirectory().length();
    boolean isUsingFileExtensions = configController.config.getIsUsingFileExtensions();

    for (int i = 0; i < tableData.length; i++) {
      File file = fileList.get(i);
      String parentDirectory = file.getPath().toString();
      parentDirectory = parentDirectory.substring(currentDirectoryLength,
                                        parentDirectory.lastIndexOf(FILE_SEPARATOR));

      tableData[i][0] = parentDirectory;
      tableData[i][1] = file.getName(isUsingFileExtensions);
      tableData[i][2] = "";
    }

    return tableData;
  }

  // public void addNewFilenames(String[][] tableData) {
  //   for (int i = 0; i < tableData.length; i++) {
  //     String newName = tableData[i][2];

  //     if (!newName.isEmpty()) {
  //       SystemFile file = fileList.get(i);
  //       file.setNewName(newName);
  //       fileList.set(i, file);
  //     }
  //   }
  // }
}