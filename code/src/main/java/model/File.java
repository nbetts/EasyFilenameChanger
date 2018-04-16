package model;

import java.nio.file.Path;
import javafx.beans.property.SimpleStringProperty;

public class File {
  public static final char FILE_EXTENSION_SYMBOL = '.';
  public static final String WINDOWS_FILENAME_REGEX = "^[^\\x00-\\x1f\\x7f<>:\"\\/|?*]+$";
  public static final String UNIX_FILENAME_REGEX = "^[^\\x00/]+$";

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
    String name = newName.get();
    return isUsingFileExtensions ? name : removeFileExtension(name);
  }

  public String getNewNameWithExtension() {
    return newName.get();
  }

  public void setNewName(String newName) {
    String name = newName;

    if (!isUsingFileExtensions) {
      name = removeFileExtension(name);
      name += getOriginalFileExtension();
    }

    if (isFilenameValid(name)) {
      this.newName.set(name);
    }
  }

  public boolean isFilenameValid(String newName) {
    if (System.getProperty("os.name").startsWith("Windows")) {
      return newName.matches(WINDOWS_FILENAME_REGEX);
    } else {
      return newName.matches(UNIX_FILENAME_REGEX);
    }
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