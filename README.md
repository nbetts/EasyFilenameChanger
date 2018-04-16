# Easy Filename Changer
Easily change the names of files in a directory.

![See /resources/screenshot.jpg for an example of the program running.](resources/screenshot.jpg "Screenshot example")

## Using the program
Double click `EasyFilenameChanger.jar` to run the program.

Start by clicking **Choose Directory ...** to choose a directory to load files from (files within subdirectories are also included).

You can immediately begin adding new file names by double-clicking the table cells in the **New Name** column and entering text.

Once you're ready to save the new file names, click **Update Files** and confirm.

## Building the program
If you wish to modify the source code and build the program yourself, navigate to `code` and run one of the following commands:

| Command                     | Description                    |
|-----------------------------|--------------------------------|
| `./gradlew build`           | Build the program.             |
| `./gradlew run`             | Run the program.               |
| `./gradlew buildStandalone` | Build a standalone `jar` file. |

For Windows users, replace `./gradlew` with `./gradlew.bat`.
