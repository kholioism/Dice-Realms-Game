package game.engine;

public class GameStatus {

    private Status state;
    private TurnShift turn;
    private int roundNumber = 1;

    public GameStatus(){
        this.state = Status.initialization;
        this.turn = TurnShift.P1AT1;
        this.roundNumber = 1;
    }

    public void nextTurn(){
        switch (turn) {
            case P1AT1:
                turn = TurnShift.P1AT2;
                break;

            case P1AT2:
                turn = TurnShift.P1AT3;
                break;

            case P1AT3:
                turn = TurnShift.P2PT;
                break;

            case P2PT:
                turn = TurnShift.P2AT1;
                break;

            case P2AT1:
                turn = TurnShift.P2AT2;
                break;

            case P2AT2:
                turn = TurnShift.P2AT3;
                break;

            case P2AT3:
                turn = TurnShift.P1PT;
                break;

            case P1PT:
                //turn=TurnShift.P1AT1;
                nextRound();
                break;

            default:
                break;
        }
    }

    public void nextState(){
        switch (state) {
            case initialization:
                state = Status.ongoing;
                break;

            case ongoing:
                state = Status.calculation;
                break;

            case calculation:
                state = Status.result;
                break;
        
            default:
                break;
        }
    }

    public void nextRound(){
        roundNumber++;
        turn = TurnShift.P1AT1;
    }

    public void endTurn(){
        if(turn == TurnShift.P1AT1 || turn == TurnShift.P1AT2 || turn == TurnShift.P1AT3 ){
            turn = TurnShift.P2PT;
        }
        else if(turn == TurnShift.P2PT)
         turn = TurnShift.P2AT1;

         else if(turn == TurnShift.P1PT)
         turn = TurnShift.P1AT1;
        else{
            turn = TurnShift.P1PT;
        }
    }

    public Status getState() {
        return state;
    }

    public TurnShift getTurn() {
        return turn;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

}
