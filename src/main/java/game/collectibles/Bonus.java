package game.collectibles;

import game.engine.Realm;

public class Bonus extends Reward {
    private Realm color;

    public RewardType getRewardType(){
        return RewardType.BONUS;
    }

    public Realm getColor() {
        return color;
    }

    public Bonus(Realm color){
        this.color = color;
    }

}
