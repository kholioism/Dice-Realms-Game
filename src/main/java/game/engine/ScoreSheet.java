package game.engine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import game.collectibles.ArcaneBoost;
import game.collectibles.Bonus;
import game.collectibles.ElementalCrest;
import game.collectibles.Reward;
import game.collectibles.TimeWarp;
import game.creatures.*;
import game.dice.Dice;

public class ScoreSheet {

    private Dragon dragon = new Dragon();
    private Guardians guardians = new Guardians();
    private Hydra hydra = new Hydra();
    private Phoenix phoenix = new Phoenix();
    private Lion lion = new Lion();

    // final int[] guardians = {0,1,2,4,7,11,16,22,29,37,46,56};
    // boolean dragon1;
    // boolean dragon2;
    // boolean dragon3;
    // boolean dragon4;
    // final int[] hydra = {0,1,3,6,10,15,21,28,36,45,55,66};
    // int[] phoenix = new int[11];
    // int[] lion = new int[11];

    // int gScore = 0;
    // int bScore = 0;
    // int mScore = 0;
    // int yScore = 0;
    // TODO: make sure everyone has this implementation

    private TimeWarp[] timeWarp = new TimeWarp[7];
    private ArcaneBoost[] arcaneBoost = new ArcaneBoost[7];
    private int ElementalCrest;
    private ArrayList<Move> moveHistory = new ArrayList<Move>();
    private Reward[] turnRewards;
    String bonus[] = new String[6];
    String[] defString = { "TW", "AB", "TW", "ES", "  ", "  " };
    Reward[] def = { new TimeWarp(), new ArcaneBoost(), new TimeWarp(), new Bonus(Realm.WHITE), null, null };

