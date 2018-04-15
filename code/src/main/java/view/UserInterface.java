package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import controller.FileController;
import model.File;

public class UserInterface extends Application {
  private FileController fileController;
  private DirectoryChooser directoryChooser;
  private Stage stage;

  @FXML private Button changeCurrentDirectoryButton;
  @FXML private CheckBox isUsingFileExtensionsCheckBox;
  @FXML private Label currentDirectory;
  @FXML private TableView<File> tableView;

  public UserInterface() {
    fileController = new FileController();
    fileController.load();

    directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Change Directory");
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

  @FXML protected void changeCurrentDirectory(ActionEvent event) {
    directoryChooser.setInitialDirectory(new java.io.File(fileController.getCurrentDirectory()));
    java.io.File directory = directoryChooser.showDialog(stage);

    if (directory != null) {
      fileController.setCurrentDirectory(directory.toString());
      updateTable();
    }
  }

  private void updateTable() {
    tableView.getItems().setAll(fileController.getFileList());
    tableView.sort();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UserInterface.fxml"));
    stage = primaryStage;
    stage.setTitle("EasyFilenameChanger");
    stage.setScene(new Scene(root));
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}