package org.project;

import org.project.View.MainMenu;

public class Main {
  //
  //  @Override
  //  public void start(Stage primaryStage) throws Exception {
  //    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
  //    Scene scene = new Scene(loader.load(), 1280, 720);
  //
  //    primaryStage.setTitle("Unified Modeling Language Editor");
  //    primaryStage.setScene(scene);
  //    primaryStage.show();
  //  }
  //
  //  public static void main(String[] args) throws IOException {
  //    launch();
  //    boolean answer = true;
  //    Scanner scanner = new Scanner(System.in);
  //
  //    while (answer) {
  //      System.out.println("Welcome to the Unified Modeling Language Editor.");
  //      System.out.println("Please choose an interface to start:");
  //      System.out.println("   - Graphical User Interface    (GUI): Type 'gui'     or 'g'");
  //      System.out.println("   - Command Line Interface      (CLI): Type 'cli'     or 'c'");
  //      //      System.out.println("OLD- Command Line Interface      (CLI): Type 'cli'     or
  // 'c'");
  //      //      System.out.println("NEW- NEW Command Line Interface  (CLI): Type 'new cli' or
  // 'n'");
  //      System.out.println("   - Terminate launcher:                Type 'exit'    or 'quit'");
  //      System.out.print("Your choice: ");
  //      String input = scanner.nextLine().toLowerCase();
  //
  //      switch (input) {
  //        case "gui", "g" -> {
  //          answer = false;
  //          GraphicalUserInterface.main(args);
  //        }
  //          //        case "cli", "c" -> {
  //          //          answer = false;
  //          //          new CLIView().runMenu();
  //          //        }
  //          //        case "new cli", "n" -> {
  //        case "cli", "c" -> {
  //          answer = false;
  //          CommandRegistries commands = new
  // CommandRegistries("src/main/resources/CLICommands.json");
  //          CommandLineTerminal commandLineTerminal = new CommandLineTerminal(commands);
  //          commandLineTerminal.launch();
  //        }
  //        case "exit", "quit" -> answer = false;
  //        default -> System.out.println("Please enter a valid option.");
  //      }
  //    }
  //
  //    scanner.close();
  //    System.exit(0);
  //  }

  public static void main(String[] args) {
    MainMenu.launchApplication();
  }
}