    public ScoreSheet() {
        for (int i = 0; i < arcaneBoost.length; i++) {
            arcaneBoost[i] = new ArcaneBoost();
        }
        for (int i = 0; i < timeWarp.length; i++) {
            timeWarp[i] = new TimeWarp();
        }
        turnRewards = new Reward[6];
        String[] names = { "round1Reward", "round2Reward", "round3Reward", "round4Reward", "round5Reward",
                "round6Reward" };
        try {
            FileReader f = new FileReader("src/main/resources/config/RoundsRewards.properties");
            Properties p = new Properties();
            p.load(f);
            String s = "";
            for (int i = 0; i < names.length; i++) {
                s = p.getProperty(names[i]);
                if (s != null) {
                    switch (s) {
                        case "ArcaneBoost":
                            turnRewards[i] = new ArcaneBoost();
                            bonus[i] = "AB";
                            break;
                        case "TimeWarp":
                            turnRewards[i] = new TimeWarp();
                            bonus[i] = "TW";
                            break;
                        case "ElementalCrest":
                            turnRewards[i] = new ElementalCrest();
                            bonus[i] = "EC";
                            break;
                        case "EssenceBonus":
                            turnRewards[i] = new Bonus(Realm.WHITE);
                            bonus[i] = "ES";
                            break;
                        case "RedBonus":
                            turnRewards[i] = new Bonus(Realm.RED);
                            bonus[i] = "RB";
                            break;
                        case "GreenBonus":
                            turnRewards[i] = new Bonus(Realm.GREEN);
                            bonus[i] = "GB";
                            break;
                        case "BlueBonus":
                            turnRewards[i] = new Bonus(Realm.BLUE);
                            bonus[i] = "BB";
                            break;
                        case "MagentaBonus":
                            turnRewards[i] = new Bonus(Realm.MAGENTA);
                            bonus[i] = "MB";
                            break;
                        case "YellowBonus":
                            turnRewards[i] = new Bonus(Realm.YELLOW);
                            bonus[i] = "YB";
                            break;
                        case "null":
                        default:
                            bonus[i] = defString[i];
                            turnRewards[i] = def[i];
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            for (int j = 0; j < names.length; j++) {
                bonus[j] = defString[j];
                turnRewards[j] = def[j];
            }
        } catch (IOException e) {
            for (int j = 0; j < names.length; j++) {
                bonus[j] = defString[j];
                turnRewards[j] = def[j];
            }
        }
    }


    public int getArcaneBoostCount() {
        int count = 0;
        for (int i = 0; i < arcaneBoost.length; i++) {
            if (arcaneBoost[i].isObtained() && !(arcaneBoost[i].isUsed())) {
                count++;
            }
        }
        return count;
    }

    public int getTimeWarpCount() {
        int count = 0;
        for (int i = 0; i < timeWarp.length; i++) {
            if (timeWarp[i].isObtained() && !(timeWarp[i].isUsed())) {
                count++;
            }
        }
        return count;
    }

    public Lion getLion() {
        return lion;
    }

    public Dragon getDragon() {
        return dragon;
    }

    public Guardians getGuardians() {
        return guardians;
    }

    public Hydra getHydra() {
        return hydra;
    }

    public Phoenix getPhoenix() {
        return phoenix;
    }

    public String toString() {
        String s = "\n\nScoreSheet\n\n" + dragon.toString() + guardians.toString() + hydra.toString()
                + phoenix.toString() + lion.toString();
        return s;
    }

    public int getElementalCrest() {
        return ElementalCrest;
    }

    public void incrementElementalCrest() {
        ElementalCrest++;
        System.out.println("u got an elemental");
    }

    public void incrementTimeWarp() {
        for (int index = 0; index < timeWarp.length; index++) {
            if (!(timeWarp[index].isObtained())) {
                timeWarp[index].setObtained();
                return;
            }
        }
    }

    public void useTimeWarp() {
        for (int index = 0; index < timeWarp.length; index++) {
            if (timeWarp[index].isObtained() && !timeWarp[index].isUsed()) {
                timeWarp[index].setUsed();
                return;
            }
        }
    }

    public boolean timeWarpAvailable() {
        for (int index = 0; index < timeWarp.length; index++) {
            if (timeWarp[index].isObtained() && !timeWarp[index].isUsed()) {
                return true;
            }
        }
        return false;
    }

    public void incrementArcaneBoost() {
        for (int index = 0; index < arcaneBoost.length; index++) {
            if (!(arcaneBoost[index].isObtained())) {
                arcaneBoost[index].setObtained();
                return;
            }
        }
    }

    public boolean arcaneBoostAvailable() {
        for (int index = 0; index < arcaneBoost.length; index++) {
            if (arcaneBoost[index].isObtained() && !arcaneBoost[index].isUsed()) {
                return true;
            }
        }
        return false;
    }

    public void useArcaneBoost() {
        for (int index = 0; index < arcaneBoost.length; index++) {
            if (arcaneBoost[index].isObtained() && !arcaneBoost[index].isUsed()) {
                arcaneBoost[index].setUsed();
                return;
            }
        }
    }

    public Creature getCreatureByRealm(Dice x) {
        switch (x.getRealm()) {
            case RED:
                return dragon;

            case GREEN:
                return guardians;

            case BLUE:
                return hydra;

            case MAGENTA:
                return phoenix;

            case YELLOW:
                return lion;

            case WHITE:
                Scanner sc = new Scanner(System.in);
                System.out.println("Choose a realm (1-5)");
                System.out.println("1.Red 2.Green 3.Blue 4.Magenta 5.Yellow");
                int y = sc.nextInt();
                while (y < 1 || y > 5) {
                    System.out.println("Invalid input. Please try again.");
                    System.out.println("1.Red 2.Green 3.Blue 4.Magenta 5.Yellow");
                    y = sc.nextInt();
                }
                sc.close();
                switch (y) {
                    case 1:
                        return dragon;

                    case 2:
                        return guardians;

                    case 3:
                        return hydra;

                    case 4:
                        return phoenix;

                    case 5:
                        return lion;
                }
        }
        return dragon;
    }

    public ArcaneBoost[] getArcaneBoost() {
        return arcaneBoost;
    }

    public TimeWarp[] getTimeWarp() {
        return timeWarp;
    }

    public Reward[] getTurnRewards() {
        return turnRewards;
    }

    public ArrayList<Move> getMoveHistory() {
        return moveHistory;
    }

}
