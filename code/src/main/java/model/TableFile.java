package model;

import javafx.beans.property.SimpleStringProperty;

public class TableFile {
  private final SimpleStringProperty parentDirectory;
  private final SimpleStringProperty fileName;
  private final SimpleStringProperty newName;

  public TableFile(String parentDirectory, String fileName) {
    this.parentDirectory = new SimpleStringProperty(parentDirectory);
    this.fileName = new SimpleStringProperty(fileName);
    this.newName = new SimpleStringProperty("");
  }

  public String getParentDirectory() {
    return parentDirectory.get();
  }

  public void setParentDirectory(String parentDirectory) {
    this.parentDirectory.set(parentDirectory);
  }

  public String getFileName() {
    return fileName.get();
  }

  public void setFileName(String fileName) {
    this.fileName.set(fileName);
  }

  public String getNewName() {
    return newName.get();
  }

  public void setNewName(String newName) {
    this.newName.set(newName);
  }
}