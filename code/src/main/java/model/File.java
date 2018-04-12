package model;

import java.nio.file.Path;

public class File {
  public static final char FILE_EXTENSION_SYMBOL = '.';

  private Path path;
  private String newName;

  public File(Path path) {
    this.path = path;
    this.newName = "";
  }

  public Path getPath() {
    return path;
  }

  public void setPath(Path path) {
    this.path = path;
  }

  public String getName(boolean isUsingFileExtensions) {
    String name = path.getFileName().toString();

    if (!isUsingFileExtensions) {
      name = removeFileExtension(name);
    }

    return name;
  }

  public String getNewName() {
    return newName;
  }

  public void setNewName(boolean isUsingFileExtensions, String newName) {
    String name = newName;

    if (!isUsingFileExtensions) {
      name = removeFileExtension(name);
      name += getOriginalFileExtension();
    }

    this.newName = name;
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