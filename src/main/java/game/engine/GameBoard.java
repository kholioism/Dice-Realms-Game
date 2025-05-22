package game.engine;

import game.dice.ArcanePrism;
import game.dice.BlueDice;
import game.dice.Dice;
import game.dice.GreenDice;
import game.dice.MagentaDice;
import game.dice.RedDice;
import game.dice.YellowDice;

public class GameBoard {
        
    private Player Player1;
    private Player Player2;
    private GameStatus status;
    private RedDice red;
    private GreenDice green;
    private BlueDice blue;
    private MagentaDice magenta;
    private YellowDice yellow;
    private ArcanePrism white;
    private Dice[] dice = new Dice[6];

    public ArcanePrism getWhite() {
        return white;
    }

    public GameBoard(){
        
    }
    
    public GameBoard(Player p1, Player p2, GameStatus s, RedDice r, GreenDice g, BlueDice b, MagentaDice m, YellowDice y, ArcanePrism w){
        this.Player1 = p1;
        p1.setPlayerStatus(PlayerStatus.ACTIVE);
        this.Player2 = p2;
        p2.setPlayerStatus(PlayerStatus.PASSIVE);;
        this.status = s;
        this.red = r;
        this.green = g;
        this.blue = b;
        this.magenta = m;
        this.yellow = y;
        this.white = w;
        dice[0] = red;
        dice[1] = green;
        dice[2] = blue;
        dice[3] = magenta;
        dice[4] = yellow;
        dice[5] = white;
    }

    public void roll(){
        if(!red.isForgotten() && !red.isUsed()){
            red.roll();
        }
        if(!green.isForgotten() && !green.isUsed()){
            green.roll();
        }
        if(!blue.isForgotten() && !blue.isUsed()){
            blue.roll();
        }
        if(!magenta.isForgotten() && !magenta.isUsed()){
            magenta.roll();
        }
        if(!yellow.isForgotten() && !yellow.isUsed()){
            yellow.roll();
        }
        if(!white.isForgotten() && !white.isUsed()){
            white.roll();
        }
    }

    public Player getPlayer1() {
        return Player1;
    }

    public Player getPlayer2() {
        return Player2;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    //  TODO: what if he wants to setDice fel already existing dice ely fel gameBOARD
    public Dice[] getDice(){
        return dice;
    }
    public BlueDice getBlue() {
        return blue;
    }
    public GreenDice getGreen() {
        return green;
    }
    public MagentaDice getMagenta() {
        return magenta;
    }
    public RedDice getRed() {
        return red;
    }
    public YellowDice getYellow() {
        return yellow;
    }
}
