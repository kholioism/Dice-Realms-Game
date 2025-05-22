package game.collectibles;

public abstract class Reward implements Comparable<Reward> {

    private boolean obtained; 
    private boolean used; 
    //private RewardType type; //value not used

    public abstract RewardType getRewardType();

    public boolean isObtained() {
        return obtained;
    }
    
    public boolean isUsed() {
        return used;
    }

    public void setObtained() {
        this.obtained = true;
    }

    public void setUsed() {
        this.used = true;
    }

    @Override
    public int compareTo(Reward r) {
        if (r.getRewardType().ordinal() - this.getRewardType().ordinal()!=0) {
            return r.getRewardType().ordinal() - this.getRewardType().ordinal();
        }
        if (r.getRewardType()==RewardType.BONUS && this.getRewardType()==RewardType.BONUS) {
            return ((Bonus)r).getColor().ordinal()-((Bonus)this).getColor().ordinal();
        }
        return 0;
    }
}
