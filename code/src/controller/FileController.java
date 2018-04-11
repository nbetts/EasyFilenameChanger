package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import model.SystemFile;

public class FileController {
  public final static String FILE_SEPARATOR = System.getProperty("file.separator");

  private ArrayList<SystemFile> fileList;

  public FileController() {
    fileList = new ArrayList<>();
  }

  public void load(String directory) {
    loadFiles(directory);
    Collections.sort(fileList);
  }

  public void save() {
    //
  }

  private void loadFiles(String directory) {
    if (!directory.substring(directory.length() - 1).equals(FILE_SEPARATOR)) {
      directory += FILE_SEPARATOR;
    }

    File[] files = new File(directory).listFiles();

    for (int i = 0; i < files.length; i++) {
      if (files[i].isFile()) {
        fileList.add(new SystemFile(directory, files[i]));
      } else if (files[i].isDirectory()) {
        loadFiles(directory + files[i].getName() + FILE_SEPARATOR);
      }
    }
  }

  // public String[][] generateTableData() {
  //   String[][] tableData = new String[fileList.size()][3];
  //   boolean isUsingFileExtensions = config.getIsUsingFileExtensions();

  //   for (int i = 0; i < tableData.length; i++) {
  //     SystemFile file = fileList.get(i);
  //     String subDirectory = file.getSubDirectory();
  //     subDirectory = subDirectory.substring(0, subDirectory.length() - 1);

  //     tableData[i][0] = subDirectory.substring(subDirectory.lastIndexOf(FILE_SEPARATOR) + 1);
  //     tableData[i][1] = file.getName(isUsingFileExtensions);
  //     tableData[i][2] = "";
  //   }

  //   return tableData;
  // }

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