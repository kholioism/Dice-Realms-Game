package game.collectibles;

public class ArcaneBoost extends Power {
    private int count = 0;
    
    public void setcount(int x){
        this.count = x;
    }

    public int getcount(){
        return count;
    }
}
