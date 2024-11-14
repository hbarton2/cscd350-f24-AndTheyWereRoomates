# UML Editor

v3.3.55
* How to read version # Version main branch.demo release.pull request changes'
* What's New:
  * NEW CLI preview(alpha) 
* Major changes:
  * MAJOR REFRACT HAS BEGUN
---

## Table of Contents

1. [Getting Started for User](#getting-started-for-User)
2. [Running the Application for User](#running-the-application-for-User)
3. [Overview](#overview)
4. [Features](#features)
5. [Installation for Developers](#installation-for-Developers)
6. [Usage](#usage)
7. [Testing](#testing)
8. [Contributing](#contributing)
9. [License](#license)
10. [Contact](#contact)
11. [Project Details](#Project-Details)
---
## Getting Started for User

### Prerequisites

Ensure that the following software is installed on the target machine:

- **Java Runtime Environment (JRE)** or **Java Development Kit (JDK)** version 17 or higher.  
  You can download it from the [Oracle Java Downloads page](https://www.oracle.com/java/technologies/javase-downloads.html) or from [AdoptOpenJDK](https://adoptopenjdk.net/).
- **JavaFX** version 21.
  You can download it from the [JavaFX Download page](https://gluonhq.com/products/javafx/).
  ![JavaFX Download page](doc/images/JavaFX.png)
---

## Running the Application for User

### Steps to Run the UML Editor

### 1. Obtain the Program File
Download the package https://github.com/hbarton2/cscd350-f24-AndTheyWereRoomates.git

### Make sure the main branch is selected
![Main Branch](doc/images/mainbranch.png)
### Click on the green code button and click on Download Zip.
![Code Button](doc/images/codebutton.png)
### Navigate to where the package was downloaded and extract the files
### Steps to Open Terminal and Run `umleditor.jar`


2. **Locate `uml-editor.jar`**:
    - Navigate to the directory where `uml-editor.jar` is located using your file explorer.

3. **Open Terminal/Command Prompt**:
    - **On Windows**:
        - Right-click inside the folder (not on the file) and select `Open in Terminal` or `Open PowerShell window here`.
    - **On macOS**:
        - Open Terminal, then type `cd` followed by a space.
        - Drag and drop the folder containing `uml-editor.jar` into the Terminal window.
        - Press `Enter` to navigate to the folder.
    - **On Linux**:
        - Open Terminal, then type `cd` followed by a space.
        - Drag and drop the folder containing `uml-editor.jar` into the Terminal window.
        - Press `Enter` to navigate to the folder.

4. **Run the JAR File**:
    - Type the following command in the terminal:
      ```bash
      java -jar uml-editor.jar
      ```
    - Press `Enter` to execute the command.

*Did you know: If this file is currently viewed in a dedicated markdown viewer, you can click the arrow icon next to the bash command and launch the application.*


---

## Overview

DevKitUML is a unified modeling language (UML) software development kit (SDK) designed to simplify the process of generating, managing, and interacting with UML diagrams for developers. This project integrates a console-based interface for UML Editor functionality.

This SDK is intended to streamline the experience of creating class diagrams, modifying them, and saving/loading project data using JSON format for persistence.

---

## Features

- **Command-Line Interface (CLI):** Provides a terminal-like environment where users can execute commands within the JavaFX terminal window.
- **Graphical User Interface (GUI):** Application has an elegant, easy-to-use graphical interface for an enhanced user experience.
- **JSON Integration:** Uses Gson for saving/loading the project data in JSON format.
- **Multi-platform Support:** Designed to work on Windows, macOS, and Linux with cross-platform capabilities.

---

## Installation for Developers

### Prerequisites

Before running the project, ensure you have the following installed:

- **Java JDK 17**
- **Maven 3.9.9**

### Dependencies

The project uses the following dependencies, which are defined in the `pom.xml` file:


See the `pom.xml` file for full details on dependencies.

### Building the Project

You can build the project using Maven. From the project root directory(where `pom.xml` is located)
run:

```bash
mvn clean package
mvn install
```

### Step-by-Step Instructions

```angular2html
src/main/java/uml-editor/EntryPoint.java
```

1. Clone the repository:
   ```bash
   git clone https://github.com/hbarton2/cscd350-f24-AndTheyWereRoomates
   cd cscd350-f24-AndTheyWereRoomates
   ```
   
2. Build the project using Maven:
   ```bash
   mvn clean package
   mvn install
    ```   

3. Run the project:
   ```bash
   java -jar target/original-uml-editor-shaded.jar
   ``` 
---

## Usage
### Console Interaction 
The application includes an in-app terminal/console feature, allowing users to input commands within the JavaFX interface. Users can enter specific commands for various operations such as creating, renaming, and removing classes, attributes, and methods.

- Help Menu: Users can access the help menu for detailed guidance.
- Command-Line Operations: Integration of the command-line terminal where you can execute operations directly without leaving the graphical interface.

### Graphic User Interface Interaction

placeholder under construction

### JSON Integration
The project uses Gson for storing and loading UML project data as JSON files. The data includes class names, attributes, and methods, allowing for an easily shareable format.

### Unit Testing
JUnit is used for unit testing throughout the project. To run the tests, use:

```bash
mvn test
```
---

## Testing
To run unit tests, execute the following command:
```bash
mvn test
```
Testing is performed using JUnit 5 and includes:
- Unit tests for core functionalities.
- Integration tests for external API calls.

You can find tests under the src/test directory.

---

## Contributing

Currently, no outside contribution are allowed.

---

## License
Please see LICENSE.md

---

## Contact

For any inquiries, feel free to reach out