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

public class Hydra extends Creature {
    private int[] HydraSerpentScore = { 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 66 };
    String[] DefeatedHead = { "---", "---", "---", "---", "---", "---", "---", "---", "---", "---", "---" };
    private int fiveHeads;
    private int sixHeads;
    private int index;
    private int currenthead;
    private int currentScore;
    String[] bonus = new String[11];
    Reward[] r = new Reward[11];
    Reward[] def =  {null, null, null, new ArcaneBoost(), null, new Bonus(Realm.GREEN), new ElementalCrest(),null,new Bonus(Realm.MAGENTA),new TimeWarp(),null};
    String[] defStr = {"     ","     ","     ","AB   ","     ","GB   ","EC   ","     ","MB   ","TW   ","     "};

    boolean[] movetobehit = {false,false,false,false,false,false};
    String[] bluemoves = {"BLUE 1", "BLUE 2" , "BLUE 3", "BLUE 4", "BLUE 5","BLUE 6"};
    public Hydra() {
        super();
        index = 0;
        currenthead = 1;
        fiveHeads = 1;
        sixHeads = 1;
        currentScore = 0;
        Properties p = new Properties();
        String file = "src/main/resources/config/TideAbyssRewards.properties";
        FileReader f;
        try {
            f = new FileReader(file);
            String[] names = { "hit1Reward", "hit2Reward", "hit3Reward", "hit4Reward", "hit5Reward", "hit6Reward",
                    "hit7Reward", "hit8Reward", "hit9Reward", "hit10Reward", "hit11Reward" };
            p.load(f);
            String s = "";
            for (int i = 0; i < names.length; i++) {
                s = p.getProperty(names[i]);
                if (s != null) {
                    switch (s) {
                        case "ArcaneBoost":
                            r[i] = new ArcaneBoost();
                            bonus[i] = "AB   ";
                            break;
                        case "TimeWarp":
                            r[i] = new TimeWarp();
                            bonus[i] = "TW   ";
                            break;
                        case "ElementalCrest":
                            r[i] = new ElementalCrest();
                            bonus[i] = "EC   ";
                            break;
                        case "RedBonus":
                            r[i] = new Bonus(Realm.RED);
                            bonus[i] = "RB   ";
                            break;
                        case "GreenBonus":
                            r[i] = new Bonus(Realm.GREEN);
                            bonus[i] = "GB   ";
                            break;
                        case "BlueBonus":
                            r[i] = new Bonus(Realm.BLUE);
                            bonus[i] = "BB   ";
                            break;
                        case "MagentaBonus":
                            r[i] = new Bonus(Realm.MAGENTA);
                            bonus[i] = "MB   ";
                            break;
                        case "YellowBonus":
                            r[i] = new Bonus(Realm.YELLOW);
                            bonus[i] = "YB   ";
                            break;
                        case "null":
                            r[i] = null;
                            bonus[i] = "     ";
                            break;
                        default:
                            bonus[i] = defStr[i];
                            r[i]=def[i];
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            for (int i = 0; i < def.length; i++) {
                bonus[i] = defStr[i];
                r[i]=def[i];
            }
        } catch (IOException e) {
            for (int i = 0; i < def.length; i++) {
                bonus[i] = defStr[i];
                r[i]=def[i];
            }
        }

    }
    
    public String[] getBonus() {
        return bonus;
    }

    public int getCurrenthead() {
        return currenthead;
    }

    public int getIndex() {
        return index;
    }

    public boolean makeMove(Dice d) throws InvalidMoveException {
       // if(d==null){
        //    throw new NullPointerException();
      // }
       int points = d.getValue();
      // if(points<1||points>6){
      //      throw new DiceRollException();
       // }
        if (sixHeads < 7) {
            if (isMoveAcceptable(points)) {
                DefeatHead(points);
                return true;
            } else {
                throw new InvalidMoveException("No hydra head can be defeated using the available points.");
            }
        } else {
            throw new IndexOutOfBoundsException("The hydra serpent is dead!");
        }

    }

    boolean isMoveAcceptable(int points) {
        return (fiveHeads > 5 && sixHeads <= points) || (fiveHeads <= points);

    }

    void DefeatHead(int points) {
        if (fiveHeads > 5) {
            sixHeads++;
            currenthead = sixHeads;

        } else {
            fiveHeads++;
            currenthead = fiveHeads;

        }
        currentScore= HydraSerpentScore[index];
        DefeatedHead[index] = "X  ";
        index++;

    }

    public int getScore() {
        return currentScore;
  
    }

    // int HeadWillBeDefeated(){
    // return currenthead;
    // }
    public Reward[] getReward() {
        Reward[] r1 = new Reward[1];
        switch (index) {
            case 1:
                r1[0] = r[0];
                if (r[0] != null) {
                    bonus[0] = "X    ";
                } 
                r[0] = null;
                break;
            case 2:
                r1[0] = r[1];
                if (r[1] != null) {
                    bonus[1] = "X    ";
                }r[1] = null;
                break;
            case 3:
                r1[0] = r[2];
                
                if (r[2] != null) {
                    bonus[2] = "X    ";
                }r[2] = null;
                break;
            case 4:
                r1[0] = r[3];
                
                if (r[3] != null) {
                    bonus[3] = "X    ";
                }r[3] = null;
                break;
            case 5:
                r1[0] = r[4];
                
                if (r[4] != null) {
                    bonus[4] = "X    ";
                }r[4] = null;
                break;
            case 6:
                r1[0] = r[5];
                if (r[5] != null) {
                    bonus[5] = "X    ";
                }r[5] = null;
                break;

            case 7:
                r1[0] = r[6];
                if (r[6] != null) {
                    bonus[6] = "X    ";
                }r[6] = null;
                break;
            case 8:
                r1[0] = r[7];
                if (r[7] != null) {
                    bonus[7] = "X    ";
                } r[7] = null;
                break;
            case 9:
                r1[0] = r[8];
                if (r[8] != null) {
                    bonus[8] = "X    ";
                }r[8] = null;
                break;
            case 10:
                r1[0] = r[9];
                if (r[9] != null) {
                    bonus[9] = "X    ";
                }r[9] = null;
                break;
            case 11:
                r1[0] = r[10];
                if (r[10] != null) {
                    bonus[10] = "X    ";
                }r[10] = null;
                break;    

            default:
                break;

        }
        return r1; 
    }
   
    public Move[] getPossibleMovesForADie(Dice d)  {
        //if(d==null){
       //     throw new NullPointerException();
       // }
       // int points = d.getValue();
        //if(points<1||points>6){
        //    throw new DiceRollException();
       // }
        if (sixHeads < 7 && isMoveAcceptable(d.getValue())) {
            Move[] m = new Move[1];
            m[0] = new Move(d, this);
            return m;
        }
        Move[] m = new Move[0];
        return m;

    }

    public String toString() {
        return "Tide Abyss: Hydra Serpents (BLUE REALM):\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  #  |H11  |H12  |H13  |H14  |H15  |H21  |H22  |H23  |H24  |H25  |H26  |\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  H  |" + DefeatedHead[0] + "  |" + DefeatedHead[1] + "  |" + DefeatedHead[2] + "  |" + DefeatedHead[3] + "  |" + DefeatedHead[4]
                + "  |" + DefeatedHead[5] + "  |" + DefeatedHead[6] + "  |" + DefeatedHead[7] + "  |" +DefeatedHead[8] + "  |" + DefeatedHead[9]
                + "  |" + DefeatedHead[10] + "  |\n" +
                "|  C  |≥1   |≥2   |≥3   |≥4   |≥5   |≥1   |≥2   |≥3   |≥4   |≥5   |≥6   |\n" +
                "|  R  |"+bonus[0]+"|"+bonus[1]+"|"+bonus[2]+"|" + bonus[3] + "|"+bonus[4]+"|" + bonus[5] + "|" + bonus[6] + "|"+bonus[7]+"|"
                + bonus[8] + "|" + bonus[9] + "|"+bonus[10]+"|\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  S  |1    |3    |6    |10   |15   |21   |28   |36   |45   |55   |66   |\n" +
                "+-----------------------------------------------------------------------+\n\n\n";
    }

    public int checkPoints() {
        return HydraSerpentScore[index+1]-HydraSerpentScore[index];
    }

    public int checkBonus(Move m) {
        if(r[index] != null){
            return 4;
        }
        return 0;
    }
    public String[] getDefeatedHead() {
        return DefeatedHead;
    }
}

