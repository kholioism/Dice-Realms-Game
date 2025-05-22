package game.engine;

import game.creatures.*;
import game.dice.Dice;
import game.dice.RedDice;

public class Move implements Comparable<Move>{

    private Creature target;
    private Dice dice;
    
    public Move(Dice d, Creature c){
        this.dice = d;
        this.target = c;
    }

    public Move(Move m){
        this.dice = m.getDice();
        this.target = m.getTarget();
    }

    public Creature getTarget() {
        return target;
    }

    public void setTarget(Creature target) {
        this.target = target;
    }

    public Dice getDice() {
        return dice;
    }
    
    
    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public int checkPoints(){
        switch(getTarget().getClass().getSimpleName()){
            case "Dragon":
                return ((Dragon)getTarget()).checkPoints(this);
            case "Guardians":
                return ((Guardians)getTarget()).checkPoints();
            case "Hydra":
                return ((Hydra)getTarget()).checkPoints();
            case "Phoenix":
            case "Lion":
                return getDice().getValue();
            default:
                return 0;
        }
    }

    public int checkBonus(){
        return getTarget().checkBonus(this);
    }

    @Override
    public int compareTo(Move m) {
        Realm[] r = Realm.values();
        int x = 0;
        int y = 0;
        for (int i = 0; i < r.length; i++) {
            if(this.getDice().getRealm()==r[i]) x = i;
        }
        for (int i = 0; i < r.length; i++) {
            if(m.getDice().getRealm()==r[i]) y = i;
        }
        if(x!=y) return (x-y);
        if(this.getDice().getValue()!=m.getDice().getValue()) return this.getDice().getValue()-m.getDice().getValue();
        if(!(this.getDice() instanceof RedDice)) 
        return 0;
        DragonNumber[] d = DragonNumber.values();
        RedDice w = (RedDice)this.getDice();
        RedDice a = (RedDice)m.getDice();
        for (int i = 0; i < d.length; i++) {
            if(w.getDragonNumber()==d[i]) x = i;
        }
        for (int i = 0; i < d.length; i++) {
            if(a.getDragonNumber()==d[i]) y = i;
        }
        return x-y;
    }
    
    @Override
    public String toString() {
        String s = "";
        if (getDice() instanceof RedDice) {
            RedDice r = (RedDice)getDice();
            s += r.getDragonNumber() + " " + r.getValue();
        }
        else{
            s += getTarget().getClass().getSimpleName() + " " + getDice().getValue();
        }
        return s;
    }
    
}
