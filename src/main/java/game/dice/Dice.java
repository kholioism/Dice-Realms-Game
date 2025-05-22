package game.dice;
import java.util.Random;

import game.engine.Move;
import game.engine.Realm;


public abstract class Dice {
    
    protected int value;
    protected boolean forgotten;
    protected boolean used;
    protected boolean arcaneBoosted;
    protected Realm realm;
    protected Realm oldRealm;
    
    //TODO: IF GAVE DICE WITH WRONG VALUE
    public Dice(int x){
        this.value = x;
    }

    public int getOldRealm(){
        return 1;

    }

    public Dice(){
        
    }
    
    //TODO: THROWS EXCEPTION WITH WRONG DICE ROLL CUZ I WILL CREATE A DICEWITH WRONG VALUE
    public void setValue(int x) {
        this.value = x;
    }
    
    public void roll(){
        Random random = new Random(); 
        value = random.nextInt(6) + 1;
    }

    public int getValue(){
        return value;
    }

    public void use(){
        used = true;
    }
    
    public void forget(){
        forgotten = true;
    }

    public void arcaneBoosted() {
        arcaneBoosted = true;
    }

    public void reset(){
        used = false;
        forgotten = false;
        arcaneBoosted = false;
    }

    void ifMoveIsPossible(){
            
    }

    public boolean isForgotten() {
        return forgotten;
    }

    public boolean isUsed() {
        return used;
    }

    public boolean isArcaneBoosted() {
        return arcaneBoosted;
    }

    public Realm getRealm(){
        return this.realm;
    }

    @Override
    public String toString() {
        return "" + realm + " " + value;
    }

    public void unarcane() {
        arcaneBoosted = false;
    }

    public Move[] getPossibleMovesForADie() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPossibleMovesForADie'");
    }
}
