package view;

import controller.ConfigController;
import controller.FileController;
// import model.SystemFile;

public class UserInterface {
  private ConfigController configController;
  private FileController fileController;

  public UserInterface() {
    configController = new ConfigController();
    configController.load();

    fileController = new FileController();
    fileController.load(configController.config.getCurrentDirectory());

    // String[][] tableData = fileController.generateTableData();

    // for (int i = 0; i < tableData.length; i++) {
    //   System.out.format("%s     %s\n", tableData[i][0], tableData[i][1]);
    // }
  }

  public static void main(String[] args) {
    UserInterface ui = new UserInterface();
  }
}