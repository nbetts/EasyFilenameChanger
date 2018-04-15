package model;

import java.nio.file.Path;
import javafx.beans.property.SimpleStringProperty;

public class File {
  public static final char FILE_EXTENSION_SYMBOL = '.';

  private final Path path;
  private boolean isUsingFileExtensions;

  private final SimpleStringProperty parentDirectory;
  private final SimpleStringProperty fileName;
  private final SimpleStringProperty newName;

  public File(Path path, boolean isUsingFileExtensions, String parentDirectory) {
    this.path = path;
    this.isUsingFileExtensions = isUsingFileExtensions;

    this.parentDirectory = new SimpleStringProperty(parentDirectory);
    this.fileName = new SimpleStringProperty(path.getFileName().toString());
    this.newName = new SimpleStringProperty("");
  }

  public Path getPath() {
    return path;
  }

  public void setIsUsingFileExtensions(boolean isUsingFileExtensions) {
    this.isUsingFileExtensions = isUsingFileExtensions;
  }

  public String getParentDirectory() {
    return parentDirectory.get();
  }

  public String getFileName() {
    String name = fileName.get();

    return isUsingFileExtensions ? name : removeFileExtension(name);
  }

  public String getNewName() {
    return newName.get();
  }

  public void setNewName(String newName) {
    String name = newName;

    if (!isUsingFileExtensions) {
      name = removeFileExtension(name);
      name += getOriginalFileExtension();
    }

    this.newName.set(name);
  }

  private boolean isFilenameValid(String newName) {
    // Windows: [0-31 ascii] <>:"\/|?*
    // Unix: [0 ascii] /
    // if (System.getProperty("os.name").startsWith("Windows")) {

    // }
    return true;
  }

  private String getOriginalFileExtension() {
    String name = path.getFileName().toString();
    return name.substring(name.lastIndexOf(FILE_EXTENSION_SYMBOL));
  }

  private String removeFileExtension(String name) {
    int extensionIndex = name.lastIndexOf(FILE_EXTENSION_SYMBOL);

    if (extensionIndex > 0) {
      return name.substring(0, extensionIndex);
    }

    return name;
  }
}