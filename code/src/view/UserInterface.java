package view;

import controller.FileController;

public class UserInterface {
  private FileController fileController;

  public UserInterface() {

    fileController = new FileController();
    fileController.load();

    String[][] tableData = fileController.generateTableData();
    System.out.println(tableData[3][1]);
    tableData[3][2] = "test.txt";
    fileController.processTableData(tableData);
  }

  public static void main(String[] args) {
    UserInterface ui = new UserInterface();
  }
}