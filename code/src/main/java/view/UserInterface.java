package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import controller.FileController;
import model.File;

public class UserInterface extends Application {
  private FileController fileController;

  @FXML private CheckBox isUsingFileExtensionsCheckBox;
  @FXML private Label currentDirectory;
  @FXML private TableView<File> tableView;

  public UserInterface() {
    fileController = new FileController();
    fileController.load();
  }

  @FXML private void initialize() {
    isUsingFileExtensionsCheckBox.setSelected(fileController.getIsUsingFileExtensions());
    currentDirectory.setText(fileController.getCurrentDirectory());
    updateTable();
  }

  @FXML protected void toggleFileExtensions(ActionEvent event) {
    fileController.setIsUsingFileExtensions(isUsingFileExtensionsCheckBox.isSelected());
    updateTable();
  }

  private void updateTable() {
    tableView.getItems().setAll(fileController.getFileList());
    tableView.sort();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UserInterface.fxml"));
    primaryStage.setTitle("EasyFilenameChanger");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}