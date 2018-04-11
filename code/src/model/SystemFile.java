package model;

import java.io.File;

public class SystemFile implements Comparable<SystemFile> {
  private final String subDirectory;
  private File file;
  private String newName;

  public SystemFile(String subDirectory, File file) {
    this.subDirectory = subDirectory;
    this.file = file;
    this.newName = "";
  }

  public String getName(boolean isUsingFileExtensions) {
    String name = file.getName();

    if (!isUsingFileExtensions) {
      int extensionIndex = name.lastIndexOf(".");

      if (extensionIndex > 0) {
        name = name.substring(0, extensionIndex);
      }
    }

    return name;
  }

  public String getSubDirectory() {
    return subDirectory;
  }

  public void setNewName(String newName) {
    this.newName = newName;
  }

  @Override
  public int compareTo(SystemFile otherFile) {
    return getName(true).toUpperCase().compareTo(otherFile.getName(true).toUpperCase());
  }
}