package org.project;

import org.project.View.CLIView;
import org.project.View.GUIView;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)  {

        if(args.length == 0 || args[0].equals("--gui")) {
            GUIView.main(args);
        }
        else if (args[0].equals("--cli")) {
            new CLIView().runMenu();
        }
        else {
            System.out.println("Invalid command");
        }
    }
}
