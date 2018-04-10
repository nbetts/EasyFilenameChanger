package model;

import java.io.File;

public class SystemFile implements Comparable<SystemFile> {
  private final int id;
  private File file;
  private String newName;

  public SystemFile(int id, File file) {
    this.id = id;
    this.file = file;
  }

  public int getId() {
    return id;
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

  @Override
  public int compareTo(SystemFile otherFile) {
    return getName(true).toUpperCase().compareTo(otherFile.getName(true).toUpperCase());
  }
}