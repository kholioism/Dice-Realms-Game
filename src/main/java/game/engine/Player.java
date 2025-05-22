package game.engine;

import game.dice.Dice;

public class Player {
    
    private String name;
    private PlayerStatus status;
    private GameScore gameScore = new GameScore();
    private ScoreSheet scoreSheet = new ScoreSheet();
    private Dice[] usedDice;
    //private int ElementalCrest;

    public Player(GameScore score, ScoreSheet sheet){
        this.gameScore = score;
        this.scoreSheet = sheet;
    }    

    protected void setPlayerStatus(PlayerStatus pp){
        status = pp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Player getPlayer(){
        return this;
    }

    public PlayerStatus getPlayerStatus(){
        return this.status;
    }

    public void switchPlayerStatus(){
        if(status == PlayerStatus.ACTIVE){
            status = PlayerStatus.PASSIVE;
        }
        else{
            status = PlayerStatus.ACTIVE;
        }
    }

    public GameScore getGameScore() {
        return gameScore;
    }

    public void setGameScore(GameScore gameScore) {
        this.gameScore = gameScore;
    }

    public ScoreSheet getScoreSheet() {
        return scoreSheet;
    }

    public void setScoreSheet(ScoreSheet scoreSheet) {
        this.scoreSheet = scoreSheet;
    }

    public int getElementalCrest() {
        return scoreSheet.getElementalCrest();
    }

    public void incrementElementalCrest(){
        scoreSheet.incrementElementalCrest();
    }

    public Dice[] getUsedDice() {
        return usedDice;
    }
}



