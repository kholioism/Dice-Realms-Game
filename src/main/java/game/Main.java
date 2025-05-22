package game;

import java.util.Scanner;

import game.engine.*;

public class Main {    
    public static void main(String[] args) {
        System.out.println("Dice Realms: Quest for the Elemental Crests!");
        Scanner sc = new Scanner(System.in);
        CLIGameController controller = new CLIGameController();
        controller.startGame();
        sc.close();
    }
}
