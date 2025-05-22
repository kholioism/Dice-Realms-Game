package game.creatures;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import game.collectibles.ArcaneBoost;
import game.collectibles.Bonus;
import game.collectibles.ElementalCrest;
import game.collectibles.Reward;
import game.collectibles.TimeWarp;
import game.dice.Dice;
import game.dice.GreenDice;
import game.engine.Move;
import game.engine.Realm;


public class Guardians extends Creature {
    int GuardiansNo;
    //hitvalus is 1
    //values is 2
    String[][] guardians_hitValues = { { "X", "2", "3", "4" }, { "5", "6", "7", "8" }, { "9", "10", "11", "12" } };
    final int[][] guardians_values = { { 0, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
    final int[] guardians_scores = { 0, 1, 2, 4, 7, 11, 16, 22, 29, 37, 46, 56 };
    String[] bonus = new String[7];
    int score;
    int count = 0;
    Reward[] def =  {new Bonus(Realm.YELLOW), new Bonus(Realm.RED), new ElementalCrest(), new TimeWarp(), new Bonus(Realm.BLUE), new Bonus(Realm.MAGENTA), new ArcaneBoost()};
    String[] defStr = {"YB","RB","EC","TW","BB","MB","AB"};

    ElementalCrest elementalCrest;
    TimeWarp timeWarp;
    ArcaneBoost arcaneBoost;
    boolean taken = false;
    Reward[] rewards;
    boolean[] moveTobeHit = {false,false,false,false,false,false,false,false,false,false,false};
    String[] greenMoves = {"GREEN 2","GREEN 3","GREEN 4","GREEN 5","GREEN 6","GREEN 7","GREEN 8","GREEN 9","GREEN 10","GREEN 11","GREEN 12"};

    // constructor
    public Guardians() {
        super();
        rewards = new Reward[7];
        String[] names = { "row1Reward", "row2Reward", "row3Reward", "column1Reward", "column2Reward", "column3Reward",
                "column4Reward" };
        try {
            FileReader f = new FileReader("src/main/resources/config/TerrasHeartlandRewards.properties");
            Properties p = new Properties();
            p.load(f);
            String s = "";
            for (int i = 0; i < names.length; i++) {
                s = p.getProperty(names[i]);
                if (s != null) {
                    switch (s) {
                        case "ArcaneBoost":
                            rewards[i] = new ArcaneBoost();
                            bonus[i] = "AB";
                            break;
                        case "TimeWarp":
                            rewards[i] = new TimeWarp();
                            bonus[i] = "TW";
                            break;
                        case "ElementalCrest":
                            rewards[i] = new ElementalCrest();
                            bonus[i] = "EC";
                            break;
                        case "RedBonus":
                            rewards[i] = new Bonus(Realm.RED);
                            bonus[i] = "RB";
                            break;
                        case "GreenBonus":
                            rewards[i] = new Bonus(Realm.GREEN);
                            bonus[i] = "GB";
                            break;
                        case "BlueBonus":
                            rewards[i] = new Bonus(Realm.BLUE);
                            bonus[i] = "BB";
                            break;
                        case "MagentaBonus":
                            rewards[i] = new Bonus(Realm.MAGENTA);
                            bonus[i] = "MB";
                            break;
                        case "YellowBonus":
                            rewards[i] = new Bonus(Realm.YELLOW);
                            bonus[i] = "YB";
                            break;
                        case "null":
                            rewards[i] = null;
                            bonus[i] = "  ";
                            break;
                        default:
                            rewards[i] = def[i];
                            bonus[i] = defStr[i];
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            for (int i = 0; i < bonus.length; i++) {
                rewards[i] = def[i];
                bonus[i] = defStr[i];
            }
        } catch (IOException e) {
            for (int i = 0; i < bonus.length; i++) {
                rewards[i] = def[i];
                bonus[i] = defStr[i];
            }
        }

    }
    public String[][] getguardians_hitValues() {
        return guardians_hitValues;
    }

    public int[][] getguardians_values() {
        return guardians_values;
    }
    public String[] getBonus() {
        return bonus;
    }

    // GET THE DICE VALUE (green + white)

    public Move[] getPossibleMovesForADie(Dice dice) {
        GreenDice g = (GreenDice) dice;
        Move[] moves = new Move[1];
        int x = g.getValue();
        for (int i = 0; i < guardians_values.length; i++) {
            for (int j = 0; j < guardians_values[i].length; j++) {
                if (x == guardians_values[i][j] && !(guardians_hitValues[i][j].equals("X"))) {
                    moves[0] = new Move(dice, this);
                    return moves;
                }
            }
        }
        Move[] empty = new Move[0];
        return empty;
    }

    public boolean makeMove(Dice dice){
        GreenDice g = (GreenDice) dice;
        int x = g.getValue();
        for (int i = 0; i < guardians_values.length; i++) {
            for (int j = 0; j < guardians_values[i].length; j++) {
                if (x == guardians_values[i][j] && (!(guardians_hitValues[i][j].equals("X")) || !(guardians_hitValues[i][j].equals("X ")))) {
                    if (guardians_values[i][j] > 9) {
                        guardians_hitValues[i][j] = "X ";
                    } else {
                        guardians_hitValues[i][j] = "X";
                    }
                    GuardiansNo++;
                    count++;
                    score = guardians_scores[count];
                    return true;
                }
            }
        }
        return false;
    }

    public Reward[] getReward() {
        ArrayList<Reward> r = new ArrayList<Reward>();

        if (guardians_hitValues[0][1].equals("X") && guardians_hitValues[0][2].equals("X") && guardians_hitValues[0][3].equals("X") && rewards[0] != null) {
            r.add(rewards[0]);
            rewards[0] = null;
            bonus[0] = "X ";
        }
        if (guardians_hitValues[1][0].equals("X") && guardians_hitValues[1][1].equals("X") && guardians_hitValues[1][2].equals("X") && guardians_hitValues[1][3].equals("X")
                && rewards[1] != null) {
            r.add(rewards[1]);
            rewards[1] = null;
            bonus[1] = "X ";
        }
        if (guardians_hitValues[2][0].equals("X") && guardians_hitValues[2][1].equals("X ") && guardians_hitValues[2][2].equals("X ") && guardians_hitValues[2][3].equals("X ")
                && rewards[2] != null) {
            r.add(rewards[2]);
            rewards[2] = null;
            bonus[2] = "X ";
        }
        if (guardians_hitValues[0][0].equals("X") && guardians_hitValues[1][0].equals("X") && guardians_hitValues[2][0].equals("X") && rewards[3] != null) {
            r.add(rewards[3]);
            rewards[3] = null;
            bonus[3] = "X ";
        }
        if (guardians_hitValues[0][1].equals("X") && guardians_hitValues[1][1].equals("X") && guardians_hitValues[2][1].equals("X ") && rewards[4] != null) {
            r.add(rewards[4]);
            rewards[4] = null;
            bonus[4] = "X ";
        }
        if (guardians_hitValues[0][2].equals("X") && guardians_hitValues[1][2].equals("X") && guardians_hitValues[2][2].equals("X ") && rewards[5] != null) {
            r.add(rewards[5]);
            rewards[5] = null;
            bonus[5] = "X ";
        }
        if (guardians_hitValues[0][3].equals("X") && guardians_hitValues[1][3].equals("X") && guardians_hitValues[2][3].equals("X ") && rewards[6] != null) {
            r.add(rewards[6]);
            rewards[6] = null;
            bonus[6] = "X ";
        }
        Reward[] rewards = new Reward[r.size()];
        for (int i = 0; i < rewards.length; i++) {
            rewards[i] = r.get(i);
        }
        return rewards;
    }

    // public Reward[] getReward3matan(){
    //     ArrayList<Reward> r = new ArrayList<Reward>();

    //     if (guardians_hitValues[0][1].equals("X") && guardians_hitValues[0][2].equals("X") && guardians_hitValues[0][3].equals("X") ) {
    //         r.add(rewards[0]);
           
    //     }
    //     if (guardians_hitValues[1][0].equals("X") && guardians_hitValues[1][1].equals("X") && guardians_hitValues[1][2].equals("X") && guardians_hitValues[1][3].equals("X")
    //            ) {
    //         r.add(rewards[1]);
            
    //     }
    //     if (guardians_hitValues[2][0].equals("X") && guardians_hitValues[2][1].equals("X") && guardians_hitValues[2][2].equals("X") && guardians_hitValues[2][3].equals("X")
    //            ) {
    //         r.add(rewards[2]);
            
    //     }
    //     if (guardians_hitValues[0][0].equals("X") && guardians_hitValues[1][0].equals("X") && guardians_hitValues[2][0].equals("X")) {
    //         r.add(rewards[3]);
            
    //     }
    //     if (guardians_hitValues[0][1].equals("X") && guardians_hitValues[1][1].equals("X") && guardians_hitValues[2][1].equals("X")) {
    //         r.add(rewards[4]);
            
    //     }
    //     if (guardians_hitValues[0][2].equals("X") && guardians_hitValues[1][2].equals("X") && guardians_hitValues[2][2].equals("X")) {
    //         r.add(rewards[5]);
            
    //     }
    //     if (guardians_hitValues[0][3].equals("X") && guardians_hitValues[1][3].equals("X") && guardians_hitValues[2][3].equals("X") ) {
    //         r.add(rewards[6]);
            
    //     }
    //     Reward[] rewards = new Reward[r.size()];
    //     for (int i = 0; i < rewards.length; i++) {
    //         rewards[i] = r.get(i);
    //     }
    //     return rewards;
       
    // }

    // A toString method to represent the score sheet for the green realm
    public String toString() {
        String s = "";
        s += "Terra's Heartland: Gaia Guardians (GREEN REALM):\n";
        s += "+-----------------------------------+" + "\n";
        s += "|  #  |1    |2    |3    |4    |R    |" + "\n";
        s += "+-----------------------------------+" + "\n";
        s += "|  1  |X    |" + guardians_hitValues[0][1] + "    |" + guardians_hitValues[0][2] + "    |" + guardians_hitValues[0][3] + "    |" + bonus[0] + "   |"
                + "\n";
        s += "|  2  |" + guardians_hitValues[1][0] + "    |" + guardians_hitValues[1][1] + "    |" + guardians_hitValues[1][2] + "    |" + guardians_hitValues[1][3] + "    |"
                + bonus[1] + "   |" + "\n";
        s += "|  3  |" + guardians_hitValues[2][0] + "    |" + guardians_hitValues[2][1] + "   |" + guardians_hitValues[2][2] + "   |" + guardians_hitValues[2][3] + "   |"
                + bonus[2] + "   |" + "\n";
        s += "+-----------------------------------+" + "\n";
        s += "|  R  |" + bonus[3] + "   |" + bonus[4] + "   |" + bonus[5] + "   |" + bonus[6] + "   |     |" + "\n";
        s += "+-----------------------------------------------------------------------+" + "\n";
        s += "|  S  |1    |2    |4    |7    |11   |16   |22   |29   |37   |46   |56   |" + "\n";
        s += "+-----------------------------------------------------------------------+" + "\n";
        return s + "\n\n";
    }

    // What I have to do:
    // TODO: EXCEPTIONS needed
    // TODO: what if I got a white dice value (the idea of setting the white value
    // in a new green dice and put the original green value in the white value)

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int checkBonus(Move m) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < guardians_values.length; i++) {
            for (int j = 0; j < guardians_values[i].length; j++) {
                if (m.getDice().getValue()==guardians_values[i][j]){
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        int points = 0;
        for (int i = 0; i < 3; i++) {
            if (!(guardians_hitValues[i][y].equals("X")) || !(guardians_hitValues[i][y].equals("X "))){
                points++;
            }
        }
        for (int i = 0; i < 4; i++) {
            if (!(guardians_hitValues[x][i].equals("X")) || !(guardians_hitValues[x][i].equals("X "))){
                points++;
            }
        }
        return points+3;
    }

    public int checkPoints() {
        return guardians_scores[count+1]-guardians_scores[count];
    }
}
