package game.creatures;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import game.collectibles.ArcaneBoost;
import game.collectibles.Bonus;
import game.collectibles.ElementalCrest;
import game.collectibles.Reward;
import game.collectibles.TimeWarp;
import game.dice.Dice;
import game.dice.MagentaDice;
import game.engine.Move;
import game.engine.Realm;

public class Phoenix extends Creature {
    int previousValue;
    int newValue;
    int counter;
    int score;
    int[] chosenDices;
    String[] bonus = new String[11];
    Reward[] r = new Reward[11];
    Reward[] def = {null,null,new TimeWarp(),new Bonus(Realm.GREEN),new ArcaneBoost(),new Bonus(Realm.RED),new ElementalCrest(),new TimeWarp(),new Bonus(Realm.BLUE),new Bonus(Realm.YELLOW), new ArcaneBoost()};
    String[] defString = {"  ","  ","TW","GB","AB","RB","EC","TW","BB","YB","AB"};

    boolean[] movetobehit = {false,false,false,false,false,false};
    String[] magentamoves = {"MAGENTA 1", "MAGENTA 2" , "MAGENTA 3", "MAGENTA 4", "MAGENTA 5", "MAGENTA 6"};
    public Phoenix() {
        this.newValue = 0;
        chosenDices = new int[11];
        this.previousValue = -1;
        this.counter = 0;
        this.score = 0;
        Properties p = new Properties();
        String file = "src/main/resources/config/MysticalSkyRewards.properties";
        FileReader f;
        try {
            f = new FileReader(file);
            String[] names = { "hit1Reward", "hit2Reward", "hit3Reward", "hit4Reward", "hit5Reward", "hit6Reward",
                    "hit7Reward", "hit8Reward", "hit9Reward", "hit10Reward", "hit11Reward" };
            p.load(f);
            String s = "";
            for (int i = 0; i < names.length; i++) {
                s = p.getProperty(names[i]);
                switch (s) {
                    case "ArcaneBoost":
                        r[i] = new ArcaneBoost();
                        bonus[i] = "AB";
                        break;
                    case "TimeWarp":
                        r[i] = new TimeWarp();
                        bonus[i] = "TW";
                        break;
                    case "ElementalCrest":
                        r[i] = new ElementalCrest();
                        bonus[i] = "EC";
                        break;
                    case "RedBonus":
                        r[i] = new Bonus(Realm.RED);
                        bonus[i] = "RB";
                        break;
                    case "GreenBonus":
                        r[i] = new Bonus(Realm.GREEN);
                        bonus[i] = "GB";
                        break;
                    case "BlueBonus":
                        r[i] = new Bonus(Realm.BLUE);
                        bonus[i] = "BB";
                        break;
                    case "MagentaBonus":
                        r[i] = new Bonus(Realm.MAGENTA);
                        bonus[i] = "MB";
                        break;
                    case "YellowBonus":
                        r[i] = new Bonus(Realm.YELLOW);
                        bonus[i] = "YB";
                        break;
                    case "null":
                        r[i] = null;
                        bonus[i] = "  ";
                        break;
                    default:
                        bonus[i] = defString[i];
                        r[i] = def[i];
                }
            }
        } catch (FileNotFoundException e) {
            for (int i = 0; i < bonus.length; i++) {
                bonus[i] = defString[i];
                r[i] = def[i];
            }
        } catch (IOException e) {
            for (int i = 0; i < bonus.length; i++) {
                bonus[i] = defString[i];
                r[i] = def[i];
            }
        }

    }

    public int getCounter() {
        return counter;
    }

    public boolean Acceptable(MagentaDice m)// OHHH EL NEW VALUE WAS ONLY SET LAMA GEIT A3MEL EL MAKEMOVE
                                            // I SHOULD CHANGE IT GLOBALLY AWEL MAKHOD EL dice
    {
        newValue = m.getValue();
        if (newValue > previousValue) {
            return true;
        }
        return false;
    }

