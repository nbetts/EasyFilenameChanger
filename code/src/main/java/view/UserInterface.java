package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import controller.FileController;
import model.File;

public class UserInterface extends Application {
  private boolean isInitiallyLoaded;
  private FileController fileController;
  private DirectoryChooser directoryChooser;
  private Stage stage;
  private Alert updateFilesAlert;
  private Alert invalidFilenameAlert;

  @FXML private Button chooseCurrentDirectoryButton;
  @FXML private CheckBox isUsingFileExtensionsCheckBox;
  @FXML private Label currentDirectoryLabel;
  @FXML private TableView<File> fileTable;
  @FXML private TableColumn<File, String> newNameColumn;

  public UserInterface() {
    isInitiallyLoaded = false;
    fileController = new FileController();
    directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Choose Directory");

    updateFilesAlert = new Alert(AlertType.CONFIRMATION);
    updateFilesAlert.setTitle("Confirm Filename Changes");
    updateFilesAlert.setHeaderText("Are you sure you want to update the files?");

    invalidFilenameAlert = new Alert(AlertType.WARNING);
    invalidFilenameAlert.setTitle("Invalid Filename");
  }

  @FXML private void initialize() {
    isUsingFileExtensionsCheckBox.setSelected(fileController.getIsUsingFileExtensions());
    newNameColumn.setOnEditCommit(col -> {
      File file = col.getRowValue();
      String newName = col.getNewValue();

      if (file.isFilenameValid(newName)) {
        file.setNewName(newName);
      } else {
        invalidFilenameAlert.setHeaderText("The filename \"" + newName +
                                           "\" contains characters that are not allowed.");
        invalidFilenameAlert.showAndWait();
        fileTable.refresh();
      }
    });
  }

  @FXML protected void toggleFileExtensions(ActionEvent event) {
    fileController.setIsUsingFileExtensions(isUsingFileExtensionsCheckBox.isSelected());
    updateTable();
  }

  @FXML protected void chooseCurrentDirectory(ActionEvent event) {
    String currentDirectory = fileController.getCurrentDirectory();
    directoryChooser.setInitialDirectory(new java.io.File(currentDirectory));
    java.io.File directory = directoryChooser.showDialog(stage);

    if (directory != null) {
      currentDirectoryLabel.setText("Loading...");
      fileController.setCurrentDirectory(directory.toString());
      currentDirectoryLabel.setText(fileController.getCurrentDirectory() +
                                    " (" + fileController.getFileCount() + " files)");
      updateTable();
      isInitiallyLoaded = true;
    }
  }

  @FXML protected void updateFiles(ActionEvent event) {
    if (isInitiallyLoaded) {
      if (updateFilesAlert.showAndWait().get() == ButtonType.OK) {
        fileController.save();
        fileController.load();
        updateTable();
      }
    }
  }

  private void updateTable() {
    fileTable.getItems().setAll(fileController.getFileList());
    fileTable.sort();

    int counter = 0;

    for (TableColumn<File, ?> column : fileTable.getColumns()) {
      addTooltipToColumnCells(column, counter == 2);
      counter++;
    }
  }

  // Adapted from https://stackoverflow.com/a/40677710.
  private <T> void addTooltipToColumnCells(TableColumn<File, T> column, boolean isNewNameColumn) {
    String newNameTooltip = "Double click to edit new name";

    Callback<TableColumn<File, T>, TableCell<File, T>> existingCellFactory = column.getCellFactory();

    column.setCellFactory(c -> {
      TableCell<File, T> cell = existingCellFactory.call(c);
      Tooltip tooltip = new Tooltip();

      if (isNewNameColumn) {
        tooltip.setText(newNameTooltip);
      } else {
        tooltip.textProperty().bind(cell.itemProperty().asString());
      }

      cell.setTooltip(tooltip);
      return cell;
    });
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UserInterface.fxml"));
    stage = primaryStage;
    stage.setTitle("Easy Filename Changer");
    stage.setScene(new Scene(root));
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}