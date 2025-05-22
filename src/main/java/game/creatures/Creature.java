package game.creatures;

import game.collectibles.Reward;
import game.dice.Dice;
import game.engine.Move;
import game.exceptions.InvalidMoveException;
import game.exceptions.PlayerActionException;

public abstract class Creature {

    public abstract String toString();

    public abstract boolean makeMove(Dice d) throws PlayerActionException, InvalidMoveException;

    public abstract Move[] getPossibleMovesForADie(Dice d);

    public abstract Reward[] getReward();

    public abstract int getScore();

    public abstract int checkBonus(Move m);
}
