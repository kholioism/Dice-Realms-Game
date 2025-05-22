package game.creatures;

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
import game.dice.Dice;
import game.dice.RedDice;
import game.engine.Move;
import game.engine.Realm;
import game.exceptions.PlayerActionException;

public class Dragon extends Creature {

    int[][] dragonHitBoard = { { 3, 2, 1, 0 }, { 6, 1, 0, 3 }, { 5, 0, 2, 4 }, { 0, 5, 4, 6 } };
    // try to tedakhaly feehoom el enum
    private DragonNumber[] theDragons = DragonNumber.values();
    // final int[][] the_scorehits = { { 3, 2, 1, 0 }, { 6, 1, 0, 3 }, { 5, 0, 2, 4
    // }, { 0, 5, 4, 6 } }; // do i even need this
    final int[] completeDragonScores = { 10, 14, 16, 20 };
    private int[] theScore = { 0, 0, 0, 0 };
    private String[] bonusArray = new String[5];
    private Boolean[] bonusObtained = { false, false, false, false, false };
    // make an array with ur score 3shan ne3raf
    Reward[] rewards;
    Reward[] def = { new Bonus(Realm.GREEN), new Bonus(Realm.YELLOW), new Bonus(Realm.BLUE), new ElementalCrest(),new ArcaneBoost() };
    String[] defString = { "GB", "YB", "BB", "EC", "AB" };
    boolean[] moveTobeHit = {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
    String[] redMoves = {"Dragon1 3","Dragon1 2","Dragon1 1","Dragon1 0","Dragon2 6","Dragon2 1","Dragon2 0","Dragon2 3","Dragon3 5","Dragon3 0","Dragon3 2","Dragon3 4","Dragon4 0","Dragon4 5","Dragon4 4","Dragon4 6"};

    public Dragon() {
        super();
        rewards = new Reward[5];
        String[] names = { "row1Reward", "row2Reward", "row3Reward", "row4Reward", "diagonalReward" };
        Properties p = new Properties();
        String file = "src/main/resources/config/EmberfallDominionRewards.properties";
        try {
            FileReader f = new FileReader(file);
            p.load(f);
            String s = "";
            for (int i = 0; i < rewards.length; i++) {
                s = p.getProperty(names[i]);
                switch (s) {
                    case "ArcaneBoost":
                        rewards[i] = new ArcaneBoost();
                        bonusArray[i] = "AB";
                        break;
                    case "TimeWarp":
                        rewards[i] = new TimeWarp();
                        bonusArray[i] = "TW";
                        break;
                    case "ElementalCrest":
                        rewards[i] = new ElementalCrest();
                        bonusArray[i] = "EC";
                        break;
                    case "RedBonus":
                        rewards[i] = new Bonus(Realm.RED);
                        bonusArray[i] = "RB";
                        break;
                    case "GreenBonus":
                        rewards[i] = new Bonus(Realm.GREEN);
                        bonusArray[i] = "GB";
                        break;
                    case "BlueBonus":
                        rewards[i] = new Bonus(Realm.BLUE);
                        bonusArray[i] = "BB";
                        break;
                    case "MagentaBonus":
                        rewards[i] = new Bonus(Realm.MAGENTA);
                        bonusArray[i] = "MB";
                        break;
                    case "YellowBonus":
                        rewards[i] = new Bonus(Realm.YELLOW);
                        bonusArray[i] = "YB";
                        break;
                    case "null":
                        rewards[i] = null;
                        bonusArray[i] = "  ";
                        break;
                    default:
                        bonusArray[i] = defString[i];
                        rewards[i] = def[i];
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            for (int i = 0; i < defString.length; i++) {
                bonusArray[i] = defString[i];
                rewards[i] = def[i];
            }
        } catch (IOException e) {
            for (int i = 0; i < defString.length; i++) {
                bonusArray[i] = defString[i];
                rewards[i] = def[i];
            }
        }
    }

    public int[][] getDragonHitBoard() {
        return dragonHitBoard;
    }

    public String toString() {
        String s = "";
        s = "Emberfall Dominion: Pyroclast Dragon (RED REALM):\n" +
                "+-----------------------------------+\n" +
                "|  #  |D1   |D2   |D3   |D4   |R    |\n" +
                "+-----------------------------------+\n";
        String[] dragonBodyParts = { "F", "W", "T", "H" };
        for (int j = 0; j < 4; j++) {
            s += "|  " + dragonBodyParts[j] + "  |";
            for (int i = 0; i < 4; i++) {
                if (dragonHitBoard[i][j] == 0)
                    s += "X    |";
                else
                    s += dragonHitBoard[i][j] + "    |";
            }
            s = s + bonusArray[j] + "   |\n";
        }
        s += "+-----------------------------------+\n" +
                "|  S  |10   |14   |16   |20   |" + bonusArray[4] + "   |\n" +
                "+-----------------------------------+\n\n" +
                "\n";
        return s;

    }
    public Boolean[] getBonusObtained() {
        return bonusObtained;
    }
    public String[] getBonusArray() {
        return bonusArray;
    }

    @Override
    // i added fel abstract method eno it throws brdo fel super!!
    public boolean makeMove(Dice dd) throws PlayerActionException {
        DragonNumber dragonnum = ((RedDice) dd).getDragonNumber();
        String dnum = "f";
        int counter_dead = 0;
        if (((RedDice) dd).getDragonNumber() == null) {
            while (!isNumeric(dnum) || Integer.parseInt(dnum) > 4 || Integer.parseInt(dnum) < 1) {
                Scanner sc = new Scanner(System.in);
                dnum = sc.nextLine();
                if (!isNumeric(dnum) || Integer.parseInt(dnum) > 4 || Integer.parseInt(dnum) < 1)
                    System.out.println("Invalid input. Please choose a dragon between 1 and 4.");
                else {
                    ((RedDice) dd).selectsDragon(Integer.parseInt(dnum));
                    System.out.println(((RedDice) dd).getDragonNumber() + "alo");
                    break;
                }
                sc.close();
            }
        }
        int drag_index = -1;
        for (int i = 0; i < 4; i++) {

            if (theDragons[i].equals(dragonnum))// AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            {
                drag_index = i;
                System.out.println("dragon number" + drag_index);
            }
        }
        if (drag_index == -1) // TODO:U CAN NEVER ALLOW A WRONG DICE IN LW SMHT
            return false;
        boolean killed = false;
        for (int j = 0; j < theDragons.length; j++) {

            if (dragonHitBoard[drag_index][j] == 0) {
                counter_dead += 1;
                //System.out.println("c++");
            }
            if (dragonHitBoard[drag_index][j] == dd.getValue()) {
               // System.out.println("killed");
                dragonHitBoard[drag_index][j] -= dd.getValue();
                killed = true;
            }
        }
        if (counter_dead == 3) {
            theScore[drag_index] = completeDragonScores[drag_index];
            System.out.println("score updated!");

        }
        return killed;
    }

    // public Move getPossibleforDragon(RedDice rr){
    // boolean found=false;
    // int i=0;
    // while(!found && i<4 ){
    // if(dragonHitBoard[])

    // }
    // }
    @Override
    public Move[] getPossibleMovesForADie(Dice d) {
        Move[] moves = null; //TODO:NULLPOINTEREXCEPTION?
        if(((RedDice)d).getDragonNumber()==null){
            ArrayList<Move> theMoves = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (dragonHitBoard[i][j] == d.getValue() && (d.getValue()>0 && d.getValue()<7)) {
                        theMoves.add(new Move(new RedDice(d.getValue(), (i + 1)), this));
                    }
                }
            }
            for (int i = 0; i < theMoves.size(); i++) {
                for (int j = i + 1; j < theMoves.size(); j++) {
                    if ((theMoves.get(i)).compareTo(theMoves.get(j)) == 0) {
                        theMoves.remove(j);
                    }
                }
            }
            if (!theMoves.isEmpty())
                moves = new Move[theMoves.size()];
            int i = 0;
            while (i < theMoves.size()) {
                moves[i] = theMoves.get(i);
                i++;
            }
            if (!theMoves.isEmpty())
                return moves;
            else
                return new Move[0];}
        else{
            int dragonNum = ((RedDice)d).getDragonNumberInteger();
            Move move = null;
            for (int i = 0; i < 4; i++) {
                if(dragonHitBoard[dragonNum-1][i]==d.getValue()){
                    move = new Move(d,this);
                }}
            if(move!=null){
                moves = new Move[1];
                moves[0]=move;
                return moves;
            }
            else
            {moves = new Move[0];
            return moves;}}
            
        
    }

