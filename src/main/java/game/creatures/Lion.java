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
import game.engine.Move;
import game.engine.Realm;
import game.exceptions.InvalidMoveException;

public class Lion extends Creature {
    int[] lion2 = new int[11];
    int counter = 0;
    int score;
    Reward[] r = new Reward[11];
    String[] bonus = new String[11];
    String[] defString = {"  ","  ","TW","  ","RB","AB","  ","EC","  ","MB","  "};
    Reward[] def = {null,null,new TimeWarp(),null,new Bonus(Realm.RED),new ArcaneBoost(),null,new ElementalCrest(),null,new Bonus(Realm.MAGENTA),null};

    boolean[] movetobehit = {false,false,false,false,false,false};
    String[] yellowmoves = {"YELLOW 1", "YELLOW 2" , "YELLOW 3", "YELLOW 4", "YELLOW 5", "YELLOW 6"};
    public Lion() {
        super();
        Properties p = new Properties();
        String file = "src/main/resources/config/RadiantSvannaRewards.properties";
        FileReader f;
        try {
            f = new FileReader(file);
            String[] names = { "hit1Reward", "hit2Reward", "hit3Reward", "hit4Reward", "hit5Reward", "hit6Reward",
            "hit7Reward", "hit8Reward", "hit9Reward", "hit10Reward", "hit11Reward" };
            p.load(f);
            String s = "";
            for (int i = 0; i < names.length; i++) {
                s = p.getProperty(names[i]);
                if(s!=null){
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
                        r[i] = def[i];
                        bonus[i] = defString[i];
                }
            }
        }
        } catch (FileNotFoundException e) {
            for (int i = 0; i < bonus.length; i++) {
                r[i] = def[i];
                bonus[i] = defString[i];
            }
        } catch (IOException e) {
            for (int i = 0; i < bonus.length; i++) {
                r[i] = def[i];
                bonus[i] = defString[i];
            }
        }
    }

    public int getCounter() {
        return counter;
    }
    
    public Reward[] getReward(){//Still implementation of updating counter or not? + implementation of configuration file
        Reward[] tempReward = new Reward[1];
        int counter2 =  counter - 1;
        switch(counter2){
            case 0:
                if(r[0] != null){
                    bonus[0] = "X ";
                }
                tempReward[0] = r[0];
                r[0] = null;
                break;
            case 1:
                if(r[1] != null){
                    bonus[1] = "X ";
                }
                tempReward[0] = r[1];
                r[1] = null;
                break;
            case 2:
                if(r[2] != null){
                    bonus[2] = "X ";
                }
                tempReward[0] = r[2];
                r[2] = null;
                break;
            case 3:
                if(r[3] != null){
                    bonus[3] = "X ";
                }
                tempReward[0] = r[3];
                r[3] = null;
                break;
            case 4:
                if(r[4] != null){
                    bonus[4] = "X ";
                }
                tempReward[0] = r[4];
                r[4] = null;
                break;
            case 5:
                if(r[5] != null){
                    bonus[5] = "X ";
                }
                tempReward[0] = r[5];
                r[5] = null;
                break;
            case 6:
                if(r[6] != null){
                    bonus[6] = "X ";
                }
                tempReward[0] = r[6];
                r[6] = null;
                break;
            case 7:
                if(r[7] != null){
                    bonus[7] = "X ";
                }
                tempReward[0] = r[7];
                r[7] = null;
                break;
            case 8:
                if(r[8] != null){
                    bonus[8] = "X ";
                }
                tempReward[0] = r[8];
                r[8] = null;
                break;
            case 9:
                if(r[9] != null){
                    bonus[9] = "X ";
                }
                tempReward[0] = r[9];
                r[9] = null;
                break;
            case 10:
                if(r[10] != null){
                    bonus[10] = "X ";
                }
                tempReward[0] = r[10];
                r[10] = null;
                break;
            default:
                break;
        }
        return tempReward;
    }

    public String toString() {// handling of bonuses done using boolean variable taken and conditional
                              // operator
        String s = "\n" +
                "Radiant Savanna: Solar Lion (YELLOW REALM):\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  H  |" + ((lion2[0] > 9) ? lion2[0] + "   |" : lion2[0]+"    |" ) + ((lion2[1] > 9) ? lion2[1] + "   |" : lion2[1] + "    |" ) + ((lion2[2] > 9) ? lion2[2] + "   |" : lion2[2] + "    |" ) + ((lion2[3] > 9) ? lion2[3] +  "   |" : lion2[3] + "    |" )
                + ((lion2[4] > 9) ? lion2[4] +  "   |" : lion2[4]+ "    |" ) + ((lion2[5] > 9) ? lion2[5] + "   |" : lion2[5]+ "    |" ) + ((lion2[6] > 9) ? lion2[6] +  "   |" : lion2[6]+ "    |" )
                + ((lion2[7] > 9) ? lion2[7] + "   |" : lion2[7]+ "    |" ) + ((lion2[8] > 9) ? lion2[8] + "   |" : lion2[8]+ "    |" ) + ((lion2[9] > 9) ? lion2[9] + "   |" : lion2[9]+ "    |" )
                + ((lion2[10] > 9) ? lion2[10] + "   |" : lion2[10]+ "    |" ) + "\n" +
                "|  M  |     |     |     |x2   |     |     |x2   |     |x2   |     |x3   |\n" +
                "|  R  |" + bonus[0] + "   |" + bonus[1] + "   |" + bonus[2] + "   |" + bonus[3] + "   |" + bonus[4]
                + "   |" + bonus[5] + "   |" +
                bonus[6] + "   |" + bonus[7] + "   |" + bonus[8] + "   |" + bonus[9] + "   |" + bonus[10] + "   |\n" +
                "+-----------------------------------------------------------------------+\n\n";
        return s;
    }

    public int getScore() {
        int s = 0;// kan bey accumulate 3al total score ely mahsoob men akher move
        for (int i = 0; i < lion2.length; i++) {
            s += lion2[i];
        }
        score = s;
        return score;
    }

    public Move[] getPossibleMovesForADie(Dice dice) {
        Move[] move = new Move[1];
        if (counter <= 10) {
            move[0] = new Move(dice, this);
        }
        return move;
    }

    public int checkBonus(Move m) {
        if(r[counter] != null){
            return 4;
        }
        return 0;
    }

    public boolean makeMove(Dice d) throws InvalidMoveException {// combined hit which was setlion2 with makemove
                                                                 // validation + filling out the array
        // + handling invalid move exception inside makemove method
        if (counter <= 10) {
            switch (counter) {
                case 0:
                case 1:
                case 2:
                case 4:
                case 5:
                case 7:
                case 9:
                    lion2[counter] = d.getValue();
                    break;
                case 3:
                case 6:
                case 8:
                    lion2[counter] = d.getValue() * 2;
                    break;
                case 10:
                    lion2[counter] = d.getValue() * 3;
                    break;
                default:
                    break;
            }
            counter++;
            return true;
        } else {
            throw new InvalidMoveException();
        }
    }

    public String[] getBonus() {
        return bonus;
    }
    public int[] getLion2() {
        return lion2;
    }

}

