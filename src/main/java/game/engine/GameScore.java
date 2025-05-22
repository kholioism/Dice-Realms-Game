package game.engine;

public class GameScore {

    private final String[] guide = { "RED", "GREEN", "BLUE", "MAGENTA", "YELLOW" };
    private int[] score = new int[5];
    private int elementalcrestsbrdo = 0;

    public int getTotal() {
        int total = 0;
        // theRealmScores[0] = p.getGameScore().getRedRealmScore();
        // theRealmScores[1] = p.getGameScore().getGreenRealmScore();
        // theRealmScores[2] = p.getGameScore().getBlueRealmScore();
        // theRealmScores[3] = p.getGameScore().getMagentaRealmScore();
        // theRealmScores[4] = p.getGameScore().getYellowRealmScore();
        int smallestSoFar = score[0];
        for (int j = 1; j < score.length; j++) {
            if (score[j]<smallestSoFar) {
                smallestSoFar = score[j];
            }
        }
        for (int i = 0; i < guide.length; i++) {
            total += score[i];
        }
        return total + (smallestSoFar*elementalcrestsbrdo);
    }

    public int[] getScore() {
        return score;
    }

    public int getRedRealmScore() {
        return score[0];
    }

    public int getGreenRealmScore() {
        return score[1];
    }

    public int getBlueRealmScore() {
        return score[2];
    }

    public int getMagentaRealmScore() {
        return score[3];
    }

    public int getYellowRealmScore() {
        return score[4];
    }

    public void setRedRealmScore(int x) {
        score[0] = x;
    }

    public void setGreenRealmScore(int x) {
        score[1] = x;
    }

    public void setBlueRealmScore(int x) {
        score[2] = x;
    }

    public void setMagentaRealmScore(int x) {
        score[3] = x;
    }

    public void setYellowRealmScore(int x) {
        score[4] = x;
    }

    public void incrementElementalCrest()
    { elementalcrestsbrdo++;}

    
    public String toString() {

        String s = "+---------+-------+\n| Realm   | Score |\n+---------+-------+\n";
        for (int i = 0; i < guide.length; i++) {
            s += "| ";
            switch (i) {
                case 0:
                if ((getRedRealmScore())<10) {
                    s += "Red     |" + "     " + getRedRealmScore() + " |\n";
                }
                else {
                    s += "Red     |" + "     " + getRedRealmScore() + "|\n";
                }
                    break;
                case 1:
                if ((getGreenRealmScore())<10) {
                    s += "Green   |" + "     " + getGreenRealmScore() + " |\n";
                }
                else {
                    s += "Green   |" + "     " + getGreenRealmScore() + "|\n";
                }
                    break;
                case 2:
                if ((getBlueRealmScore())<10) {
                    s += "Blue    |" + "     " + getBlueRealmScore() + " |\n";
                }
                else {
                    s += "Blue    |" + "     " + getBlueRealmScore() + "|\n";
                }
                    break;
                case 3:
                if ((getMagentaRealmScore())<10) {
                    s += "Magenta |" + "     " + getMagentaRealmScore() + " |\n";
                }
                else {
                    s += "Magenta |" + "     " + getMagentaRealmScore() + "|\n";
                }
                    break;
                case 4:
                if ((getYellowRealmScore())<10) {
                    s += "Yellow  |" + "     " + getYellowRealmScore() + " |\n";
                }
                else {
                    s += "Yellow  |" + "     " + getYellowRealmScore() + "|\n";
                }
                    break;
            }
        }
        return s;
    }
}
