package game.dice;

import game.engine.Realm;


public class GreenDice extends Dice{
  public  Realm oldRealm = realm.GREEN;
    public GreenDice(int value,Realm oldRealm){
        super();
        this.value = value;
        this.oldRealm = oldRealm;
        realm = Realm.GREEN;
    }
    
    private int whiteDiceValue;

    public GreenDice(){
        super();
        realm = Realm.GREEN;
    }
    @Override
    public int getOldRealm ()
    {return oldRealm.ordinal();}
    public GreenDice(int x){
        super(x);
        realm = Realm.GREEN;
    }

    @Override
    public int getValue(){
        return value+whiteDiceValue;
    }

    public void setWhite(ArcanePrism a){
        this.whiteDiceValue = a.value;
    }

    public void setWhite(int x){
        this.whiteDiceValue = x;
    }

    public int getWhiteValue() {
        return whiteDiceValue;
    }
    
    public int getGreenValue(){
        return value;
    }
    @Override
    public String toString() {
        return "" + realm + " " + (getGreenValue());
    }
}