    @Override
    public Reward[] getReward() {
        ArrayList<Reward> rewardList = new ArrayList<>();
        int counterdead = 0;
        int count_diagonal = 0;
        for (int k = 0; k < (bonusObtained.length-1) ; k++) {
            if (bonusObtained[k] == false) {
                counterdead = 0;
                    for (int j = 0; j < dragonHitBoard[k].length; j++) {
                        if (dragonHitBoard[j][k] == 0) {
                            counterdead++;
                        }
                        if (k == j && dragonHitBoard[j][k] == 0) {
                            count_diagonal++;
                        }
                    }
                if (counterdead == 4) {
                    switch (k) {
                        case 0:
                            rewardList.add(rewards[0]);
                            bonusObtained[0] = true;
                            bonusArray[0] = "X ";
                            break;
                        case 1:
                            rewardList.add(rewards[1]);
                            bonusObtained[1] = true;
                            bonusArray[1] = "X ";
                            break;
                        case 2:
                            rewardList.add(rewards[2]);
                            bonusObtained[2] = true;
                            bonusArray[2] = "X ";
                            break;
                        case 3:
                            rewardList.add(rewards[3]);
                            bonusObtained[3] = true;
                            bonusArray[3] = "X ";
                            break;
                    }
                }
            }
        }
        if (count_diagonal == 4) {
            rewardList.add(rewards[4]);
            bonusObtained[4] = true;
            bonusArray[4] = "X ";
        }
        Reward[] rewardArray = new Reward[rewardList.size()];
        for (int i = 0; i < rewardArray.length; i++) {
            rewardArray[i] = rewardList.get(i);
        }
        return rewardArray;
    }

