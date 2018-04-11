package model;

public class Config {
  private String currentDirectory;
  private boolean isUsingFileExtensions;

  public Config() {
    currentDirectory = System.getProperty("user.home");
    isUsingFileExtensions = false;
  }

  public String getCurrentDirectory() {
    return currentDirectory;
  }

  public void setCurrentDirectory(String currentDirectory) {
    this.currentDirectory = currentDirectory;
  }

  public boolean getIsUsingFileExtensions() {
    return isUsingFileExtensions;
  }

  public void setIsUsingFileExtensions(boolean isUsingFileExtensions) {
    this.isUsingFileExtensions = isUsingFileExtensions;
  }
}