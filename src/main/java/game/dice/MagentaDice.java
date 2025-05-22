package game.dice;

import game.engine.Realm;


public class MagentaDice extends Dice{
   public Realm oldRealm = realm.MAGENTA;
    public MagentaDice(int value,Realm oldRealm){
        super();
        this.value = value;
        this.oldRealm = oldRealm;
        realm = Realm.MAGENTA;
    }
    @Override
    public int getOldRealm ()
    {return oldRealm.ordinal();}
    public MagentaDice(){
        super();
        realm = Realm.MAGENTA;
    }

    public MagentaDice(int x){
        super(x);
        realm = Realm.MAGENTA;
    }
}
