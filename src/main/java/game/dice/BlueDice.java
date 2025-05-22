package game.dice;

import game.engine.Realm;



public class BlueDice extends Dice {
   public  Realm oldRealm = realm.BLUE;
   
    public BlueDice(int value,Realm oldRealm){
        this.value = value;
        this.oldRealm = oldRealm;
        realm = Realm.BLUE;
    }
    @Override
    public int getOldRealm ()
    {return oldRealm.ordinal();}
    public BlueDice(){
        super();
        realm = Realm.BLUE;
        }
    
    public BlueDice(int x){
        super(x);
        realm = Realm.BLUE;
    }
}
   
    


