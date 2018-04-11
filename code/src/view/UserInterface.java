package view;

import controller.FileController;
import model.SystemFile;

public class UserInterface {
  private FileController fileController;

  public UserInterface() {
    fileController = new FileController();
    fileController.load();

    String[][] tableData = fileController.generateTableData();

    for (int i = 0; i < tableData.length; i++) {
      System.out.format("%s     %s\n", tableData[i][0], tableData[i][1]);
    }
  }

  public static void main(String[] args) {
    UserInterface ui = new UserInterface();
  }
}