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
      System.out.print("Do you want to use GUI UML editor? (yes/y or no/n): ");
      String input = scanner.nextLine().toLowerCase();

      if (input.equals("yes") || input.equals("y")) {
        answer = false;
        GUIView.main(args);
      } else if (input.equals("no") || input.equals("n")) {
        answer = false;
        new CLIView().runMenu();
      } else {
        System.out.println("Please enter a valid command.");
      }
    }

    scanner.close();
  }
}
