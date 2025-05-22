package game.dice;

import game.engine.Realm;
import game.gui.SuperMarketController;

public class YellowDice extends Dice{
   public Realm oldRealm =Realm.YELLOW;
    public YellowDice(int value,Realm oldRealm){
        this.value = value;
        this.oldRealm = oldRealm;
        realm = Realm.YELLOW;
    }
    @Override
public int getOldRealm ()
{return oldRealm.ordinal();}
    public YellowDice(){
        super();
        realm = Realm.YELLOW;
    }

    public YellowDice(int x){
        super(x);
        realm = Realm.YELLOW;
    }

}
