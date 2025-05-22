package game.dice;

//import java.util.Scanner;

import game.creatures.DragonNumber;
import game.engine.Realm;

//import game.exceptions.PlayerActionException;

public class RedDice extends Dice {

    private DragonNumber d;
   public Realm oldRealm = Realm.RED ;

public RedDice(int value,Realm oldRealm){
    super();
    this.value = value;
    this.oldRealm = oldRealm;
    realm = Realm.RED;

}

@Override
public int getOldRealm ()
{return oldRealm.ordinal();}


    public RedDice() {
        super();
        realm = Realm.RED;

    }

    public RedDice(RedDice r){
        super(r.getValue());
        this.d = null;
    }

    public DragonNumber getDragonNumber() {
        return d;
    }

    public int getDragonNumberInteger(){
        if(d == null){
            return 0;
        }
        if (d.equals( DragonNumber.Dragon1)){
            return 1;
        }
        else if(d.equals( DragonNumber.Dragon2)){
            return 2;
        }
        else if(d.equals( DragonNumber.Dragon3)){
            return 3;
        }
        else if(d.equals( DragonNumber.Dragon4)) {
            return 4;
        }
        else 
        return 0;

    }

    public RedDice(int x) {
        super(x);
        realm = Realm.RED;
    }

    public RedDice(int x, int dd) {
        this(x);
        switch (dd) {
            case 1:
                d = DragonNumber.Dragon1;
                break;
            case 2:
                d = DragonNumber.Dragon2;
                break;
            case 3:
                d = DragonNumber.Dragon3;
                break;
            case 4:
                d = DragonNumber.Dragon4;
                break;
        }
    }

    public void selectsDragon(int i) {
        // try {
        // if (i != 1 && i != 2 && i != 3 && i != 4)
        // throw new PlayerActionException();// ????? malha deeh
        // else {
        switch (i) {
            case 1:
                d = DragonNumber.Dragon1;
                break;
            case 2:
                d = DragonNumber.Dragon2;
                break;
            case 3:
                d = DragonNumber.Dragon3;
                break;
            case 4:
                d = DragonNumber.Dragon4;
                break;
            case 0:
                d = null;
                break;
            default: d = null;    

            // }
            // }
        }
        // catch (PlayerActionException e) {
        // System.out.println("Invalid dragon number. Please enter a number between 1
        // and 4.");
        // Scanner sc = new Scanner(System.in);
        // int x = sc.nextInt();
        // sc.close();
        // selectsDragon(x);
        // }
    }
}
