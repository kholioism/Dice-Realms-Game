package game.gui;

import game.engine.CLIGameController;

import java.util.Scanner;

public class MainCLI {
    public static void main(String[] args) {
        new Thread(() -> DiceRealms.launch(DiceRealms.class)).start();
        System.out.println("Dice Realms: Quest for the Elemental Crests!");
        Scanner sc = new Scanner(System.in);
        GUIGameController controller = new GUIGameController();
        controller.startGame("hi");
        sc.close();
    }
}