    // public Reward[] getReward3matan() {
    //     ArrayList<Reward> rewardList = new ArrayList<>();
    //     int counterdead = 0;
    //     int count_diagonal = 0;
    //     for (int k = 0; k < 4 ; k++) {
    //          {
    //             counterdead = 0;
    //                 for (int j = 0; j < dragonHitBoard[k].length; j++) {
    //                     if (dragonHitBoard[j][k] == 0) {
    //                         counterdead++;
    //                     }
    //                     if (k == j && dragonHitBoard[j][k] == 0) {
    //                         count_diagonal++;
    //                     }
    //                 }
                
    //             if (counterdead == 4) {
    //                 System.out.println(k);
    //                 switch (k) {
    //                     case 0:
    //                         rewardList.add(rewards[0]);
    //                         break;
    //                     case 1:
    //                         rewardList.add(rewards[1]);
    //                         break;
    //                     case 2:
    //                         rewardList.add(rewards[2]);
    //                         break;
    //                     case 3:
    //                         rewardList.add(rewards[3]);
    //                         System.out.println("mafrood elemental otained");
    //                         break;
    //                 }
    //             }
    //         }
    //     }
    //     if (count_diagonal == 4) {
    //         rewardList.add(rewards[4]);
    //     }
    //     Reward[] rewardArray = new Reward[rewardList.size()];
    //     for (int i = 0; i < rewardArray.length; i++) {
    //         rewardArray[i] = rewardList.get(i);
    //     }
    //     return rewardArray;
    // }

    // public Reward retOneElemental(){
        
    //         int killedcount = 0;
    //         for (int i = 0; i < 4; i++) {
    //             if(dragonHitBoard[i][3]==0)
    //             killedcount++;
    //         }
    //         if
    //     }
    

    @Override
    public int getScore() {
        int finalscore = 0;
        for (int i = 0; i < 4; i++) {
            finalscore += theScore[i];
        }
        return finalscore;
    }

    @Override
    public int checkBonus(Move m) {
        int x = 0;
        switch(((RedDice)m.getDice()).getDragonNumberInteger()) {
            case 1:
                switch (m.getDice().getValue()) {
                    case 3:
                        x = 0;
                        break;
                    case 2:
                        x = 1;
                        break;
                    case 1:
                        x = 2;
                        break;
                }
            case 2:
                switch (m.getDice().getValue()) {
                    case 3:
                        x = 3;
                        break;
                    case 6:
                        x = 0;
                        break;
                    case 1:
                        x = 1;
                        break;
                }
            case 3:
                switch (m.getDice().getValue()) {
                    case 5:
                        x = 0;
                        break;
                    case 2:
                        x = 2;
                        break;
                    case 4:
                        x = 3;
                        break;
                }
            case 4:
                switch (m.getDice().getValue()) {
                    case 4:
                        x = 2;
                        break;
                    case 5:
                        x = 1;
                        break;
                    case 6:
                        x = 3;
                        break;
                }
        }
        int points = 0;
        for (int i = 0; i < 4; i++) {
            if (dragonHitBoard[i][x]==0){
                points++;
            }
        }
        return points+1;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            @SuppressWarnings("unused")
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public int checkPoints(Move m){
        return completeDragonScores[((RedDice)m.getDice()).getDragonNumberInteger()-1]/4;
    }

}
