package model;

import java.nio.file.Path;

public class File {
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
      int extensionIndex = name.lastIndexOf(".");

      if (extensionIndex > 0) {
        name = name.substring(0, extensionIndex);
      }
    }

    return name;
  }

  public String getNewName() {
    return newName;
  }

  public void setNewName(String newName) {
    this.newName = newName;
  }
}