package view;

import controller.FileController;

public class UserInterface {
  private FileController fileController;

  public UserInterface() {
    fileController = new FileController();
    fileController.load();
  }

  public static void main(String[] args) {
    UserInterface ui = new UserInterface();
  }
}