    public Move[] getPossibleMovesForADie(Dice dice) {
        if (Acceptable((MagentaDice) dice) && counter < 11) {
            Move[] m = new Move[1];
            m[0] = new Move(dice, this);
            return m;
        }
        Move[] m = new Move[0];
        return m;
    }

    public boolean makeMove(Dice d) // throws InvalidMoveException
    {
        newValue = d.getValue();
        if (Acceptable((MagentaDice) d)) {
            if (newValue == 6) {
                System.out.println("You have killed the phoenix!"); // Gui
                previousValue = 0;
                System.out.println("The phoenix has been revived"); // Gui
            } else {
                previousValue = newValue;
            }
            chosenDices[counter] = newValue;
            score = score + newValue;
            counter++;
            return true;
        }
        return false;
        // throw new InvalidMoveException("The move is not possible");
    }

    public Reward[] getReward() {
        Reward[] temp = new Reward[1];
        switch (counter-1) {
            case 0:
                if (r[0] != null) {
                    bonus[0] = "X ";
                }
                temp[0] = r[0];
                r[0] = null;
                break;
            case 1:
                if (r[1] != null) {
                    bonus[1] = "X ";
                }
                temp[0] = r[1];
                r[1] = null;
                break;
            case 2:
                if (r[2] != null) {
                    bonus[2] = "X ";
                }
                temp[0] = r[2];
                r[2] = null;
                break;
            case 3:
                if (r[3] != null) {
                    bonus[3] = "X ";
                }
                temp[0] = r[3];
                r[3] = null;
                break;
            case 4:
                if (r[4] != null) {
                    bonus[4] = "X ";
                }
                temp[0] = r[4];
                r[4] = null;
                break;
            case 5:
                if (r[5] != null) {
                    bonus[5] = "X ";
                }
                temp[0] = r[5];
                r[5] = null;
                break;
            case 6:
                if (r[6] != null) {
                    bonus[6] = "X ";
                }
                temp[0] = r[6];
                r[6] = null;
                break;
            case 7:
                if (r[7] != null) {
                    bonus[7] = "X ";
                }
                temp[0] = r[7];
                r[7] = null;
                break;
            case 8:
                if (r[8] != null) {
                    bonus[8] = "X ";
                }
                temp[0] = r[8];
                r[8] = null;
                break;
            case 9:
                if (r[9] != null) {
                    bonus[9] = "X ";
                }
                temp[0] = r[9];
                r[9] = null;
                break;
            case 10:
                if (r[10] != null) {
                    bonus[10] = "X ";
                }
                temp[0] = r[10];
                r[10] = null;
                break;
            default:
                break;
        }
        return temp;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int checkBonus(Move m) {
        if(r[counter] != null){
            return 4;
        }
        return 0;
    }

    public String toString() {
        return "Mystical Sky: Majestic Phoenix (MAGENTA REALM):\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  H  |" + chosenDices[0] + "    |" + chosenDices[1] + "    |" + chosenDices[2] + "    |"
                + chosenDices[3] + "    |" + chosenDices[4] + "    |" + chosenDices[5] + "    |" + chosenDices[6]
                + "    |" + chosenDices[7] + "    |" + chosenDices[8] + "    |" + chosenDices[9] + "    |"
                + chosenDices[10] + "    |\n" +
                "|  C  |<    |<    |<    |<    |<    |<    |<    |<    |<    |<    |<    |\n" +
                "|  R  |" + bonus[0] + "   |" + bonus[1] + "   |" + bonus[2] + "   |" + bonus[3] + "   |" + bonus[4]
                + "   |" + bonus[5] + "   |" + bonus[6] + "   |" + bonus[7] + "   |" + bonus[8] + "   |" + bonus[9]
                + "   |" + bonus[10] + "   |\n" +
                "+-----------------------------------------------------------------------+\n\n";
    }

    public String[] getBonus() {
        return bonus;
    }
    public int[] getChosenDices() {
        return chosenDices;
    }
}
