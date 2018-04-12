package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import controller.FileController;

public class UserInterface extends Application {
  private FileController fileController;

  @Override
  public void start(Stage primaryStage) throws Exception {
    fileController = new FileController();
    fileController.load();

    // Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UserInterface.fxml"));
    // primaryStage.setTitle("EasyFilenameChanger");
    // primaryStage.setScene(new Scene(root, 800, 500));
    // primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}