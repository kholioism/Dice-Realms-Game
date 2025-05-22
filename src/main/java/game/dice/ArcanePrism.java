package game.dice;

import game.engine.Realm;

public class ArcanePrism extends Dice{
   public Realm oldRealm = realm.WHITE;

    public ArcanePrism(int value,Realm oldRealm){
        this.value = value;
        this.oldRealm = oldRealm;
        realm = Realm.WHITE;

    }
    
    @Override
    public int getOldRealm ()
    {return oldRealm.ordinal();}
    public ArcanePrism(){
        super();
        realm = Realm.WHITE;
    }
    public ArcanePrism(int x){
        super(x);
        realm = Realm.WHITE;

    }

}