// package game.gui;

// import game.creatures.Creature;
// import game.dice.Dice;
// import game.engine.CLIGameController;
// import game.engine.Move;
// import game.engine.Player;

// public class GuiGameControl extends CLIGameController {
//      boolean timeWarppicked = false;
//     // static CLIGameController c;
//     PlayerSequence theSequence = PlayerSequence.rollDice;
//     int turn;

//     // public GUIGameController(){
//     // c = new CLIGameController();
//     // }

//     public boolean ifDiePossible(Player p, Dice d) {
//         Move[] m = getPossibleMovesForADie(p, d);
//         if (m.length > 0 && !(d.isForgotten()) && (!d.isUsed())) {
//             return true;
//         } else {
//             return false;
//         }
//     }
// //TODO:b3d el rolldice 3latool call this method
//     public void incrementPlayerSequence() {
//         if (theSequence == PlayerSequence.rollDice) {
//             if (getGameBoard().getPlayer1().getScoreSheet().timeWarpAvailable())
//                 if (timeWarppicked) {
//                     theSequence = PlayerSequence.timeWarp;
//                 } else if (getAllPossibleMoves(getGameBoard().getPlayer1()).length > 0
//                         && theSequence == PlayerSequence.pickDice) {
//                     theSequence = PlayerSequence.makeMove;
//                 } else

//                     theSequence = PlayerSequence.waitingforpassiveturn;

//         } else if (theSequence == PlayerSequence.dicePicked) {
//             theSequence = PlayerSequence.makeMove; // TODO:whenever u make the move fel akher change the enum
//         } else if (theSequence == PlayerSequence.moveMade && getNotBonus() == true
//                 && (turn == 1 || turn == 2 || turn == 3)) {
//             theSequence = PlayerSequence.waitingforpassiveturn;
//             turn = 0;

//         }

//         // else if(theSequence==PlayerSequence.waitingforpassiveturn && turn == 0 )//he
//         // has to be passive anndddd
//         // {
//         // theSequence = PlayerSequence.arcaneBoost;

//         // }
//         // TODO:add this condition lama yekoon ekhtar eno ye3mel arcane boost
//         // TODO:after arcane boost y set the thing back to roll dice 3latool?
//     }

// //  public void updateRedScoreScheet(Creature c){
// //   //TODO:  all the set texts ely hma 3amlenha

// //  }
// //  public void updateGreenScoreScheet(Creature c){
// //     //TODO:  all the set texts ely hma 3amlenha
  
// //    }
// //    public void updateBlueScoreScheet(Creature c){
// //     //TODO:  all the set texts ely hma 3amlenha
  
// //    }
// //    public void updateMagentaScoreScheet(Creature c){
// //     //TODO:  all the set texts ely hma 3amlenha
  
// //    }
// //    public void updateYellowScoreScheet(Creature c){
// //     //TODO:  all the set texts ely hma 3amlenha
  
// //    }






   
//    public void endTurn(){
//     getGameBoard().getStatus().endTurn();
//     turn=3;
//    }

// @Override
//     public Dice[] rollDice() {
//         Dice[] d = rollDice();
//         return d;
//     }

//     public void selectRed(Player player) {
//         makeMove(player, new Move(getAllDice()[0], player.getScoreSheet().getDragon()));
//         getAllDice()[0].use();

//     }

//     public boolean selectGreen(Player player) {
//         boolean f = makeMove(player, new Move(getAllDice()[1], player.getScoreSheet().getGuardians()));
//         if (f) {
//             getAllDice()[1].use();
//         }
//         return f;
//     }

//     public boolean selectBlue(Player player) {
//         boolean f = makeMove(player, new Move(getAllDice()[2], player.getScoreSheet().getHydra()));
//         if (f) {
//             getAllDice()[2].use();
//         }
//         return f;
//     }

//     public boolean selectMagenta(Player player) {
//         boolean f = makeMove(player, new Move(getAllDice()[3], player.getScoreSheet().getPhoenix()));
//         if (f) {
//             getAllDice()[3].use();
//         }
//         return f;
//     }

//     public boolean selectYellow(Player player) {
//         boolean f = makeMove(player, new Move(getAllDice()[4], player.getScoreSheet().getLion()));
//         if (f) {
//             getAllDice()[4].use();
//         }
//         return f;
//     }

//     public void selectArcane(Player player, Creature creature) {
//         makeMove(player, new Move(getAllDice()[5], creature));
//         getAllDice()[5].use();
//     }

// }
