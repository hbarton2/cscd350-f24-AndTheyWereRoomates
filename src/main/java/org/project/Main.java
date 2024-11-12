package org.project;

import java.util.Scanner;
import org.project.View.CLIView;
import org.project.View.GUIView;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

  public static void main(String[] args) {

    boolean answer = true;
    Scanner scanner = new Scanner(System.in);

    while (answer) {
      System.out.println("Welcome to the Unified Modeling Language Editor.");
      System.out.println("Please choose an interface to start:");
      System.out.println("  - Graphical User Interface (GUI): Type 'gui' or 'g'");
      System.out.println("  - Command Line Interface (CLI): Type 'cli' or 'c'");
      System.out.print("Your choice: ");
      String input = scanner.nextLine().toLowerCase();

      if (input.equals("gui") || input.equals("g")) {
        answer = false;
        GUIView.main(args);
      } else if (input.equals("cli") || input.equals("c")) {
        answer = false;
        new CLIView().runMenu();
      } else {
        System.out.println("Please enter a valid option.");
      }
    }

    scanner.close();
  }

}
