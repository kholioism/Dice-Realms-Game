package game.engine;

import java.util.*;

import game.collectibles.ArcaneBoost;
import game.collectibles.Bonus;
import game.collectibles.Reward;
import game.collectibles.TimeWarp;
import game.dice.*;
import game.exceptions.InvalidMoveException;
import game.exceptions.PlayerActionException;
import game.creatures.*;
import game.exceptions.TimeWarpException;

import java.util.stream.Stream;

public class CLIGameController extends GameController {

    private final GameBoard board;
    public boolean notBonus;

    public boolean getNotBonus(){
        return notBonus;
    }

    public CLIGameController() {
        RedDice red = new RedDice();
        GreenDice green = new GreenDice();
        BlueDice blue = new BlueDice();
        MagentaDice magenta = new MagentaDice();
        YellowDice yellow = new YellowDice();
        ArcanePrism white = new ArcanePrism();

        // initialization of scoresheets for merlin and gandalf
        ScoreSheet MerlinSS = new ScoreSheet();
        ScoreSheet GandalfSS = new ScoreSheet();

        // initialization of gamescore for merlin and gandalf
        GameScore MerlinGS = new GameScore();
        GameScore GandalfGS = new GameScore();

        // initialization of players merlin and gandalf
        Player Merlin = new Player(MerlinGS, MerlinSS);
        Player Gandalf = new Player(GandalfGS, GandalfSS);

        // initialization of status
        GameStatus status = new GameStatus();

        //
        board = new GameBoard(Merlin, Gandalf, status, red, green, blue, magenta, yellow, white);
        notBonus = true;
    }



    @Override
    public void startGame() {
        Scanner sc = new Scanner(System.in);
        String f = "";
        do {
            System.out.println("Would you like to play versus another player or the AI?");
            System.out.println("1.PvP");
            System.out.println("2.AI");
            f = sc.nextLine();
            if (f==null || !isNumeric(f) || (Integer.parseInt(f) != 2 && Integer.parseInt(f) != 1)) {
                System.out.println("Invalid input. Please choose a correct input.");
            } else {
                break;
            }
        } while (true);
        if (Integer.parseInt(f) == 1) {
            startGame("Hello");
        } else {
            startGame(1);
        }
    }

    // AI mode
    public void startGame(int ignoredStart) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name");
        String name1 = sc.nextLine();
        while (name1.isEmpty() || (name1.charAt(0) >= '0' && name1.charAt(0) <= '9')) {
            System.out.println("Please enter a valid name.");
            name1 = sc.nextLine();
        }
        board.getPlayer1().setName(name1);
        board.getPlayer2().setName("AI");

        for (int i = 0; i < 6; i++) {
            System.out.println("ROUND " + getGameStatus().getRoundNumber());
            System.out.println(board.getPlayer1().getName() + "'s turn");
            // System.out.println(getActivePlayer().getScoreSheet().getTurnRewards()[i]);
            handleReward(board.getPlayer1().getScoreSheet().getTurnRewards()[i], board.getPlayer1());
            for (int k = 0; k < 3; k++) {
                if (getAvailableDice().length == 0
                        && getPossibleMovesForAvailableDice(board.getPlayer1()).length == 0) {
                    getGameStatus().endTurn();
                    break;
                }
                System.out.println(board.getPlayer1().getName() + " YOU NEED TO PLEASE Press enter to roll dice");
                sc.nextLine();
                rollDice();
                Dice[] d = getAvailableDice();
                for (int j = 0; j < getAvailableDice().length; j++) {
                    System.out.println((j + 1) + ". " + d[j]);
                }
                String c;

                while (board.getPlayer1().getScoreSheet().timeWarpAvailable()) {
                    System.out.println("u have " + board.getPlayer1().getScoreSheet().getTimeWarpCount()
                            + " timewarps, Do you want to reroll? Enter Y for yes or N for no.");
                    c = sc.nextLine().toUpperCase();
                    while (c == null || (!c.equals("Y") && !c.equals("N"))) {
                        System.out.println("Invalid input. Please enter Y for yes or N for no.");
                        c = sc.nextLine().toUpperCase();
                    }
                    if (c.equals("Y")) {
                        board.getPlayer1().getScoreSheet().useTimeWarp();
                        rollDice();
                        for (int j = 0; j < getAvailableDice().length; j++) {
                            System.out.println((j + 1) + ". " + d[j]);
                        }
                    } else {
                        break;
                    }
                }
                // Reward[] reward = new Reward[0];2
                if (getPossibleMovesForAvailableDice(board.getPlayer1()).length > 0) {
                    boolean moveMade = false;
                    String turnnumber = "";
                    switch (board.getStatus().getTurn()) {
                        case P1AT1:
                        case P2AT1:
                            System.out.println(board.getStatus().getTurn());
                            turnnumber = "first";
                            break;
                        case P1AT2:
                        case P2AT2:
                            turnnumber = "second";
                            System.out.println(board.getStatus().getTurn());
                            break;
                        case P1AT3:
                        case P2AT3:
                            turnnumber = "third";
                            System.out.println(board.getStatus().getTurn());
                            break;
                        default:
                            System.out.println(board.getStatus().getTurn() + "bayez");
                            break;
                    }
                    String e;
                    Move[] m;
                    Dice dice;
                    while (!moveMade) {
                        System.out.println(board.getPlayer1().getName() + ", play your " + turnnumber + " turn.");
                        System.out.println("Choose a dice from 1 to " + (getAvailableDice().length));
                        String x = sc.nextLine();
                        while (!isNumeric(x) || Integer.parseInt(x) < 1
                                || Integer.parseInt(x) > getAvailableDice().length) {
                            System.out.println("Invalid input. Enter a number from 1 to " + (d.length) + ".");
                            x = sc.nextLine();
                        }
                        dice = getAvailableDice()[Integer.parseInt(x) - 1];
                        m = getPossibleMovesForADie(board.getPlayer1(), dice);
                        if (m.length == 1) {
                            if (notBonus) {
                                dice.use();
                            }
                            moveMade = makeMove(board.getPlayer1(), m[0]);
                            // reward = getReward(getActivePlayer(), m[0]);
                            Creature targetedCreature = m[0].getTarget();
                            System.out.println(targetedCreature);
                        } else if (m.length == 0) {
                            System.out.println("No available moves for selected dice. Choose another dice.");
                        } else {
                            System.out.println("Choose a move 1-" + m.length);
                            for (int j = 0; j < m.length; j++) {
                                System.out.println((j + 1) + ". " + m[j]);
                            }
                            e = sc.nextLine();//
                            while (!isNumeric(e) || Integer.parseInt(e) < 1 || Integer.parseInt(e) > m.length) {
                                System.out
                                        .println("Invalid input. Please choose a number between 1 and " + m.length);
                                e = sc.nextLine();
                            }
                            if (notBonus) {
                                dice.use();
                            }
                            System.out.println("hi u have used the dice u make move next");
                            if (dice instanceof ArcanePrism) {
                                Creature h = m[Integer.parseInt(e) - 1].getTarget();
                                boolean red = false;
                                if (h instanceof Dragon) {
                                    RedDice r = (RedDice) m[Integer.parseInt(e) - 1].getDice();
                                    RedDice actualDice = new RedDice(dice.getValue());
                                    actualDice.selectsDragon(r.getDragonNumberInteger());
                                    red = true;
                                    moveMade = makeMove(board.getPlayer1(),
                                            new Move(actualDice, m[Integer.parseInt(e) - 1].getTarget()));
                                }
                                if (!red) {
                                    moveMade = makeMove(board.getPlayer1(),
                                            new Move(dice, m[Integer.parseInt(e) - 1].getTarget()));
                                }
                                Creature targetedCreature = m[Integer.parseInt(e) - 1].getTarget();
                                System.out.println(targetedCreature);
                            } else {
                                moveMade = makeMove(board.getPlayer1(), m[Integer.parseInt(e) - 1]);
                                Creature targetedCreature = m[Integer.parseInt(e) - 1].getTarget();
                                System.out.println(targetedCreature);
                            }
                            // reward = getReward(getActivePlayer(), m[Integer.parseInt(e) - 1]);
                            // System.out.println("the bonus u just claimed is of length" + reward.length);
                            // // TODO:remove
                            // Creature targetedCreature = m[Integer.parseInt(e) - 1].getTarget();
                            // System.out.println(targetedCreature);
                            // }
                            // if (reward.length == 2) {
                            // if (reward[0].compareTo(reward[1]) > 0) {
                            // handleReward(reward[0], getActivePlayer());
                            // handleReward(reward[1], getActivePlayer());// TODO: el mshkela hena
                            // } else {
                            // handleReward(reward[1], getActivePlayer());
                            // handleReward(reward[0], getActivePlayer());// TODO el mshkela hena
                            // }
                            // } else if (reward.length == 1) {
                            // handleReward(reward[0], getActivePlayer());
                            // }
                        }

                        // board.getStatus().endTurn();
                        // break;

                    }
                } else {
                    board.getStatus().endTurn();
                    break;
                }

                board.getStatus().nextTurn();

            }
            for (int j = 0; j < getAllDice().length; j++) {
                if (!getAllDice()[j].isForgotten() && !getAllDice()[j].isUsed()) {
                    getAllDice()[j].forget();
                }
            }
            // Reward[] reward = new Reward[0];
            if (getPossibleMovesForForgottenDice(board.getPlayer2()).length > 0) {
                boolean moveMade = false;
                System.out.println("Time for " + board.getPlayer2().getName() + "'s passive turn");
                System.out.println(board.getStatus().getTurn());
                while (!moveMade) {
                    for (int z = 0; z < getForgottenRealmDice().length; z++) {
                        System.out.println((z + 1) + ". " + getForgottenRealmDice()[z]);
                    }
                    System.out.println("Time for the AI's move. Press enter to see the move.");
                    sc.nextLine();
                    Move chosen = moveChooserPassive(i+1);

                    moveMade = makeMoveAI(chosen);
                    Creature targetedCreature = chosen.getTarget();
                    System.out.println(targetedCreature);
                }
            }
            else {
                System.out.println("No available moves for passive player.");
                // board.getStatus().nextTurn();
            }

            Move[] m;
            String n;
            board.getStatus().endTurn();
            String e;
            // reward = new Reward[0];
            while (board.getPlayer1().getScoreSheet().arcaneBoostAvailable()
                    && getPossibleArcaneMoves(board.getPlayer1()).length > 0) {
                System.out.println(
                        board.getPlayer1().getName()
                                + ", do you want to use ArcaneBoost? Enter Y for yes or N for no.");
                e = sc.nextLine().toUpperCase();
                while (e == null || (!e.equals("Y") && !e.equals("N")) ) {
                    System.out.println("Invalid input. Please enter Y for yes or N for no.");
                    e = sc.nextLine().toUpperCase();
                }
                if (e.equals("Y")) {
                    board.getPlayer1().getScoreSheet().useArcaneBoost();
                    System.out.println("ARCANE BOOST TIME");
                    for (int j = 0; j < getArcaneBoostDice().length; j++) {
                        System.out.println((j + 1) + ". " + getArcaneBoostDice()[j]);
                    }
                    boolean moveMade = false;
                    while (!moveMade) {
                        System.out.println("Choose a dice from 1 to " + (getArcaneBoostDice().length));
                        String x = sc.nextLine();
                        while (!isNumeric(x) || Integer.parseInt(x) < 1
                                || Integer.parseInt(x) > getArcaneBoostDice().length) {
                            System.out.println("Invalid input. Enter a number from 1 to "
                                    + (getArcaneBoostDice().length) + ".");
                            x = sc.nextLine();
                        }
                        Dice dice = getArcaneBoostDice()[Integer.parseInt(x) - 1];
                        m = getPossibleMovesForADie(board.getPlayer1(), dice);
                        if (m.length == 1) {
                            if (notBonus) {
                                dice.use();
                            }
                            moveMade = makeMove(board.getPlayer1(),
                                    m[0]);
                            // reward = getReward(getActivePlayer(), m[0]);
                            Creature targetedCreature = m[0].getTarget();
                            System.out.println(targetedCreature);
                        } else if (m.length == 0) {
                            System.out.println("No available moves for selected dice. Choose another dice.");
                        } else {
                            System.out.println("Choose a move 1-" + m.length);
                            for (int j = 0; j < m.length; j++) {
                                System.out.println((j + 1) + ". " + m[j]);
                            }
                            n = sc.nextLine();
                            while (!isNumeric(n) || Integer.parseInt(n) < 1 || Integer.parseInt(n) > m.length) {
                                System.out
                                        .println("Invalid input. Please choose a number between 1 and " + m.length);
                                n = sc.nextLine();
                            }
                            if (notBonus) {
                                dice.use();
                            }
                            if (dice instanceof ArcanePrism) {
                                Creature h = m[Integer.parseInt(n) - 1].getTarget();
                                boolean red = false;
                                if (h instanceof Dragon) {
                                    RedDice r = (RedDice) m[Integer.parseInt(n) - 1].getDice();
                                    RedDice actualDice = new RedDice(dice.getValue());
                                    actualDice.selectsDragon(r.getDragonNumberInteger());
                                    red = true;
                                    moveMade = makeMove(board.getPlayer1(),
                                            new Move(actualDice, m[Integer.parseInt(n) - 1].getTarget()));
                                }
                                if (!red) {
                                    moveMade = makeMove(board.getPlayer1(),
                                            new Move(dice, m[Integer.parseInt(n) - 1].getTarget()));
                                }
                                Creature targetedCreature = m[Integer.parseInt(n) - 1].getTarget();
                                System.out.println(targetedCreature);
                            } else {
                                moveMade = makeMove(board.getPlayer1(), m[Integer.parseInt(n) - 1]);
                                Creature targetedCreature = m[Integer.parseInt(n) - 1].getTarget();
                                System.out.println(targetedCreature);
                            }
                            // reward = getReward(getActivePlayer(), m[Integer.parseInt(n) - 1]);
                            // Creature targetedCreature = m[Integer.parseInt(n) - 1].getTarget();
                            // System.out.println(targetedCreature);
                        }
                        // if (reward.length == 2) {
                        // if (reward[0].compareTo(reward[1]) > 0) {
                        // handleReward(reward[0], getActivePlayer());
                        // handleReward(reward[1], getActivePlayer());
                        // } else {
                        // handleReward(reward[1], getActivePlayer());
                        // handleReward(reward[0], getActivePlayer());
                        // }
                        // } else if (reward.length == 1) {
                        // handleReward(reward[0], getActivePlayer());
                        // }
                    }

                } else {
                    break;
                }
            }

            for (int j = 0; j < getAllDice().length; j++) {
                getAllDice()[i].unarcane();
            }

            Dice dice;
            // reward = new Reward[0];
            //TODO:arcane boost decision
            while (board.getPlayer2().getScoreSheet().arcaneBoostAvailable()
                    && getPossibleArcaneMovesAI(board.getPlayer2()).length > 0) {
                if (getPossibleArcaneMovesAI(board.getPlayer2())[0]!=null) {
                    Creature targetedCreature = getPossibleArcaneMovesAI(board.getPlayer2())[0].getTarget() ;

                    boolean moveMade = false;
                    while (!moveMade) {
                        moveMade = makeMove(board.getPlayer2(), getPossibleArcaneMovesAI(board.getPlayer2())[0]);
                        System.out.println("The AI have used the ArcaneBoost on ");
                        System.out.println(targetedCreature);
                    }board.getPlayer2().getScoreSheet().useArcaneBoost() ;
                    // reward = getReward(getPassivePlayer(), m[Integer.parseInt(n) - 1]);



                }



                else {
                    break;
                }
            }

            for (int j = 0; j < getAllDice().length; j++) {
                getAllDice()[j].reset();
            }

            System.out.println(board.getPlayer1().getName());
            System.out.println(printGameScore(board.getPlayer1()));
            System.out.println(board.getPlayer2().getName());
            System.out.println(printGameScore(board.getPlayer2()));
            switchPlayer();

            for (int l = 0; l < 1; l++) {
                System.out.println("AI's Turn");
                handleRewardAI(board.getPlayer2().getScoreSheet().getTurnRewards()[i], board.getPlayer2());
                boolean moveMade = false;
                Move chosen = null;
                while (!moveMade) {
                    System.out.println(("Press enter to see AI's dice"));
                    sc.nextLine();
                    rollDice();
                    Dice[] d = getAvailableDice();
                    for (int j = 0; j < getAvailableDice().length; j++) {
                        System.out.println((j + 1) + ". " + d[j]);
                    }
                    try {
                        Move c = moveChooser12(i+1);
                        moveMade = true;
                        chosen = c;
                    } catch (TimeWarpException ex) {
                        board.getPlayer2().getScoreSheet().useTimeWarp();
                        System.out.println("AI just used timewarp.");
                    }
                }
                if(chosen==null){
                    System.out.println("No available moves for AI.");
                    board.getStatus().endTurn();
                    break;
                }
                System.out.println("Press enter to see AI's move for first turn.");
                sc.nextLine();
                System.out.println("AI is going to hit the "+chosen.getTarget().getClass().getSimpleName());
                if(notBonus){
                    switch ((chosen.getDice()).getOldRealm()){
                        case 0:

                            getAllDice()[0].use();
                            break;
                        case 1:
                           getAllDice()[1].use();
                            break;
                        case 2:
                           getAllDice()[2].use();
                            break;
                        case 3:
                           getAllDice()[3].use();
                            break;
                        case 4:
                           getAllDice()[4].use();
                            break;
                        case 5:
                           getAllDice()[5].use();
                            break;
                    }
                }
                makeMoveAI(chosen);
                Creature targetedCreature = chosen.getTarget();
                System.out.println(targetedCreature);

                chosen = null;
                moveMade = false;

                if (getAvailableDice().length == 0) {
                    System.out.println("AI has no more moves");
                    board.getStatus().endTurn();
                    break;
                }

                while (!moveMade) {
                    System.out.println(("Press enter to see AI's dice"));
                    sc.nextLine();
                    rollDice();
                    Dice[] d = getAvailableDice();
                    for (int j = 0; j < getAvailableDice().length; j++) {
                        System.out.println((j + 1) + ". " + d[j]);
                    }
                    try {
                        Move c = moveChooser12(l+1);
                        moveMade = true;
                        chosen = c;
                    } catch (TimeWarpException ex) {
                        board.getPlayer2().getScoreSheet().useTimeWarp();
                        System.out.println("AI just used timewarp.");
                    }
                }
                if(chosen==null){
                    System.out.println("No available moves for AI.");
                    break;
                }
                System.out.println("Press enter to see AI's move for second turn.");
                sc.nextLine();
                System.out.println("AI is going to hit the "+chosen.getTarget().getClass().getSimpleName());
                if(notBonus){
                    switch (chosen.getDice().getOldRealm()){
                        case 0:

                        getAllDice()[0].use();
                        break;
                    case 1:
                       getAllDice()[1].use();
                        break;
                    case 2:
                       getAllDice()[2].use();
                        break;
                    case 3:
                       getAllDice()[3].use();
                        break;
                    case 4:
                       getAllDice()[4].use();
                        break;
                    case 5:
                       getAllDice()[5].use();
                        break;
                    }
                }
                makeMoveAI(chosen);
                targetedCreature = chosen.getTarget();
                System.out.println(targetedCreature);

                chosen = null;
                moveMade = false;

                while (!moveMade) {
                    System.out.println(("Press enter to see AI's dice"));
                    sc.nextLine();
                    rollDice();
                    Dice[] d = getAvailableDice();
                    for (int j = 0; j < getAvailableDice().length; j++) {
                        System.out.println((j + 1) + ". " + d[j]);
                    }
                    try {
                        Move c = MoveChooser3p(i+1);
                        moveMade = true;
                        chosen = c;
                    } catch (TimeWarpException ex) {
                        board.getPlayer2().getScoreSheet().useTimeWarp();
                        System.out.println("AI just used timewarp.");
                    }
                }
                if(chosen==null){
                    System.out.println("No available moves for AI.");
                    break;
                }
                System.out.println("Press enter to see AI's move for second turn.");
                sc.nextLine();
                System.out.println("AI is going to hit the "+chosen.getTarget().getClass().getSimpleName());
                if(notBonus){
                    chosen.getDice().use();
                }
                makeMoveAI(chosen);
                targetedCreature = chosen.getTarget();
                System.out.println(targetedCreature);

                if(notBonus){
                    switch (chosen.getDice().getOldRealm()){
                        case 0:

                        getAllDice()[0].use();
                        break;
                    case 1:
                       getAllDice()[1].use();
                        break;
                    case 2:
                       getAllDice()[2].use();
                        break;
                    case 3:
                       getAllDice()[3].use();
                        break;
                    case 4:
                       getAllDice()[4].use();
                        break;
                    case 5:
                       getAllDice()[5].use();
                        break;
                    }
                }
            }



            for (int j = 0; j < getAllDice().length; j++) {
                if (!getAllDice()[j].isForgotten() && !getAllDice()[j].isUsed()) {
                    getAllDice()[j].forget();
                }
            }

            if (getPossibleMovesForForgottenDice(getPassivePlayer()).length > 0) {
                boolean moveMade = false;
                System.out.println("Time for " + getPassivePlayer().getName() + "'s passive turn");
                System.out.println(board.getStatus().getTurn());
                while (!moveMade) { //TODO:note keda el dice here is not set as used khales shaklo not needed uk!
                    System.out.println("Choose a dice from 1 to " + (getForgottenRealmDice().length));
                    for (int z = 0; z < getForgottenRealmDice().length; z++) {
                        System.out.println((z + 1) + ". " + getForgottenRealmDice()[z]);
                    }
                    String x = sc.nextLine();
                    while (!isNumeric(x) || Integer.parseInt(x) < 1
                            || Integer.parseInt(x) > getForgottenRealmDice().length) {
                        System.out.println(
                                "Invalid input. Enter a number from 1 to " + (getForgottenRealmDice().length)
                                        + ".");
                        x = sc.nextLine();
                    }
                    dice = getForgottenRealmDice()[Integer.parseInt(x) - 1];
                    m = getPossibleMovesForADie(getPassivePlayer(), dice);
                    if (m.length == 1) {

                        moveMade = makeMove(getPassivePlayer(),
                                m[0]);
                        Creature targetedCreature = m[0].getTarget();
                        System.out.println(targetedCreature);
                    } else if (m.length == 0) {
                        System.out.println("No available moves for selected dice. Choose another dice.");
                    } else {
                        System.out.println("Choose a move 1-" + m.length);
                        for (int j = 0; j < m.length; j++) {
                            System.out.println((j + 1) + ". " + m[j]);
                        }
                        n = sc.nextLine();
                        while (!isNumeric(n) || Integer.parseInt(n) < 1 || Integer.parseInt(n) > m.length) {
                            System.out.println("Invalid input. Please choose a number between 1 and " + m.length);
                            n = sc.nextLine();
                        }
                        if (dice instanceof ArcanePrism) {
                            Creature h = m[Integer.parseInt(n) - 1].getTarget();
                            boolean red = false;
                            if (h instanceof Dragon) {
                                RedDice r = (RedDice) m[Integer.parseInt(n) - 1].getDice();
                                RedDice actualDice = new RedDice(dice.getValue());
                                actualDice.selectsDragon(r.getDragonNumberInteger());
                                red = true;
                                moveMade = makeMove(getPassivePlayer(),
                                        new Move(actualDice, m[Integer.parseInt(n) - 1].getTarget()));
                            }
                            if (!red) {
                                moveMade = makeMove(getPassivePlayer(),
                                        new Move(dice, m[Integer.parseInt(n) - 1].getTarget()));
                            }
                            Creature targetedCreature = m[Integer.parseInt(n) - 1].getTarget();
                            System.out.println(targetedCreature);
                        } else {
                            moveMade = makeMove(getPassivePlayer(), m[Integer.parseInt(n) - 1]);
                            Creature targetedCreature = m[Integer.parseInt(n) - 1].getTarget();
                            System.out.println(targetedCreature);
                        }
                    }
                    // if (reward.length == 2) {
                    // if (reward[0].compareTo(reward[1]) > 0) {
                    // handleReward(reward[0], getPassivePlayer());
                    // handleReward(reward[1], getPassivePlayer());// TODO: el mshkela hena even if
                    // we give
                    // //the passive player we dont even use him
                    // } else {
                    // handleReward(reward[1], getPassivePlayer());
                    // handleReward(reward[0], getPassivePlayer());// TODO el mshkela hena
                    // }
                    // } else if (reward.length == 1) {
                    // handleReward(reward[0], getPassivePlayer());
                    // }
                }

            } else {
                System.out.println("No available moves for passive player.");
                // board.getStatus().nextTurn();
            }

            board.getStatus().endTurn();

            // reward = new Reward[0];
            //TODO:arcane boost decision
            while (board.getPlayer2().getScoreSheet().arcaneBoostAvailable()
                    && getPossibleArcaneMovesAI(board.getPlayer2()).length > 0) {
                if (getPossibleArcaneMovesAI(board.getPlayer2())[0]!=null) {
                    Creature targetedCreature = getPossibleArcaneMovesAI(board.getPlayer2())[0].getTarget() ;
                    boolean moveMade = false;
                    while (!moveMade) {
                                moveMade = makeMove(board.getPlayer2(), getPossibleArcaneMovesAI(board.getPlayer2())[0]);

                            System.out.println("The AI have used the ArcaneBoost on ");
                            System.out.println(targetedCreature);
                            }board.getPlayer2().getScoreSheet().useArcaneBoost();
                            // reward = getReward(getPassivePlayer(), m[Integer.parseInt(n) - 1]);




                }



                else {
                    break;
                }

            }

            for (int j = 0; j < getAllDice().length; j++) {
                getAllDice()[i].unarcane();
            }

            while (board.getPlayer1().getScoreSheet().arcaneBoostAvailable()
                    && getPossibleArcaneMoves(board.getPlayer1()).length > 0) {
                System.out.println(
                        board.getPlayer1().getName()
                                + ", do you want to use ArcaneBoost? Enter Y for yes or N for no.");
                e = sc.nextLine().toUpperCase();
                while (e == null || (!e.equals("Y") && !e.equals("N")) ) {
                    System.out.println("Invalid input. Please enter Y for yes or N for no.");
                    e = sc.nextLine().toUpperCase();
                }
                if (e.equals("Y")) {
                    board.getPlayer1().getScoreSheet().useArcaneBoost();
                    System.out.println("ARCANE BOOST TIME");
                    for (int j = 0; j < getArcaneBoostDice().length; j++) {
                        System.out.println((j + 1) + ". " + getArcaneBoostDice()[j]);
                    }
                    boolean moveMade = false;
                    while (!moveMade) {
                        System.out.println("Choose a dice from 1 to " + (getArcaneBoostDice().length));
                        String x = sc.nextLine();
                        while (!isNumeric(x) || Integer.parseInt(x) < 1
                                || Integer.parseInt(x) > getArcaneBoostDice().length) {
                            System.out.println("Invalid input. Enter a number from 1 to "
                                    + (getArcaneBoostDice().length) + ".");
                            x = sc.nextLine();
                        }
                        dice = getArcaneBoostDice()[Integer.parseInt(x) - 1];
                        m = getPossibleMovesForADie(board.getPlayer1(), dice);
                        if (m.length == 1) {
                            if (notBonus) {
                                dice.use();
                            }
                            moveMade = makeMove(board.getPlayer1(),
                                    m[0]);
                            // reward = getReward(getActivePlayer(), m[0]);
                            Creature targetedCreature = m[0].getTarget();
                            System.out.println(targetedCreature);
                        } else if (m.length == 0) {
                            System.out.println("No available moves for selected dice. Choose another dice.");
                            continue;
                        } else {
                            System.out.println("Choose a move 1-" + m.length);
                            for (int j = 0; j < m.length; j++) {
                                System.out.println((j + 1) + ". " + m[j]);
                            }
                            n = sc.nextLine();
                            while (!isNumeric(n) || Integer.parseInt(n) < 1 || Integer.parseInt(n) > m.length) {
                                System.out
                                        .println("Invalid input. Please choose a number between 1 and " + m.length);
                                n = sc.nextLine();
                            }
                            if (notBonus) {
                                dice.use();
                            }
                            if (dice instanceof ArcanePrism) {
                                Creature h = m[Integer.parseInt(n) - 1].getTarget();
                                boolean red = false;
                                if (h instanceof Dragon) {
                                    RedDice r = (RedDice) m[Integer.parseInt(n) - 1].getDice();
                                    RedDice actualDice = new RedDice(dice.getValue());
                                    actualDice.selectsDragon(r.getDragonNumberInteger());
                                    red = true;
                                    moveMade = makeMove(board.getPlayer1(),
                                            new Move(actualDice, m[Integer.parseInt(n) - 1].getTarget()));
                                }
                                if (!red) {
                                    moveMade = makeMove(board.getPlayer1(),
                                            new Move(dice, m[Integer.parseInt(n) - 1].getTarget()));
                                }
                                Creature targetedCreature = m[Integer.parseInt(n) - 1].getTarget();
                                System.out.println(targetedCreature);
                            } else {
                                moveMade = makeMove(board.getPlayer1(), m[Integer.parseInt(n) - 1]);
                                Creature targetedCreature = m[Integer.parseInt(n) - 1].getTarget();
                                System.out.println(targetedCreature);
                            }
                            // reward = getReward(getActivePlayer(), m[Integer.parseInt(n) - 1]);
                            // Creature targetedCreature = m[Integer.parseInt(n) - 1].getTarget();
                            // System.out.println(targetedCreature);
                        }
                        // if (reward.length == 2) {
                        // if (reward[0].compareTo(reward[1]) > 0) {
                        // handleReward(reward[0], getActivePlayer());
                        // handleReward(reward[1], getActivePlayer());
                        // } else {
                        // handleReward(reward[1], getActivePlayer());
                        // handleReward(reward[0], getActivePlayer());
                        // }
                        // } else if (reward.length == 1) {
                        // handleReward(reward[0], getActivePlayer());
                        // }
                    }

                } else {
                    break;
                }
            }

            for (int j = 0; j < getAllDice().length; j++) {
                getAllDice()[j].reset();
            }

            System.out.println(board.getPlayer1().getName());
            System.out.println(printGameScore(board.getPlayer1()));
            System.out.println(board.getPlayer2().getName());
            System.out.println(printGameScore(board.getPlayer2()));
            switchPlayer();

            getGameStatus().nextRound();
        }
    }

    // PvP Mode
    public void startGame(String ignoredS) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter player 1 name");
        String name1 = sc.nextLine();
        while (name1.isEmpty() || (name1.charAt(0) >= '0' && name1.charAt(0) <= '9') || name1.equals("AI")) {
            System.out.println("Please enter a valid name.");
            name1 = sc.nextLine();
        }
        board.getPlayer1().setName(name1);
        System.out.println("Enter player 2 name");
        String name2 = sc.nextLine();
        while (name2.isEmpty() || (name2.charAt(0) >= '0' && name2.charAt(0) <= '9') || (name2.equals(name1))) {
            if ((name2.equals(name1))) {
                System.out.println("You need to choose a different name than player 1");
                name2 = sc.nextLine();
            } else {
                System.out.println("Please enter a valid name.");
                name2 = sc.nextLine();
            }
        }
        board.getPlayer2().setName(name2);
        board.getStatus().nextState();

        for (int i = 0; i < 6; i++) {
            System.out.println("ROUND " + getGameStatus().getRoundNumber());
            for (int l = 0; l < 2; l++) {
                System.out.println(getActivePlayer().getName() + "'s turn");
                // System.out.println(getActivePlayer().getScoreSheet().getTurnRewards()[i]);
                handleReward(getActivePlayer().getScoreSheet().getTurnRewards()[i], getActivePlayer());
                for (int k = 0; k < 3; k++) {
                    if (getAvailableDice().length == 0
                            && getPossibleMovesForAvailableDice(getActivePlayer()).length == 0) {
                        getGameStatus().endTurn();
                        break;
                    }
                    System.out.println(getActivePlayer().getName() + " YOU NEED TO PLEASE Press enter to roll dice");
                    sc.nextLine();
                    rollDice();
                    Dice[] d = getAvailableDice();
                    for (int j = 0; j < getAvailableDice().length; j++) {
                        System.out.println("" + (j + 1) + ". " + d[j]);
                    }
                    String c;

                    while (getActivePlayer().getScoreSheet().timeWarpAvailable()) {
                        System.out.println("u have " + getActivePlayer().getScoreSheet().getTimeWarpCount()
                                + " timewarps, Do you want to reroll? Enter Y for yes or N for no.");
                        c = sc.nextLine().toUpperCase();
                        while (c == null || (!c.equals("Y") && !c.equals("N")) ) {
                            System.out.println("Invalid input. Please enter Y for yes or N for no.");
                            c = sc.nextLine().toUpperCase();
                        }
                        if (c.equals("Y")) {
                            getActivePlayer().getScoreSheet().useTimeWarp();
                            rollDice();
                            for (int j = 0; j < getAvailableDice().length; j++) {
                                System.out.println("" + (j + 1) + ". " + d[j]);
                            }
                        } else {
                            break;
                        }
                    }
                    // Reward[] reward = new Reward[0];
                    if (getPossibleMovesForAvailableDice(getActivePlayer()).length > 0) {
                        boolean moveMade = false;
                        String turnnumber = "";
                        String p = "";
                        if (board.getPlayer1().getPlayerStatus() == PlayerStatus.ACTIVE)
                        {p+=" player 1";}
                        else
                        {p+="player 2";}
                        switch (board.getStatus().getTurn()) {
                            case P1AT1:
                            case P2AT1:
                                System.out.println(board.getStatus().getTurn() + "and the one with an active status is "+ p);
                                turnnumber = "first";
                                break;
                            case P1AT2:
                            case P2AT2:
                                turnnumber = "second";
                                System.out.println(board.getStatus().getTurn());
                                break;
                            case P1AT3:
                            case P2AT3:
                                turnnumber = "third";
                                System.out.println(board.getStatus().getTurn());
                                break;
                            default:
                                System.out.println(board.getStatus().getTurn() + "bayez");
                                break;
                        }
                        String e;
                        Move[] m;
                        Dice dice;
                        while (!moveMade) {
                            System.out.println(getActivePlayer().getName() + ", play your " + turnnumber + " turn.");
                            System.out.println("Choose a dice from 1 to " + (getAvailableDice().length));
                            String x = sc.nextLine();
                            while (!isNumeric(x) || Integer.parseInt(x) < 1
                                    || Integer.parseInt(x) > getAvailableDice().length) {
                                System.out.println("Invalid input. Enter a number from 1 to " + (d.length) + ".");
                                x = sc.nextLine();
                            }
                            dice = getAvailableDice()[Integer.parseInt(x) - 1];
                            m = getPossibleMovesForADie(getActivePlayer(), dice);
                            if (m.length == 1) {
                                if (notBonus) {
                                    dice.use();
                                }
                                moveMade = makeMove(getActivePlayer(), m[0]);
                                // reward = getReward(getActivePlayer(), m[0]);
                                Creature targetedCreature = m[0].getTarget();
                                System.out.println(targetedCreature);
                            } else if (m.length == 0) {
                                System.out.println("No available moves for selected dice. Choose another dice.");
                            } else {
                                System.out.println("Choose a move 1-" + m.length);
                                for (int j = 0; j < m.length; j++) {
                                    System.out.println((j + 1) + ". " + m[j]);
                                }
                                e = sc.nextLine();//
                                while (!isNumeric(e) || Integer.parseInt(e) < 1 || Integer.parseInt(e) > m.length) {
                                    System.out
                                            .println("Invalid input. Please choose a number between 1 and " + m.length);
                                    e = sc.nextLine();
                                }
                                if (notBonus) {
                                    dice.use();
                                }
                                System.out.println("hi u have used the dice u make move next");
                                if (dice instanceof ArcanePrism) {
                                    Creature h = m[Integer.parseInt(e) - 1].getTarget();
                                    boolean red = false;
                                    if (h instanceof Dragon) {
                                        RedDice r = (RedDice) m[Integer.parseInt(e) - 1].getDice();
                                        RedDice actualDice = new RedDice(dice.getValue());
                                        actualDice.selectsDragon(r.getDragonNumberInteger());
                                        red = true;
                                        moveMade = makeMove(getActivePlayer(),
                                                new Move(actualDice, m[Integer.parseInt(e) - 1].getTarget()));
                                    }
                                    if (!red) {
                                        moveMade = makeMove(getActivePlayer(),
                                                new Move(dice, m[Integer.parseInt(e) - 1].getTarget()));
                                    }
                                    Creature targetedCreature = m[Integer.parseInt(e) - 1].getTarget();
                                    System.out.println(targetedCreature);
                                } else {
                                    moveMade = makeMove(getActivePlayer(), m[Integer.parseInt(e) - 1]);
                                    Creature targetedCreature = m[Integer.parseInt(e) - 1].getTarget();
                                    System.out.println(targetedCreature);
                                }
                                // reward = getReward(getActivePlayer(), m[Integer.parseInt(e) - 1]);
                                // System.out.println("the bonus u just claimed is of length" + reward.length);
                                // // TODO:remove
                                // Creature targetedCreature = m[Integer.parseInt(e) - 1].getTarget();
                                // System.out.println(targetedCreature);
                                // }
                                // if (reward.length == 2) {
                                // if (reward[0].compareTo(reward[1]) > 0) {
                                // handleReward(reward[0], getActivePlayer());
                                // handleReward(reward[1], getActivePlayer());// TODO: el mshkela hena
                                // } else {
                                // handleReward(reward[1], getActivePlayer());
                                // handleReward(reward[0], getActivePlayer());// TODO el mshkela hena
                                // }
                                // } else if (reward.length == 1) {
                                // handleReward(reward[0], getActivePlayer());
                                // }
                            }

                            // board.getStatus().endTurn();
                            // break;

                        }
                    } else {
                        board.getStatus().endTurn();
                        break;
                    }

                    board.getStatus().nextTurn();

                }
                for (int j = 0; j < getAllDice().length; j++) {
                    if (!getAllDice()[j].isForgotten() && !getAllDice()[j].isUsed()) {
                        getAllDice()[j].forget();
                    }
                }
                Move[] m;
                String n;
                // Reward[] reward = new Reward[0];
                if (getPossibleMovesForForgottenDice(getPassivePlayer()).length > 0) {
                    boolean moveMade = false;
                    System.out.println("Time for " + getPassivePlayer().getName() + "'s passive turn");
                    System.out.println(board.getStatus().getTurn());
                    while (!moveMade) {
                        System.out.println("Choose a dice from 1 to " + (getForgottenRealmDice().length));
                        for (int z = 0; z < getForgottenRealmDice().length; z++) {
                            System.out.println((z + 1) + ". " + getForgottenRealmDice()[z]);
                        }
                        String x = sc.nextLine();
                        while (!isNumeric(x) || Integer.parseInt(x) < 1
                                || Integer.parseInt(x) > getForgottenRealmDice().length) {
                            System.out.println(
                                    "Invalid input. Enter a number from 1 to " + (getForgottenRealmDice().length)
                                            + ".");
                            x = sc.nextLine();
                        }
                        Dice dice = getForgottenRealmDice()[Integer.parseInt(x) - 1];
                        m = getPossibleMovesForADie(getPassivePlayer(), dice);
                        if (m.length == 1) {

                            moveMade = makeMove(getPassivePlayer(),
                                    m[0]);
                            Creature targetedCreature = m[0].getTarget();
                            System.out.println(targetedCreature);
                        } else if (m.length == 0) {
                            System.out.println("No available moves for selected dice. Choose another dice.");
                        } else {
                            System.out.println("Choose a move 1-" + m.length);
                            for (int j = 0; j < m.length; j++) {
                                System.out.println((j + 1) + ". " + m[j]);
                            }
                            n = sc.nextLine();
                            while (!isNumeric(n) || Integer.parseInt(n) < 1 || Integer.parseInt(n) > m.length) {
                                System.out.println("Invalid input. Please choose a number between 1 and " + m.length);
                                n = sc.nextLine();
                            }
                            if (dice instanceof ArcanePrism) {
                                Creature h = m[Integer.parseInt(n) - 1].getTarget();
                                boolean red = false;
                                if (h instanceof Dragon) {
                                    RedDice r = (RedDice) m[Integer.parseInt(n) - 1].getDice();
                                    RedDice actualDice = new RedDice(dice.getValue());
                                    actualDice.selectsDragon(r.getDragonNumberInteger());
                                    red = true;
                                    moveMade = makeMove(getPassivePlayer(),
                                            new Move(actualDice, m[Integer.parseInt(n) - 1].getTarget()));
                                }
                                if (!red) {
                                    moveMade = makeMove(getPassivePlayer(),
                                            new Move(dice, m[Integer.parseInt(n) - 1].getTarget()));
                                }
                                Creature targetedCreature = m[Integer.parseInt(n) - 1].getTarget();
                                System.out.println(targetedCreature);
                            } else {
                                moveMade = makeMove(getPassivePlayer(), m[Integer.parseInt(n) - 1]);
                                Creature targetedCreature = m[Integer.parseInt(n) - 1].getTarget();
                                System.out.println(targetedCreature);
                            }
                        }
                        // if (reward.length == 2) {
                        // if (reward[0].compareTo(reward[1]) > 0) {
                        // handleReward(reward[0], getPassivePlayer());
                        // handleReward(reward[1], getPassivePlayer());// TODO: el mshkela hena even if
                        // we give
                        // //the passive player we dont even use him
                        // } else {
                        // handleReward(reward[1], getPassivePlayer());
                        // handleReward(reward[0], getPassivePlayer());// TODO el mshkela hena
                        // }
                        // } else if (reward.length == 1) {
                        // handleReward(reward[0], getPassivePlayer());
                        // }
                    }

                } else {
                    System.out.println("No available moves for passive player.");
                    // board.getStatus().nextTurn();
                }

                board.getStatus().endTurn();
                String e;
                // reward = new Reward[0];
                while (getActivePlayer().getScoreSheet().arcaneBoostAvailable()
                        && getPossibleArcaneMoves(getActivePlayer()).length > 0) {
                    System.out.println(
                            getActivePlayer().getName()
                                    + ", do you want to use ArcaneBoost? Enter Y for yes or N for no.");
                    e = sc.nextLine().toUpperCase();
                    while (e == null || (!e.equals("Y") && !e.equals("N")) ) {
                        System.out.println("Invalid input. Please enter Y for yes or N for no.");
                        e = sc.nextLine().toUpperCase();
                    }
                    if (e.equals("Y")) {
                        getActivePlayer().getScoreSheet().useArcaneBoost();
                        System.out.println("ARCANE BOOST TIME");
                        for (int j = 0; j < getArcaneBoostDice().length; j++) {
                            System.out.println((j + 1) + ". " + getArcaneBoostDice()[j]);
                        }
                        boolean moveMade = false;
                        while (!moveMade) {
                            System.out.println("Choose a dice from 1 to " + (getArcaneBoostDice().length));
                            String x = sc.nextLine();
                            while (!isNumeric(x) || Integer.parseInt(x) < 1
                                    || Integer.parseInt(x) > getArcaneBoostDice().length) {
                                System.out.println("Invalid input. Enter a number from 1 to "
                                        + (getArcaneBoostDice().length) + ".");
                                x = sc.nextLine();
                            }
                            Dice dice = getArcaneBoostDice()[Integer.parseInt(x) - 1];
                            m = getPossibleMovesForADie(getActivePlayer(), dice);
                            if (m.length == 1) {
                                if (notBonus) {
                                    dice.arcaneBoosted();
                                }
                                moveMade = makeMove(getActivePlayer(),
                                        m[0]);
                                // reward = getReward(getActivePlayer(), m[0]);
                                Creature targetedCreature = m[0].getTarget();
                                System.out.println(targetedCreature);
                            } else if (m.length == 0) {
                                System.out.println("No available moves for selected dice. Choose another dice.");
                            } else {
                                System.out.println("Choose a move 1-" + m.length);
                                for (int j = 0; j < m.length; j++) {
                                    System.out.println((j + 1) + ". " + m[j]);
                                }
                                n = sc.nextLine();
                                while (!isNumeric(n) || Integer.parseInt(n) < 1 || Integer.parseInt(n) > m.length) {
                                    System.out
                                            .println("Invalid input. Please choose a number between 1 and " + m.length);
                                    n = sc.nextLine();
                                }
                                if (notBonus) {
                                    dice.arcaneBoosted();
                                }
                                if (dice instanceof ArcanePrism) {
                                    Creature h = m[Integer.parseInt(n) - 1].getTarget();
                                    boolean red = false;
                                    if (h instanceof Dragon) {
                                        RedDice r = (RedDice) m[Integer.parseInt(n) - 1].getDice();
                                        RedDice actualDice = new RedDice(dice.getValue());
                                        actualDice.selectsDragon(r.getDragonNumberInteger());
                                        red = true;
                                        moveMade = makeMove(getActivePlayer(),
                                                new Move(actualDice, m[Integer.parseInt(n) - 1].getTarget()));
                                    }
                                    if (!red) {
                                        moveMade = makeMove(getActivePlayer(),
                                                new Move(dice, m[Integer.parseInt(n) - 1].getTarget()));
                                    }
                                    Creature targetedCreature = m[Integer.parseInt(n) - 1].getTarget();
                                    System.out.println(targetedCreature);
                                } else {
                                    moveMade = makeMove(getActivePlayer(), m[Integer.parseInt(n) - 1]);
                                    Creature targetedCreature = m[Integer.parseInt(n) - 1].getTarget();
                                    System.out.println(targetedCreature);
                                }
                                // reward = getReward(getActivePlayer(), m[Integer.parseInt(n) - 1]);
                                // Creature targetedCreature = m[Integer.parseInt(n) - 1].getTarget();
                                // System.out.println(targetedCreature);
                            }
                            // if (reward.length == 2) {
                            // if (reward[0].compareTo(reward[1]) > 0) {
                            // handleReward(reward[0], getActivePlayer());
                            // handleReward(reward[1], getActivePlayer());
                            // } else {
                            // handleReward(reward[1], getActivePlayer());
                            // handleReward(reward[0], getActivePlayer());
                            // }
                            // } else if (reward.length == 1) {
                            // handleReward(reward[0], getActivePlayer());
                            // }
                        }

                    } else {
                        break;
                    }
                }

                for (int j = 0; j < getAllDice().length; j++) {
                    getAllDice()[i].unarcane();
                }

                Dice dice;
                // reward = new Reward[0];
                while (getPassivePlayer().getScoreSheet().arcaneBoostAvailable()
                        && getPossibleArcaneMoves(getPassivePlayer()).length > 0) {
                    System.out.println(
                            getPassivePlayer().getName()
                                    + ", do you want to use ArcaneBoost? Enter Y for yes or N for no.");
                    e = sc.nextLine().toUpperCase();
                    while (e == null || (!e.equals("Y") && !e.equals("N")) ) {
                        System.out.println("Invalid input. Please enter Y for yes or N for no.");
                        e = sc.nextLine().toUpperCase();
                    }
                    if (e.equals("Y")) {
                        getPassivePlayer().getScoreSheet().useArcaneBoost();
                        for (int j = 0; j < getArcaneBoostDice().length; j++) {
                            System.out.println((j + 1) + ". " + getArcaneBoostDice()[j]);
                        }
                        boolean moveMade = false;
                        while (!moveMade) {
                            System.out.println("Choose a dice from 1 to " + (getArcaneBoostDice().length));
                            String x = sc.nextLine();
                            while (!isNumeric(x) || Integer.parseInt(x) < 1
                                    || Integer.parseInt(x) > getArcaneBoostDice().length) {
                                System.out.println("Invalid input. Enter a number from 1 to "
                                        + (getArcaneBoostDice().length) + ".");
                                x = sc.nextLine();
                            }
                            dice = getArcaneBoostDice()[Integer.parseInt(x) - 1];
                            m = getPossibleMovesForADie(getPassivePlayer(), dice);
                            if (m.length == 1) {
                                if (notBonus) {
                                    dice.arcaneBoosted();
                                }
                                moveMade = makeMove(getPassivePlayer(), m[0]); // TODO:aloo hwa hena lw arcane boost
                                                                               // yehsal eh
                                // reward = getReward(getPassivePlayer(), m[0]);
                                Creature targetedCreature = m[0].getTarget();
                                System.out.println(targetedCreature);
                            } else if (m.length == 0) {
                                System.out.println("No available moves for selected dice. Choose another dice.");
                            } else {
                                System.out.println("Choose a move 1-" + m.length);
                                for (int j = 0; j < m.length; j++) {
                                    System.out.println((j + 1) + ". " + m[j]);
                                }
                                n = sc.nextLine();
                                while (!isNumeric(n) || Integer.parseInt(n) < 1 || Integer.parseInt(n) > m.length) {
                                    System.out
                                            .println("Invalid input. Please choose a number between 1 and " + m.length);
                                    n = sc.nextLine();
                                }
                                if (notBonus) {
                                    dice.arcaneBoosted();
                                }
                                if (dice instanceof ArcanePrism) {
                                    Creature h = m[Integer.parseInt(n) - 1].getTarget();
                                    boolean red = false;
                                    if (h instanceof Dragon) {
                                        RedDice r = (RedDice) m[Integer.parseInt(n) - 1].getDice();
                                        RedDice actualDice = new RedDice(dice.getValue());
                                        actualDice.selectsDragon(r.getDragonNumberInteger());
                                        red = true;
                                        moveMade = makeMove(getPassivePlayer(),
                                                new Move(actualDice, m[Integer.parseInt(n) - 1].getTarget()));
                                    }
                                    if (!red) {
                                        moveMade = makeMove(getPassivePlayer(),
                                                new Move(dice, m[Integer.parseInt(n) - 1].getTarget()));
                                    }
                                    Creature targetedCreature = m[Integer.parseInt(n) - 1].getTarget();
                                    System.out.println(targetedCreature);
                                } else {
                                    moveMade = makeMove(getPassivePlayer(), m[Integer.parseInt(n) - 1]);
                                    Creature targetedCreature = m[Integer.parseInt(n) - 1].getTarget();
                                    System.out.println(targetedCreature);
                                }
                                // reward = getReward(getPassivePlayer(), m[Integer.parseInt(n) - 1]);
                                Creature targetedCreature = m[Integer.parseInt(n) - 1].getTarget();
                                System.out.println(targetedCreature);
                            }
                            // if (reward.length == 2) {
                            // if (reward[0].compareTo(reward[1]) > 0) {
                            // handleReward(reward[0], getPassivePlayer());
                            // handleReward(reward[1], getPassivePlayer());
                            // } else {
                            // handleReward(reward[1], getPassivePlayer());
                            // handleReward(reward[0], getPassivePlayer());
                            // }
                            // } else if (reward.length == 1) {
                            // handleReward(reward[0], getPassivePlayer());
                            // }
                        }

                    } else {
                        break;
                    }
                }

                for (int j = 0; j < getAllDice().length; j++) {
                    getAllDice()[j].reset();
                }
                System.out.println(board.getPlayer1().getName());
                System.out.println(printGameScore(board.getPlayer1()));
                System.out.println(board.getPlayer2().getName());
                System.out.println(printGameScore(board.getPlayer2()));
                switchPlayer();
            }

            getGameStatus().nextRound();
        }
        Player winner;
        if(board.getPlayer1().getGameScore().getTotal()>board.getPlayer2().getGameScore().getTotal()){
            winner = board.getPlayer1();
        }
        else{
            winner = board.getPlayer2();
        }
        System.out.println("AN THE WINNER ISSSSSSSS....drum roll pls");
        System.out.println("el fal7oos(a) "+ winner.getName());
    }
    // TODO:no endgame implemented
    // } catch (NumberFormatException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }

    // public void handleElemental(Reward reward,Player p){
    // String x = reward.getClass().getSimpleName();
    // if(x.equals("ElementalCrest"))
    // {p.incrementElementalCrest();
    // System.out.println("You got an elementalcrest!");}

    // }

    public void handleReward(Reward reward, Player p) {
        if (reward == null)
            return;
        notBonus = false;
        Scanner sc = new Scanner(System.in);
        switch (reward.getClass().getSimpleName()) {
            case "ArcaneBoost":
                p.getScoreSheet().incrementArcaneBoost();
                System.out.println("You got an arcane boost!");
                // TODO:ya3ni lw gatlo w howa passive hayeyhsal eh
                break;
            case "TimeWarp":
                p.getScoreSheet().incrementTimeWarp();
                System.out.println("You got a time warp!");
                break;
            case "ElementalCrest":
                p.getScoreSheet().incrementElementalCrest();
                p.getGameScore().incrementElementalCrest();
                System.out.println("You got an elemental crest!");
                break;
            case "Bonus":
                Bonus b = (Bonus) reward;
                boolean moveMade;
                switch (b.getColor()) {
                    case RED:
                        moveMade = false;
                        while (!moveMade) {
                            if (getPossibleMovesForARealm(p, new RedDice()).length > 0) {
                                System.out.println("RED BONUS TIME!!!");
                                Move[] m = getPossibleMovesForARealm(p, new RedDice());
                                System.out.println("Choose a move 1-" + m.length);
                                for (int j = 0; j < m.length; j++) {
                                    System.out.println("" + (j + 1) + ". " + m[j]);
                                }
                                String f = sc.nextLine();
                                while (!isNumeric(f) || Integer.parseInt(f) > m.length || Integer.parseInt(f) < 1) {
                                    System.out.println("Invalid input. Please enter a number from 1 to "+m.length);
                                    f = sc.nextLine();
                                }
                                RedDice d = new RedDice(m[Integer.parseInt(f)-1].getDice().getValue());
                                d.selectsDragon(((RedDice)(m[Integer.parseInt(f)-1].getDice())).getDragonNumberInteger()); // TODO:WOOWOO
                                moveMade = makeMove(p, new Move(d, p.getScoreSheet().getCreatureByRealm(d)));
                                if (!moveMade) {
                                    System.out.println("Invalid move");
                                } else {
                                    System.out.println(p.getScoreSheet().getCreatureByRealm(d));
                                }
                            } else {
                                System.out.println("You got a red bonus but you have no possible moves");
                                break;
                            }
                        }
                        break;

                    case YELLOW:
                        moveMade = false;
                        while (!moveMade) {
                            if (getPossibleMovesForARealm(p, new YellowDice()).length > 0) {
                                System.out.println("YELLOW BONUS TIME!!!");
                                Move[] m = getPossibleMovesForARealm(p, new YellowDice());
                                // System.out.println("Choose a value for your yellow bonus");
                                System.out.println("Choose a move 1-" + m.length);
                                for (int j = 0; j < m.length; j++) {
                                    System.out.println("" + (j + 1) + ". " + m[j]);
                                }
                                String f = sc.nextLine();
                                while (!isNumeric(f) || Integer.parseInt(f) > m.length || Integer.parseInt(f) < 1) {
                                    System.out.println("Invalid input. Please enter a number from 1 to "+m.length);
                                    f = sc.nextLine();
                                }
                                // TODO:note eno mfrood el dice is initialized with one mafeesh haga hatkhosh
                                // forgotton with makemove
                                YellowDice d = new YellowDice(Integer.parseInt(f));
                                moveMade = makeMove(p, new Move(d, p.getScoreSheet().getCreatureByRealm(d)));
                                if (!moveMade) {
                                    System.out.println("Invalid move");
                                } else {
                                    System.out.println(p.getScoreSheet().getCreatureByRealm(d));
                                }
                            } else {
                                System.out.println("You got a yellow bonus but you have no possible moves");
                                break;
                            }
                        }
                        break;
                    case BLUE:
                        moveMade = false;
                        while (!moveMade) {
                            if (getPossibleMovesForARealm(p, new BlueDice()).length > 0) {
                                System.out.println("BLUE BONUS TIME!!!");
                                // System.out.println("Choose a value for your blue bonus");
                                Move[] m = getPossibleMovesForARealm(p, new BlueDice());
                                // System.out.println("Choose a value for your yellow bonus");
                                System.out.println("Choose a move 1-" + m.length);
                                for (int j = 0; j < m.length; j++) {
                                    System.out.println("" + (j + 1) + ". " + m[j]);
                                }
                                String f = sc.nextLine();
                                while (!isNumeric(f) || Integer.parseInt(f) > m.length || Integer.parseInt(f) < 1) {
                                    System.out.println("Invalid input. Please enter a number from 1 to "+m.length);
                                    f = sc.nextLine();
                                }
                                BlueDice d = new BlueDice(Integer.parseInt(f));
                                // TODO:shoofo lw el blue dice nafsaha beeb2a forgotton uslan wla la
                                moveMade = makeMove(p, new Move(d, p.getScoreSheet().getCreatureByRealm(d)));
                                if (!moveMade) {
                                    System.out.println("Invalid move");
                                } else {
                                    System.out.println(p.getScoreSheet().getCreatureByRealm(d));
                                }
                            } else {
                                System.out.println("You got a blue bonus but you have no possible moves");
                                break;
                            }
                        }
                        break;
                    case MAGENTA:
                        moveMade = false;
                        while (!moveMade) {
                            if (getPossibleMovesForARealm(p, new MagentaDice()).length > 0) {
                                System.out.println("MAGENTA BONUS TIME!!!");
                                Move[] m = getPossibleMovesForARealm(p, new MagentaDice());
                                System.out.println("Choose a move 1-" + m.length);
                                for (int j = 0; j < m.length; j++) {
                                    System.out.println("" + (j + 1) + ". " + m[j]);
                                }
                                String f = sc.nextLine();
                                while (!isNumeric(f) || Integer.parseInt(f) > m.length || Integer.parseInt(f) < 1) {
                                    System.out.println("Invalid input. Please enter a number from 1 to "+m.length);
                                    f = sc.nextLine();
                                }
                                MagentaDice d = new MagentaDice(m[Integer.parseInt(f)-1].getDice().getValue());
                                moveMade = makeMove(p, new Move(d, p.getScoreSheet().getCreatureByRealm(d)));
                                if (!moveMade) {
                                    System.out.println("Invalid move");
                                } else {
                                    System.out.println(p.getScoreSheet().getCreatureByRealm(d));
                                }
                            } else {
                                System.out.println("You got a magenta bonus but you have no possible moves");
                                break;
                            }
                        }
                        break;
                    case GREEN:
                        moveMade = false;
                        while (!moveMade) {
                            if (getPossibleMovesForARealm(p, new GreenDice()).length > 0) {
                                System.out.println("GREEN BONUS TIME!!!");
                                Move[] m = getPossibleMovesForARealm(p, new GreenDice());
                                System.out.println("Choose a move 1-" + m.length);
                                for (int j = 0; j < m.length; j++) {
                                    System.out.println("" + (j + 1) + ". " + m[j]);
                                }
                                String f = sc.nextLine();
                                while (!isNumeric(f) || Integer.parseInt(f) > m.length || Integer.parseInt(f) < 1) {
                                    System.out.println("Invalid input. Please enter a number from 1 to "+m.length);
                                    f = sc.nextLine();
                                }
                                Move k = m[Integer.parseInt(f) - 1];
                                GreenDice d = new GreenDice();
                                d = (GreenDice) k.getDice();
                                // GreenDice g = (GreenDice) k.getDice();
                                // GreenDice d = new GreenDice();
                                // if(g.getValue()<7){
                                // d.setValue(6);
                                // d.setWhite(g.get);

                                // }
                                System.out.println("just for tracking purposes the green value is"+ d.getGreenValue()+"and white val is "+d.getWhiteValue());
                                moveMade = makeMove(p, new Move(d, p.getScoreSheet().getCreatureByRealm(d)));
                                if (!moveMade) {
                                    System.out.println("Invalid move");
                                } else {
                                    System.out.println(p.getScoreSheet().getCreatureByRealm(d));
                                }
                            } else {
                                System.out.println("You got a green bonus but you have no possible moves");
                                break;
                            }
                        }
                        break;
                    case WHITE:
                        moveMade = false;
                        while (!moveMade) {
                            if (getAllPossibleMoves(p).length > 0) {
                                System.out.println("ESSENCE OMO BONUS TIME!!!");
                                System.out.println("Choose a realm for your essence bonus");
                                System.out.println("1.Red 2.Green 3.Blue 4.Magenta 5.Yellow");
                                String f = sc.nextLine();
                                Move[] m = null;
                                // Dice d = null;
                                do {
                                    if (!isNumeric(f)|| Integer.parseInt(f) > 5 || Integer.parseInt(f) < 1) {
                                        System.out.println("Invalid input. Please enter a number from 1 to 5.");
                                        f = sc.nextLine();
                                        continue;
                                    }
                                    switch (Integer.parseInt(f)) {
                                        case 1: {
                                            m = getPossibleMovesForARealm(p, new RedDice());
                                            // TODO:WOOWOO
                                            // TODO:WOOWOO
                                            // moveMade = makeMove(p,new Move(d,
                                            // p.getScoreSheet().getCreatureByRealm(d)));

                                            // d = reddice;

                                            // d = reddice;
                                            // d = new RedDice(Integer.parseInt(f));
                                            break;
                                        }
                                        case 2:
                                            m = getPossibleMovesForARealm(p, new GreenDice());
                                            // d = new GreenDice(Integer.parseInt(f));
                                            // d = new GreenDice(Integer.parseInt(f));
                                            break;
                                        case 3:
                                            m = getPossibleMovesForARealm(p, new BlueDice());
                                            // d = new BlueDice(Integer.parseInt(f));
                                            // d = new BlueDice(Integer.parseInt(f));
                                            break;
                                        case 4:
                                            m = getPossibleMovesForARealm(p, new MagentaDice());
                                            // d = new MagentaDice(Integer.parseInt(f));
                                            // d = new MagentaDice(Integer.parseInt(f));
                                            break;
                                        case 5:
                                            m = getPossibleMovesForARealm(p, new YellowDice());
                                            // d = new YellowDice(Integer.parseInt(f));
                                            // d = new YellowDice(Integer.parseInt(f));
                                            break;
                                        default:
                                            break;
                                    }
                                    if (m.length == 0) {
                                        System.out.println(

                                                "This realm has no possible moves. Please choose another realm to hit.");
                                        f = sc.nextLine();
                                    }
                                } while (!isNumeric(f) || Integer.parseInt(f) > 5 || Integer.parseInt(f) < 1 || m == null || (m.length == 0));

                                // RedDice reddice = new RedDice(Integer.parseInt(f));
                                // Move[] dd = getPossibleMovesForADie(p, reddice);

                                if (m.length == 1) {
                                    moveMade = makeMove(p, m[0]);
                                    // reward = getReward(getActivePlayer(), m[0]);
                                    Creature targetedCreature = m[0].getTarget();
                                    System.out.println(targetedCreature);
                                } else {
                                    System.out.println("Choose a move 1-" + m.length);
                                    for (int j = 0; j < m.length; j++) {
                                        System.out.println((j + 1) + ". " + m[j]);
                                    }
                                    String k = sc.nextLine();
                                    // int y = Integer.parseInt(k);
                                    while (!isNumeric(k) || Integer.parseInt(k) > m.length
                                            || Integer.parseInt(k) < 1) {
                                        System.out.println(
                                                "Invalid input. Please enter a number from 1 to" + m.length);
                                        k = sc.nextLine();
                                    }
                                    moveMade = makeMove(p, m[Integer.parseInt(k) - 1]);
                                    // RedDice redDiceOfMoveDice = (RedDice) dd[y - 1].getDice();
                                    // reddice.selectsDragon(redDiceOfMoveDice.getDragonNumberInteger());

                                    // moveMade = makeMove(p, new Move(d, p.getScoreSheet().getCreatureByRealm(d)));
                                    if (!moveMade) {
                                        System.out.println("Invalid move somehow for some reason");// TODO:why would it ever output that!!
                                    } else {
                                        System.out.println(p.getScoreSheet()
                                                .getCreatureByRealm(m[Integer.parseInt(k) - 1].getDice()));
                                    }
                                }
                            } else {
                                System.out.println("You got an essence bonus but you have no possible moves");
                                break;
                            }
                        }
                        break;
                }

            default:
                break;
        }
        notBonus = true;
        // } catch (NumberFormatException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        System.out.println("NYAHAHAHAHA TESTAHEL ALF MAJESTIC LAKE ;)");
    }

    public Move[] getPossibleMovesForARealm(Player player, Dice dice) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        switch (dice.getRealm()) {

            case RED:
                Dragon d = player.getScoreSheet().getDragon();
                for (int i = 0; i < d.getDragonHitBoard().length; i++) {
                    for (int j = 0; j < d.getDragonHitBoard()[i].length; j++) {
                        if (d.getDragonHitBoard()[i][j] != 0) {
                            RedDice r = new RedDice(d.getDragonHitBoard()[i][j]);
                            r.selectsDragon(i + 1);
                            possibleMoves.add(new Move(r, d));
                        }
                    }
                }
                break;
            case GREEN:
                Guardians g = player.getScoreSheet().getGuardians();
                String[][] tempGuardians = g.getguardians_hitValues();
                for (int i = 0; i < g.getguardians_hitValues().length; i++) {
                    for (int j = 0; j < g.getguardians_hitValues()[i].length; j++) {
                        if ((isNumeric(tempGuardians[i][j]))) {
                            int x;
                            if (g.getguardians_values()[i][j] > 6) {
                                x = 6;
                            } else {
                                x = 1;
                            }
                            GreenDice gd = new GreenDice(x);
                            gd.setWhite(new ArcanePrism(g.getguardians_values()[i][j] - x));
                            possibleMoves.add(new Move(gd, g));// TODO: green implementation
                        }
                    }
                }
                break;
            case BLUE:
                Hydra h = player.getScoreSheet().getHydra();
                for (int i = 0; i < 6 - h.getCurrenthead(); i++) {
                    possibleMoves.add(new Move(new BlueDice(6 - i), h));
                }
                break;
            case MAGENTA:
                Phoenix p = player.getScoreSheet().getPhoenix();
                int counter;
                // if (p.Acceptable((MagentaDice)dice)) {
                counter = p.getCounter() % 6;
                int y = 6 - counter;
                for (int i = 0; i < y; i++) {
                    possibleMoves.add(new Move(new MagentaDice(counter + 1), p));
                    counter++;
                }
                // }
                break;
            case YELLOW:
                Lion l = player.getScoreSheet().getLion();
                if (l.getCounter() < 11) {
                    for (int i = 0; i < 6; i++) {
                        possibleMoves.add(new Move(new YellowDice(i + 1), l));
                    }
                }
                break;
            case WHITE:
                return getAllPossibleMoves(player);
        }
        Move[] allPossibleMoves = new Move[possibleMoves.size()];
        for (int i = 0; i < allPossibleMoves.length; i++) {
            allPossibleMoves[i] = possibleMoves.get(i);
        }

        return allPossibleMoves;
    }

    public Reward[] getReward(Player activePlayer, Move move) {
        Creature c = move.getTarget();
        return c.getReward();
    }

    @Override
    public boolean switchPlayer() {
        try {
            board.getPlayer1().switchPlayerStatus();
            board.getPlayer2().switchPlayerStatus();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Dice[] rollDice() {
        board.roll();
        return getAllDice();
    }

    @Override
    public Dice[] getAvailableDice() {
        int j = 0;
        for (int i = 0; i < getAllDice().length; i++) {
            if ((!getAllDice()[i].isForgotten()) && (!getAllDice()[i].isUsed())) {
                j++;
            }
        }
        Dice[] available = new Dice[j];
        int k = 0;
        for (int i = 0; i < 6; i++) {
            if ((!getAllDice()[i].isForgotten()) && (!getAllDice()[i].isUsed())) {
                available[k] = getAllDice()[i];
                k++;
            }
        }
        return available;
    }

    @Override
    public Dice[] getAllDice() {
        return board.getDice();
    }

    public Dice[] getArcaneBoostDice() {
        int j = 0;
        for (int i = 0; i < getAllDice().length; i++) {
            if (!getAllDice()[i].isArcaneBoosted()) {
                j++;
            }
        }
        Dice[] arcane = new Dice[j];
        int k = 0;
        for (int i = 0; i < 6; i++) {
            if (!getAllDice()[i].isArcaneBoosted()) {
                arcane[k] = getAllDice()[i];
                k++;
            }
        }
        return arcane;
    }

    @Override
    public Dice[] getForgottenRealmDice() {
        // Dice[] d = getAllDice();
        int j = 0;
        for (int i = 0; i < getAllDice().length; i++) {
            if (getAllDice()[i].isForgotten()) {
                j++;
            }
        }
        Dice[] forgotten = new Dice[j];
        int k = 0;
        for (int i = 0; i < 6; i++) {
            if (getAllDice()[i].isForgotten()) {
                forgotten[k] = getAllDice()[i];
                k++;
            }
        }
        return forgotten;
    }

    @Override
    public Move[] getAllPossibleMoves(Player player) {
        ArrayList<Move> possibleMoves = new ArrayList<Move>();

        Dragon d = player.getScoreSheet().getDragon();
        for (int i = 0; i < d.getDragonHitBoard().length; i++) {
            for (int j = 0; j < d.getDragonHitBoard()[i].length; j++) {
                if (d.getDragonHitBoard()[i][j] != 0) {
                    RedDice r = new RedDice(d.getDragonHitBoard()[i][j]);
                    r.selectsDragon(i + 1);
                    possibleMoves.add(new Move(r, d));
                }
            }
        }

        Guardians g = player.getScoreSheet().getGuardians();
        for (int i = 0; i < g.getguardians_hitValues().length; i++) {
            for (int j = 0; j < g.getguardians_hitValues()[i].length; j++) {
                if (g.getguardians_hitValues()[i][j] != "X") {
                    int x;
                    if (g.getguardians_values()[i][j] > 6) {
                        x = 6;
                    } else {
                        x = 1;
                    }
                    GreenDice gd = new GreenDice(x);
                    gd.setWhite(new ArcanePrism(g.getguardians_values()[i][j] - x));
                    possibleMoves.add(new Move(gd, g));// TODO: green implementation
                }
            }
        }

        Hydra h = player.getScoreSheet().getHydra();
        int counter = h.getIndex();
        for (int i = 0; i < 6 - h.getIndex(); i++) {
            possibleMoves.add(new Move(new BlueDice(6 - i), h));
        }

        Phoenix p = player.getScoreSheet().getPhoenix();
        if (p.Acceptable((MagentaDice) board.getDice()[3])) {
            counter = p.getCounter() % 6;
            int y = 6 - counter;
            for (int i = 0; i < y; i++) {
                possibleMoves.add(new Move(new MagentaDice(counter + 1), p));
                counter++;
            }
        }

        Lion l = player.getScoreSheet().getLion();
        if (l.getCounter() < 11) {
            for (int i = 0; i < 6; i++) {
                possibleMoves.add(new Move(new YellowDice(i + 1), l));
            }
        }

        Move[] allPossibleMoves = new Move[possibleMoves.size()];
        for (int i = 0; i < allPossibleMoves.length; i++) {
            allPossibleMoves[i] = possibleMoves.get(i);
        }

        return allPossibleMoves;

    }

    @Override
    public Move[] getPossibleMovesForAvailableDice(Player player) {
        Dice[] availableDice = getAvailableDice();
        ArrayList<Move> possibleMoves = new ArrayList<>();

        Move move1;
        for (Dice value : availableDice) {
            Move[] DieMoves = getPossibleMovesForADie(player, value);
            Collections.addAll(possibleMoves, DieMoves);
        }

        for (int i = 0; i < possibleMoves.size(); i++) {
            for (int j = i + 1; j < possibleMoves.size(); j++) {
                if ((possibleMoves.get(i)).compareTo(possibleMoves.get(j)) == 0) {
                    possibleMoves.remove(j);
                }
            }
        }

        //another implentation rather than changing all of them null bas actually howa beye3melohom sort !!!!!
        //hence howa lazem aghayaro internally!
        //i cant just add them to the string

        for (int i = 0; i < possibleMoves.size(); i++) {
            if (possibleMoves.get(i).getTarget() instanceof Dragon) {
                RedDice d = (RedDice) possibleMoves.get(i).getDice();
                RedDice dice = new RedDice(d);
                // dice.selectsDragon(0);
                move1 = new Move(dice, possibleMoves.get(i).getTarget());
                possibleMoves.set(i, move1);
            }
        }
        // TODO: i did this cuzzzz i dont wanna change the the reddice of the actual
        // gameboard

        Move[] allPossibleMoves = new Move[possibleMoves.size()];
        for (int j = 0; j < allPossibleMoves.length; j++) {
            allPossibleMoves[j] = possibleMoves.get(j);
        }
        return allPossibleMoves;
    }

    public Move[] getPossibleMovesForForgottenDice(Player player) {
        Dice[] availableDice = getForgottenRealmDice();
        ArrayList<Move> possibleMoves = new ArrayList<>();
        for (Dice dice : availableDice) {
            Move[] DieMoves = getPossibleMovesForADie(player, dice);
            Collections.addAll(possibleMoves, DieMoves);
        }

        for (int i = 0; i < possibleMoves.size(); i++) {
            for (int j = i + 1; j < possibleMoves.size(); j++) {
                if ((possibleMoves.get(i)).compareTo(possibleMoves.get(j)) == 0) {
                    possibleMoves.remove(j);
                }
            }
        }

        Move[] allPossibleMoves = new Move[possibleMoves.size()];
        for (int j = 0; j < allPossibleMoves.length; j++) {
            allPossibleMoves[j] = possibleMoves.get(j);
        }
        return allPossibleMoves;
    }

    @Override
    // farah's method
    public Move[] getPossibleMovesForADie(Player player, Dice dice) {

        switch (dice.getRealm()) {
            case RED:
                return player.getScoreSheet().getDragon().getPossibleMovesForADie(dice);
            case YELLOW:
                return player.getScoreSheet().getLion().getPossibleMovesForADie(dice);
            case BLUE:
                return player.getScoreSheet().getHydra().getPossibleMovesForADie(dice);
            case MAGENTA:
                return player.getScoreSheet().getPhoenix().getPossibleMovesForADie(dice);
            case GREEN:
                GreenDice gd = (GreenDice) dice;
                gd.setWhite(board.getWhite());

                return player.getScoreSheet().getGuardians().getPossibleMovesForADie(gd);
            case WHITE:
                GreenDice g = new GreenDice(dice.getValue(),Realm.WHITE);
                g.setWhite(board.getGreen().getGreenValue());
                Move[] a = player.getScoreSheet().getDragon().getPossibleMovesForADie(new RedDice(dice.getValue(),Realm.WHITE));
                Move[] b = player.getScoreSheet().getGuardians().getPossibleMovesForADie(g); // TODO:
                Move[] c = player.getScoreSheet().getHydra().getPossibleMovesForADie(new BlueDice(dice.getValue(),Realm.WHITE));
                Move[] d = player.getScoreSheet().getPhoenix()
                        .getPossibleMovesForADie(new MagentaDice(dice.getValue(),Realm.WHITE));
                Move[] e = player.getScoreSheet().getLion().getPossibleMovesForADie(new YellowDice(dice.getValue(),Realm.WHITE));
                Move[] m = new Move[a.length + b.length + c.length + d.length + e.length];
                int x = 0;
                for (Move move : a) {
                    m[x] = move;
                    x++;
                }
                for (Move move : b) {
                    m[x] = move;
                    x++;
                }
                for (Move move : c) {
                    m[x] = move;
                    x++;
                }
                for (Move move : d) {
                    m[x] = move;
                    x++;
                }
                for (Move move : e) {
                    m[x] = move;
                    x++;
                }
                return m;
        }
        return new Move[0];
    }

    @SuppressWarnings("SuspiciousListRemoveInLoop")
    public Move[] getPossibleArcaneMoves(Player player) {
        Dice[] availableDice = getArcaneBoostDice();
        ArrayList<Move> possibleMoves = new ArrayList<>();
        for (Dice dice : availableDice) {
            Move[] DieMoves = getPossibleMovesForADie(player, dice);
            Collections.addAll(possibleMoves, DieMoves);
        }

        for (int i = 0; i < possibleMoves.size(); i++) {
            for (int j = i + 1; j < possibleMoves.size(); j++) {
                if ((possibleMoves.get(i)).compareTo(possibleMoves.get(j)) == 0) {
                    possibleMoves.remove(j);
                }
            }
        }

        Move[] allPossibleMoves = new Move[possibleMoves.size()];
        for (int j = 0; j < allPossibleMoves.length; j++) {
            allPossibleMoves[j] = possibleMoves.get(j);
        }
        return allPossibleMoves;
    }

    @Override
    public GameBoard getGameBoard() {
        return board;
    }

    @Override
    public Player getActivePlayer() {
        if (board.getPlayer1().getPlayerStatus() == PlayerStatus.ACTIVE) {
            return board.getPlayer1();
        }
        return board.getPlayer2();
    }

    @Override
    public Player getPassivePlayer() {
        if (board.getPlayer1().getPlayerStatus() == PlayerStatus.ACTIVE) {
            return board.getPlayer2();
        }
        return board.getPlayer1();
    }

    @Override
    public ScoreSheet getScoreSheet(Player player) {
        return player.getScoreSheet();
    }

    @Override
    public GameStatus getGameStatus() {
        return board.getStatus();
    }

    @Override
    public GameScore getGameScore(Player player) {
        return player.getGameScore();
    }

    @Override
    public TimeWarp[] getTimeWarpPowers(Player player) {
        return player.getScoreSheet().getTimeWarp();
    }

    @Override
    public ArcaneBoost[] getArcaneBoostPowers(Player player) {
        return player.getScoreSheet().getArcaneBoost();
    }

    @Override
    public boolean selectDice(Dice dice, Player player) {
        try {
            if (notBonus) {
                dice.use();
            }
            switch (board.getStatus().getTurn()) {
                case P1AT1:
                case P2AT1:
                    player.getUsedDice()[0] = dice;
                    break;

                case P1AT2:
                case P2AT2:
                    player.getUsedDice()[1] = dice;
                    break;

                case P1AT3:
                case P2AT3:
                    player.getUsedDice()[2] = dice;
                    break;

                default:
                    break;
            }
            for (int i = 0; i < 6; i++) {
                if (getAllDice()[i].getValue() < dice.getValue()) {
                    getAllDice()[i].forget();
                }
            }
            return true;
        } catch (Exception e) {
            // TODO
            return false;
        }
    }

    @Override
    public boolean makeMove(Player player, Move move) {
        boolean b = false;
        try {
            ScoreSheet ss = player.getScoreSheet();
            Dice dice = move.getDice();
            if (dice instanceof RedDice) {
                b = ss.getDragon().makeMove(dice);
                updateScore(player, ss.getDragon());
                Reward[] reward = ss.getDragon().getReward();
                if (reward.length == 2) {
                    if (reward[0].compareTo(reward[1]) > 0) {
                        handleReward(reward[0], player);
                        handleReward(reward[1], player);// TODO: el mshkela hena
                    } else {
                        handleReward(reward[1], player);
                        handleReward(reward[0], player);// TODO el mshkela hena
                    }
                } else if (reward.length == 1) {
                    handleReward(reward[0], player);
                }
                // Reward[] tempRewards = ss.getDragon().getReward3matan();
                // for (int i = 0; i < tempRewards.length; i++) {
                // handleElemental(tempRewards[i], player);
                // System.out.println("elemental handled with red");//TODO:Fix ino yeraga3 all
                // rewards cuz incase
                // // le3eb fel config files!!
                // //TODO: r we prepared lw ghayar an elemental crest in any other realm?
                // }

            } else if (dice instanceof GreenDice) {
                if(notBonus) {
                    ArcanePrism a = (ArcanePrism) board.getDice()[5];
                    ((GreenDice) dice).setWhite(a);
                }
                else {
                    ((GreenDice) dice).setWhite(0);
                }
                b = ss.getGuardians().makeMove(dice);
                updateScore(player, ss.getGuardians());
                Reward[] reward = ss.getGuardians().getReward();
                if (reward.length == 2) {
                    if (reward[0].compareTo(reward[1]) > 0) {
                        handleReward(reward[0], player);
                        handleReward(reward[1], player);// TODO: el mshkela hena
                    } else {
                        handleReward(reward[1], player);
                        handleReward(reward[0], player);// TODO el mshkela hena
                    }
                } else if (reward.length == 1) {
                    handleReward(reward[0], player);
                }
                // Reward[] tempRewards = ss.getGuardians().getReward3matan();
                // for (int i = 0; i < tempRewards.length; i++) {
                // handleElemental(tempRewards[i], player);
                // System.out.println("elemental handled with green");
                // }
            } else if (dice instanceof BlueDice) {
                b = ss.getHydra().makeMove(dice);
                updateScore(player, ss.getHydra());
                Reward[] reward = ss.getHydra().getReward();
                if (reward.length == 1) {
                    handleReward(reward[0], player);
                }
            } else if (dice instanceof MagentaDice) {
                b = ss.getPhoenix().makeMove(dice);
                updateScore(player, ss.getPhoenix());
                Reward[] reward = ss.getPhoenix().getReward();
                if (reward.length == 1) {
                    handleReward(reward[0], player);
                }
            } else if (dice instanceof YellowDice) {
                b = ss.getLion().makeMove(dice);
                updateScore(player, ss.getLion());
                Reward[] reward = ss.getLion().getReward();
                if (reward.length == 1) {
                    handleReward(reward[0], player);
                }
            } else if (dice instanceof ArcanePrism) {
                if (notBonus) {
                    getAllDice()[5].use();
                }
                // Scanner sc = new Scanner(System.in);
                // System.out.println("Choose a color to cast your Arcane Prism into.");
                // System.out.println("1-5");
                // System.out.println("1.Red 2.Green 3.Blue 4.Magenta 5.Yellow");
                // int x = sc.nextInt();
                // sc.close();
                Creature x = move.getTarget();
                System.out.println("u have the creature as " + x.getClass().getSimpleName());
                if (x instanceof Dragon) {

                    System.out.println("YA FALHOOSA WESELTY EL ARCANE RED");// TODO:do a backup
                    // RedDice r = (RedDice) move.getDice();
                    // RedDice actualDice = new RedDice(dice.getValue());
                    // actualDice.selectsDragon(r.getDragonNumberInteger());
                    // b = ss.getDragon().makeMove(actualDice);
                    // updateScore(player, ss.getDragon());
                    // Reward[] reward = ss.getGuardians().getReward();
                    // if (reward.length == 2) {
                    // if (reward[0].compareTo(reward[1]) > 0) {
                    // handleReward(reward[0], getActivePlayer());
                    // handleReward(reward[1], getActivePlayer());// TODO: el mshkela hena
                    // } else {
                    // handleReward(reward[1], getActivePlayer());
                    // handleReward(reward[0], getActivePlayer());// TODO el mshkela hena
                    // }
                    // } else if (reward.length == 1) {
                    // handleReward(reward[0], getActivePlayer());
                    // }
                    // Reward[] tempRewards = ss.getDragon().getReward();
                    // for (int i = 0; i < tempRewards.length; i++) {
                    // handleElemental(tempRewards[i], player);
                    // }
                } else if (x instanceof Guardians) {
                    if(notBonus) {
                        GreenDice g = (GreenDice) board.getDice()[1];
                        ArcanePrism myarcane = new ArcanePrism(g.getGreenValue());
                        GreenDice actualDice = new GreenDice(dice.getValue());
                        actualDice.setWhite(myarcane);
                        b = ss.getGuardians().makeMove(actualDice);
                    }
                    else
                    {b = ss.getGuardians().makeMove(dice);}
                    updateScore(player, ss.getGuardians());
                    Reward[] reward = ss.getGuardians().getReward();
                    if (reward.length == 2) {
                        if (reward[0].compareTo(reward[1]) > 0) {
                            handleReward(reward[0], player);
                            handleReward(reward[1], player);// TODO: el mshkela hena
                        } else {
                            handleReward(reward[1], player);
                            handleReward(reward[0], player);// TODO el mshkela hena
                        }
                    } else if (reward.length == 1) {
                        handleReward(reward[0], player);
                    }
                    // Reward[] tempRewards = ss.getDragon().getReward();
                    // for (int i = 0; i < tempRewards.length; i++) {
                    // handleElemental(tempRewards[i], player);
                    // }
                } else if (x instanceof Hydra) {
                    b = ss.getHydra().makeMove(dice);
                    updateScore(player, ss.getHydra());
                    Reward[] reward = ss.getHydra().getReward();
                    if (reward.length == 1) {
                        handleReward(reward[0], player);
                    }
                } else if (x instanceof Phoenix) {
                    b = ss.getPhoenix().makeMove(new MagentaDice(dice.getValue()));
                    updateScore(player, ss.getPhoenix());
                    Reward[] reward = ss.getPhoenix().getReward();
                    if (reward.length == 1) {
                        handleReward(reward[0], player);
                    }
                } else if (x instanceof Lion) {
                    b = ss.getLion().makeMove(dice);
                    updateScore(player, ss.getLion());
                    Reward[] reward = ss.getLion().getReward();
                    if (reward.length == 1) {
                        handleReward(reward[0], player);
                    }
                    // } else {
                    // while (x > 5 || x < 1) {
                    // sc = new Scanner(System.in);
                    // System.out.println("Wrong selection");
                    // System.out.println("Choose a color to cast your Arcane Prism into.");
                    // System.out.println("1-5");
                    // System.out.println("1.Red 2.Green 3.Blue 4.Magenta 5.Yellow");
                    // x = sc.nextInt();
                    // sc.close();
                    // if (x == 1) {
                    // b = ss.getDragon().makeMove(dice);
                    // updateScore(player, ss.getDragon());
                    // break;
                    // } else if (x == 2) {
                    // b = ss.getGuardians().makeMove(dice);
                    // updateScore(player, ss.getGuardians());
                    // break;
                    // } else if (x == 3) {
                    // b = ss.getHydra().makeMove(dice);
                    // updateScore(player, ss.getHydra());
                    // break;
                    // } else if (x == 4) {
                    // b = ss.getPhoenix().makeMove(dice);
                    // updateScore(player, ss.getPhoenix());
                    // break;
                    // } else if (x == 5) {
                    // b = ss.getLion().makeMove(dice);
                    // updateScore(player, ss.getLion());
                    // break;
                    // }
                    // }
                    // }
                }
            }

            if (b && notBonus) {
                Dice[] avail = getAvailableDice();
                for (Dice value : avail) {
                    if ((move.getDice() instanceof GreenDice)) {
                        if (value instanceof GreenDice) {// but in the case of an arcane prism here
                            if (((GreenDice) move.getDice()).getGreenValue() > ((GreenDice) value).getGreenValue()) {
                                value.forget();
                            }
                        } else if (((GreenDice) move.getDice()).getGreenValue() > value.getValue()) {
                            value.forget();
                        }

                    } else {// TODO:Whats happening here
                        if (value instanceof GreenDice) {
                            if (move.getDice().getValue() > ((GreenDice) value).getGreenValue()) {
                                value.forget();
                            }
                        } else if (move.getDice().getValue() > value.getValue()) {
                            value.forget();
                        }
                    }
                } // TODO:is white and green dilemma handled here or not

            }

            return b;

        }

        catch (InvalidMoveException e) {
            // TODO:worst comes to worst just get available dice and print their possible
            // moves lol
            // by printing getpossibleforaDIE
            Move[] m = getPossibleMovesForAvailableDice(player);
            System.out.println("Choose a move from the following:");
            for (Move value : m) {
                System.out.println(value);
            }
            Scanner sc = new Scanner(System.in);
            String f = sc.nextLine();
            while (!isNumeric(f) || Integer.parseInt(f) > m.length || Integer.parseInt(f) < 1) {
                System.out.println("Invalid input. Please enter a number from 1 to 6.");
                f = sc.nextLine();
            }
            sc.close();
            return makeMove(player, m[Integer.parseInt(f)]);
        } catch (PlayerActionException e) {
            System.out.println("Choose a dragon between 1 and 4");
            return makeMove(player, move);
        }
    }

    public void setValue(Dice d) {
        // TODO:zabaty set value 3shan te3mel hesab el green wel red!!
    }

    public void updateScore(Player p, Creature c) {
        switch (c.getClass().getSimpleName()) {
            case "Dragon":
                p.getGameScore().setRedRealmScore(c.getScore());
                break;
            case "Guardians":
                p.getGameScore().setGreenRealmScore(c.getScore());
                break;
            case "Hydra":
                p.getGameScore().setBlueRealmScore(c.getScore());
                break;
            case "Phoenix":
                p.getGameScore().setMagentaRealmScore(c.getScore());
                break;
            case "Lion":
                p.getGameScore().setYellowRealmScore(c.getScore());
                break;
            default:
                break;
        }
    }

    public String printGameScore(Player p) {
        // String s = System.out.println(p.getGameScore()); WHY IS THIS NOT WORKING
        String s = p.getGameScore().toString();
        s = p.getName() + "'s Game Score:\n" + s;
        s += "| Crests  |" + "     " + p.getScoreSheet().getElementalCrest() + " |\n";
        s += "+---------+-------+\n";
        s += "| Total   |    ";
        // int[] theRealmScores = new int[5];
        s += (p.getGameScore().getTotal());
        if (p.getGameScore().getTotal() < 10) {
            s += "  |\n";
        } else if(p.getGameScore().getTotal()<100) {
            s += " |\n";
        }
        else s+="|\n";
        s += "+---------+-------+\n";
        return s;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            @SuppressWarnings("unused")
            double d = Double.parseDouble(strNum);
            // TODO:suppressed a warning here
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void handleRewardAI(Reward reward, Player p) {
        if (reward == null)
            return;
        notBonus = false;
        Scanner sc = new Scanner(System.in);
        switch (reward.getClass().getSimpleName()) {
            case "ArcaneBoost":
                p.getScoreSheet().incrementArcaneBoost();
                System.out.println("You got an arcane boost!");
                // TODO:ya3ni lw gatlo w howa passive hayeyhsal eh
                break;
            case "TimeWarp":
                p.getScoreSheet().incrementTimeWarp();
                System.out.println("You got a time warp!");
                break;
            case "ElementalCrest":
                p.getScoreSheet().incrementElementalCrest();
                p.getGameScore().incrementElementalCrest();
                System.out.println("You got an elemental crest!");
                break;
            case "Bonus":
                Bonus b = (Bonus) reward;
                boolean moveMade = false;
                switch (b.getColor()) {
                    case RED:
                        moveMade = false;
                        while (!moveMade) {
                            if (getPossibleMovesForARealm(p, new RedDice()).length > 0) {
                                System.out.println("BONUS TIME!!!");
                                int[] value = new int[2];
                                Move tempmove = null;
                                int finalvalue = 0;
                                int tempvalue = 0;
                                RedDice d1 = new RedDice(1);
                                Move[] dd1 = getPossibleMovesForADie(p,d1);
                                for (int j = 0; j < dd1.length; j++) {
                                    value[j] = dd1[j].checkPoints() + dd1[j].checkBonus();
                                }
                                Move finalmove = dd1[0];
                                for (int j = 1; j < dd1.length; j++) {
                                    if (value[j] > value[j-1]) {
                                        finalmove = dd1[j];
                                        finalvalue=value[j];}}
                                int finaldicevalue=1;

                                for (int i=2; i<7 ; i++){
                                    RedDice d = new RedDice(i);
                                    Move[] dd = getPossibleMovesForADie(p, d);
                                    for (int j = 0; j < dd.length; j++) {
                                        value[j] = dd[j].checkPoints() + dd[j].checkBonus();
                                    }
                                    for (int j = 1; j < dd.length; j++) {
                                        if (value[j] > value[j-1]) {
                                            tempmove = dd[j];
                                            tempvalue=value[j];}}
                                    if(tempvalue > finalvalue){
                                        finalmove=tempmove;
                                        finalvalue=tempvalue;
                                        finaldicevalue=i;
                                    }
                                }
                                RedDice finald = new RedDice(finaldicevalue);
                                RedDice reddice=(RedDice) finalmove.getDice();
                                finald.selectsDragon(reddice.getDragonNumberInteger());
                                moveMade = makeMove(p, new Move(finald, p.getScoreSheet().getCreatureByRealm(finald)));
                                System.out.println(p.getScoreSheet().getCreatureByRealm(finald));
                                    }

                            else {
                                System.out.println("The AI got a red bonus but has no possible moves");
                            }
                        }
                        break;

                    case YELLOW:
                        moveMade = false;
                        while (!moveMade) {
                            if (getPossibleMovesForARealm(p, new YellowDice()).length > 0) {
                                System.out.println("BONUS TIME!!!");
                                YellowDice d = new YellowDice(6);
                                moveMade = makeMove(p, new Move(d, p.getScoreSheet().getCreatureByRealm(d)));
                                System.out.println(p.getScoreSheet().getCreatureByRealm(d));
                            } else {
                                System.out.println("The AI got a yellow bonus but has no possible moves");
                            }
                        }
                        break;
                    case BLUE:
                        moveMade = false;
                        while (!moveMade) {
                            if (getPossibleMovesForARealm(p, new BlueDice()).length > 0) {
                                System.out.println("BONUS TIME!!!");
                                BlueDice d = new BlueDice(6);
                                moveMade = makeMove(p, new Move(d, p.getScoreSheet().getCreatureByRealm(d)));
                                System.out.println(p.getScoreSheet().getCreatureByRealm(d));
                            } else {
                                System.out.println("The AI got a blue bonus but ha no possible moves");
                            }
                        }
                        break;
                    case MAGENTA:
                        moveMade = false;
                        while (!moveMade) {
                            if (getPossibleMovesForARealm(p, new MagentaDice()).length > 0) {
                                System.out.println("BONUS TIME!!!");
                                MagentaDice d = new MagentaDice(6);
                                moveMade = makeMove(p, new Move(d, p.getScoreSheet().getCreatureByRealm(d)));
                                System.out.println(p.getScoreSheet().getCreatureByRealm(d));
                            } else {
                                System.out.println("You got a magenta bonus but you have no possible moves");
                            }
                        }
                        break;
                    case GREEN:
                        moveMade = false;
                        while (!moveMade) {
                            if (getPossibleMovesForARealm(p, new GreenDice()).length > 0) {
                                System.out.println("BONUS TIME!!!");
                                int[] value = new int[2];
                                Move tempmove = null;
                                int finalvalue = 0;
                                int tempvalue = 0;
                                GreenDice d1 = new GreenDice(1);
                                Move[] dd1 = getPossibleMovesForADie(p,d1);
                                if (dd1.length == 0) {
                                    break;}
                                for (int j = 0; j < dd1.length; j++) {
                                    value[j] = dd1[j].checkPoints() + dd1[j].checkBonus();
                                }


                                Move finalmove = dd1[0];
                                for (int j = 1; j < dd1.length; j++) {
                                    if (value[j] > value[j-1]) {
                                        finalmove = dd1[j];
                                        finalvalue=value[j];}}
                                int finaldicevalue=1;

                                for (int i=2; i<7 ; i++){
                                    GreenDice d = new GreenDice(i);
                                    Move[] dd = getPossibleMovesForADie(p, d);
                                    if (dd.length == 0) {
                                        continue;
                                    }
                                    for (int j = 0; j < dd.length; j++) {
                                        value[j] = dd[j].checkPoints() + dd[j].checkBonus();
                                    }
                                    for (int j = 1; j < dd.length; j++) {
                                        if (value[j] > value[j-1]) {
                                            tempmove = dd[j];
                                            tempvalue=value[j];}}
                                    if(tempvalue > finalvalue){
                                        finalmove=tempmove;
                                        finalvalue=tempvalue;
                                        finaldicevalue=i;
                                    }
                                }
                                GreenDice finald = new GreenDice(finaldicevalue);
                                moveMade = makeMove(p, new Move(finald, p.getScoreSheet().getCreatureByRealm(finald)));
                                System.out.println(p.getScoreSheet().getCreatureByRealm(finald));
                            } else {
                                System.out.println("You got a green bonus but you have no possible moves");
                            }
                        }
                        break;
                    case WHITE:
                        moveMade = false;
                        while (!moveMade) {
                            if (getAllPossibleMoves(p).length > 0) {
                                System.out.println("BONUS TIME!!!");
                                if (getPossibleMovesForARealm(p, new MagentaDice()).length > 0) {
                                    MagentaDice d = new MagentaDice(6);
                                    moveMade = makeMove(p, new Move(d, p.getScoreSheet().getCreatureByRealm(d)));
                                    System.out.println(p.getScoreSheet().getCreatureByRealm(d));

                                } else if (getPossibleMovesForARealm(p, new YellowDice()).length > 0) {
                                    System.out.println("BONUS TIME!!!");
                                    YellowDice d = new YellowDice(6);
                                    moveMade = makeMove(p, new Move(d, p.getScoreSheet().getCreatureByRealm(d)));
                                    System.out.println(p.getScoreSheet().getCreatureByRealm(d));

                                } else if (getPossibleMovesForARealm(p, new BlueDice()).length > 0) {
                                    System.out.println("BONUS TIME!!!");
                                    BlueDice d = new BlueDice(6);
                                    moveMade = makeMove(p, new Move(d, p.getScoreSheet().getCreatureByRealm(d)));
                                    System.out.println(p.getScoreSheet().getCreatureByRealm(d));

                                }
                            } else {
                                System.out.println("You got an essence bonus but you have no possible moves");
                                break;
                            }
                        }
                        break;
                }

            default:
                break;
        }
        notBonus = true;
    }

    public Move[] getAIMoves12() {
        ArrayList<Move> move = new ArrayList<Move>();
        Move[] m = null;
        for (int i = 0; i < getAvailableDice().length; i++) {
            m = getPossibleMovesForADie(board.getPlayer2(), getAvailableDice()[i]);
            move.addAll(Arrays.asList(m));
        }
        Move[] sorting = new Move[move.size()];
        for (int i = 0; i < move.size(); i++) {
            sorting[i] = move.get(i);
        }
        Stream<Move> moves = Arrays.stream(sorting).sorted((o1, o2) -> {
            if (o1.getDice().getValue() == o2.getDice().getValue()) {
                return o2.getDice().getRealm().ordinal() -
                        o1.getDice().getRealm().ordinal();
            }
            return o1.getDice().getValue() -
                    o2.getDice().getValue();
        });
        Object[] movesArray = moves.toArray();
        Move[] possible = new Move[movesArray.length];
        for (int i = 0; i < movesArray.length; i++) {
            possible[i] = ((Move) movesArray[i]);
        }
        return possible;
    }

    public Move MoveChooser3p(int round) throws TimeWarpException {
        ArrayList<Move> move = new ArrayList<Move>();
        Move[] m = null;
        Dice[] d= getAvailableDice();
        for (int i = 0; i < d.length; i++) {
            m = getPossibleMovesForADie(board.getPlayer2(),d[i]);
            move.addAll(Arrays.asList(m));
        }
        if (move.isEmpty()) {
            return null;
        }
        Move move3ps;
        int[] diceValue = new int[move.size()];
        for (int j = 0; j < move.size(); j++) {
            diceValue[j] = move.get(j).checkPoints() + move.get(j).checkBonus();
        }
        move3ps = move.get(0);
        for (int j = 0; j < move.size()-1; j++) {
            if (diceValue[j+1] > diceValue[j]) {
                move3ps = move.get(j+1);
            }
        }
        if(round <= 3){
            return move3ps;
        }
        if (move3ps==null) {
            return null;
        }
        if (getGameStatus().getRoundNumber()==4|| getGameStatus().getRoundNumber()==5|| getGameStatus().getRoundNumber()==6){
            if ((move3ps.checkPoints() + move3ps.checkBonus()) > 8) {
                return move3ps;
            }
            else {
                if(board.getPlayer2().getScoreSheet().timeWarpAvailable()) {
                    throw new TimeWarpException();}
                else {
                    return move3ps;
                }}}
        else {
            return move3ps;
        }


    }





    public Move moveChooserPassive(int round){
        Move[] moves = getPossibleMovesForForgottenDice(board.getPlayer2());
        Move move3ps;
        int[] diceValue = new int[moves.length];
        for (int j = 0; j < moves.length; j++) {
            diceValue[j] = moves[j].checkPoints() + moves[j].checkBonus();
        }
        move3ps = moves[0];
        for (int j = 0; j < moves.length-1; j++) {
            if (diceValue[j+1] > diceValue[j]) {
                move3ps = moves[j+1];
            }
        }
        return move3ps;
    }



    public Move moveChooser12(int round) throws TimeWarpException {
        Move[] moves = getAIMoves12();
        if (moves.length == 0) {
            return null;
        }

        Move move;
        int[] value = new int[moves.length];

        if (round == 1 || round == 2 || round == 6) {
            if (moves.length == 2 || moves.length == 3) {
                for (int j = 0; j < moves.length; j++) {
                    value[j] = moves[j].checkPoints();
                }
                move = moves[0];
                int curr = 0;
                for (int j = 1; j < moves.length; j++) {
                    if (value[j] > value[curr]) {
                        move = moves[j];
                        curr = j;
                    }
                }
                if (move.checkPoints() + move.checkBonus() > 6) {
                    return move;
                }
                if (moves.length == 3) {
                    if (moves[0].getDice().getValue() == moves[1].getDice().getValue() && moves[1].getDice().getValue() == moves[2].getDice().getValue()) {
                        return move;
                    }
                } else {
                    if (moves[0].getDice().getValue() == moves[1].getDice().getValue()) {
                        return move;
                    }
                }

                int lowestDice = 0;
                if (round == 1 || round == 2) {
                    for (int j = 1; j < moves.length; j++) {
                        if (moves[j].getDice().getValue() < moves[lowestDice].getDice().getValue()) {
                            lowestDice = j;
                        }
                    }
                    return moves[lowestDice];
                } else {
                    if (board.getPlayer2().getScoreSheet().timeWarpAvailable()) {
                        throw new TimeWarpException();
                    } else {
                        for (int j = 1; j < moves.length; j++) {
                            if (moves[j].getDice().getValue() < moves[lowestDice].getDice().getValue()) {
                                lowestDice = j;
                            }
                        }
                        return moves[lowestDice];
                    }
                }
            }

            int i = moves.length / 2;
            if (board.getStatus().getTurn() == TurnShift.P2AT2 && !board.getDice()[5].isForgotten()) {
                i = Math.min(moves.length, i + 2);
            }
            value = new int[moves.length];

            for (int j = 0; j < i; j++) {
                value[j] = moves[j].checkPoints();
            }
            move = moves[0];
            int curr = 0;
            for (int j = 1; j < i; j++) {
                if (value[j] > value[curr]) {
                    move = moves[j];
                    curr = j;
                }
            }
            return move;
        } else {
            if (moves.length == 2 || moves.length == 3) {
                for (int j = 0; j < moves.length; j++) {
                    value[j] = moves[j].checkPoints() + moves[j].checkBonus();
                }
                move = moves[0];
                int curr = 0;
                for (int j = 1; j < moves.length; j++) { // Start from 1 to avoid comparing the first element to itself
                    if (value[j] > value[curr]) {
                        move = moves[j];
                        curr = j;
                    }
                }
                if ((move.checkPoints() + move.checkBonus()) > 8) {
                    return move;
                }
                if (moves.length == 3) {
                    if (moves[0].getDice().getValue() == moves[1].getDice().getValue() && moves[1].getDice().getValue() == moves[2].getDice().getValue()) {
                        return move;
                    }
                } else {
                    if (moves[0].getDice().getValue() == moves[1].getDice().getValue()) {
                        return move;
                    }
                }


                if (board.getPlayer2().getScoreSheet().timeWarpAvailable()) {
                    throw new TimeWarpException();
                } else {
                    int lowestDice = 0;
                    for (int j = 1; j < moves.length; j++) { // Ensure the loop doesn't exceed the length
                        if (moves[j].getDice().getValue() < moves[lowestDice].getDice().getValue()) {
                            lowestDice = j;
                        }
                    }
                    return moves[lowestDice];
                }
                //TODO: if no rerolls, choose the lower move
                //TODO: else choose the remaining move
                //TODO: return here
            }

            int i = moves.length / 2;
            if (board.getStatus().getTurn() == TurnShift.P2AT2 && !board.getDice()[5].isForgotten()) {
                i = Math.min(moves.length, i + 2); // Ensure i doesn't exceed moves.length
            }
            value = new int[moves.length];

            for (int j = 0; j < i; j++) {
                value[j] = moves[j].checkPoints() + moves[j].checkBonus();
            }
            move = moves[0];
            int curr = 0;
            for (int j = 1; j < i; j++) { // Start from 1 to avoid comparing the first element to itself
                if (value[j] > value[curr]) {
                    move = moves[j];
                    curr = j;
                }
            }
            return move;
        }

    }

    public boolean makeMoveAI(Move move) {
        boolean b = false;
        try {
            ScoreSheet ss = board.getPlayer2().getScoreSheet();
            Dice dice = move.getDice();
            if (dice instanceof RedDice) {
                b = ss.getDragon().makeMove(dice);
                updateScore(board.getPlayer2(), ss.getDragon());
                Reward[] reward = ss.getDragon().getReward();
                if (reward.length == 2) {
                    if (reward[0].compareTo(reward[1]) > 0) {
                        handleRewardAI(reward[0], board.getPlayer2());
                        handleRewardAI(reward[1], board.getPlayer2());// TODO: el mshkela hena
                    } else {
                        handleRewardAI(reward[1], board.getPlayer2());
                        handleRewardAI(reward[0], board.getPlayer2());// TODO el mshkela hena
                    }
                } else if (reward.length == 1) {
                    handleRewardAI(reward[0], board.getPlayer2());
                }
                // Reward[] tempRewards = ss.getDragon().getReward3matan();
                // for (int i = 0; i < tempRewards.length; i++) {
                // handleElemental(tempRewards[i], player);
                // System.out.println("elemental handled with red");//TODO:Fix ino yeraga3 all
                // rewards cuz incase
                // // le3eb fel config files!!
                // //TODO: r we prepared lw ghayar an elemental crest in any other realm?
                // }

            } else if (dice instanceof GreenDice) {
                if(notBonus) {
                    ArcanePrism a = (ArcanePrism) board.getDice()[5];
                    ((GreenDice) dice).setWhite(a);
                }
                else {
                    ((GreenDice) dice).setWhite(0);
                }
                b = ss.getGuardians().makeMove(dice);
                updateScore(board.getPlayer2(), ss.getGuardians());
                Reward[] reward = ss.getGuardians().getReward();
                if (reward.length == 2) {
                    if (reward[0].compareTo(reward[1]) > 0) {
                        handleRewardAI(reward[0], board.getPlayer2());
                        handleRewardAI(reward[1], board.getPlayer2());// TODO: el mshkela hena
                    } else {
                        handleRewardAI(reward[1], board.getPlayer2());
                        handleRewardAI(reward[0], board.getPlayer2());// TODO el mshkela hena
                    }
                } else if (reward.length == 1) {
                    handleRewardAI(reward[0], board.getPlayer2());
                }
                // Reward[] tempRewards = ss.getGuardians().getReward3matan();
                // for (int i = 0; i < tempRewards.length; i++) {
                // handleElemental(tempRewards[i], player);
                // System.out.println("elemental handled with green");
                // }
            } else if (dice instanceof BlueDice) {
                b = ss.getHydra().makeMove(dice);
                updateScore(board.getPlayer2(), ss.getHydra());
                Reward[] reward = ss.getHydra().getReward();
                if (reward.length == 1) {
                    handleRewardAI(reward[0], board.getPlayer2());
                }
            } else if (dice instanceof MagentaDice) {
                b = ss.getPhoenix().makeMove(dice);
                updateScore(board.getPlayer2(), ss.getPhoenix());
                Reward[] reward = ss.getPhoenix().getReward();
                if (reward.length == 1) {
                    handleRewardAI(reward[0], board.getPlayer2());
                }
            } else if (dice instanceof YellowDice) {
                b = ss.getLion().makeMove(dice);
                updateScore(board.getPlayer2(), ss.getLion());
                Reward[] reward = ss.getLion().getReward();
                if (reward.length == 1) {
                    handleRewardAI(reward[0], board.getPlayer2());
                }
            } else if (dice instanceof ArcanePrism) {
                if (notBonus) {
                    getAllDice()[5].use();
                }
                // Scanner sc = new Scanner(System.in);
                // System.out.println("Choose a color to cast your Arcane Prism into.");
                // System.out.println("1-5");
                // System.out.println("1.Red 2.Green 3.Blue 4.Magenta 5.Yellow");
                // int x = sc.nextInt();
                // sc.close();
                Creature x = move.getTarget();
                System.out.println("u have the creature as " + x.getClass().getSimpleName());
                if (x instanceof Dragon) {

                    System.out.println("YA FALHOOSA WESELTY EL ARCANE RED");// TODO:do a backup
                    // RedDice r = (RedDice) move.getDice();
                    // RedDice actualDice = new RedDice(dice.getValue());
                    // actualDice.selectsDragon(r.getDragonNumberInteger());
                    // b = ss.getDragon().makeMove(actualDice);
                    // updateScore(player, ss.getDragon());
                    // Reward[] reward = ss.getGuardians().getReward();
                    // if (reward.length == 2) {
                    // if (reward[0].compareTo(reward[1]) > 0) {
                    // handleReward(reward[0], getActivePlayer());
                    // handleReward(reward[1], getActivePlayer());// TODO: el mshkela hena
                    // } else {
                    // handleReward(reward[1], getActivePlayer());
                    // handleReward(reward[0], getActivePlayer());// TODO el mshkela hena
                    // }
                    // } else if (reward.length == 1) {
                    // handleReward(reward[0], getActivePlayer());
                    // }
                    // Reward[] tempRewards = ss.getDragon().getReward();
                    // for (int i = 0; i < tempRewards.length; i++) {
                    // handleElemental(tempRewards[i], player);
                    // }
                } else if (x instanceof Guardians) {
                    if(notBonus) {
                        GreenDice g = (GreenDice) board.getDice()[1];
                        ArcanePrism myarcane = new ArcanePrism(g.getGreenValue());
                        GreenDice actualDice = new GreenDice(dice.getValue());
                        actualDice.setWhite(myarcane);
                        b = ss.getGuardians().makeMove(actualDice);
                    }
                    else
                    {b = ss.getGuardians().makeMove(dice);}
                    updateScore(board.getPlayer2(), ss.getGuardians());
                    Reward[] reward = ss.getGuardians().getReward();
                    if (reward.length == 2) {
                        if (reward[0].compareTo(reward[1]) > 0) {
                            handleRewardAI(reward[0], board.getPlayer2());
                            handleRewardAI(reward[1], board.getPlayer2());// TODO: el mshkela hena
                        } else {
                            handleRewardAI(reward[1], board.getPlayer2());
                            handleRewardAI(reward[0], board.getPlayer2());// TODO el mshkela hena
                        }
                    } else if (reward.length == 1) {
                        handleRewardAI(reward[0], board.getPlayer2());
                    }
                    // Reward[] tempRewards = ss.getDragon().getReward();
                    // for (int i = 0; i < tempRewards.length; i++) {
                    // handleElemental(tempRewards[i], player);
                    // }
                } else if (x instanceof Hydra) {
                    b = ss.getHydra().makeMove(dice);
                    updateScore(board.getPlayer2(), ss.getHydra());
                    Reward[] reward = ss.getHydra().getReward();
                    if (reward.length == 1) {
                        handleRewardAI(reward[0], board.getPlayer2());
                    }
                } else if (x instanceof Phoenix) {
                    b = ss.getPhoenix().makeMove(new MagentaDice(dice.getValue()));
                    updateScore(board.getPlayer2(), ss.getPhoenix());
                    Reward[] reward = ss.getPhoenix().getReward();
                    if (reward.length == 1) {
                        handleRewardAI(reward[0], board.getPlayer2());
                    }
                } else if (x instanceof Lion) {
                    b = ss.getLion().makeMove(dice);
                    updateScore(board.getPlayer2(), ss.getLion());
                    Reward[] reward = ss.getLion().getReward();
                    if (reward.length == 1) {
                        handleRewardAI(reward[0], board.getPlayer2());
                    }
                    // } else {
                    // while (x > 5 || x < 1) {
                    // sc = new Scanner(System.in);
                    // System.out.println("Wrong selection");
                    // System.out.println("Choose a color to cast your Arcane Prism into.");
                    // System.out.println("1-5");
                    // System.out.println("1.Red 2.Green 3.Blue 4.Magenta 5.Yellow");
                    // x = sc.nextInt();
                    // sc.close();
                    // if (x == 1) {
                    // b = ss.getDragon().makeMove(dice);
                    // updateScore(player, ss.getDragon());
                    // break;
                    // } else if (x == 2) {
                    // b = ss.getGuardians().makeMove(dice);
                    // updateScore(player, ss.getGuardians());
                    // break;
                    // } else if (x == 3) {
                    // b = ss.getHydra().makeMove(dice);
                    // updateScore(player, ss.getHydra());
                    // break;
                    // } else if (x == 4) {
                    // b = ss.getPhoenix().makeMove(dice);
                    // updateScore(player, ss.getPhoenix());
                    // break;
                    // } else if (x == 5) {
                    // b = ss.getLion().makeMove(dice);
                    // updateScore(player, ss.getLion());
                    // break;
                    // }
                    // }
                    // }
                }
            }

            if (b && notBonus) {
                Dice[] avail = getAvailableDice();
                for (Dice value : avail) {
                    if ((move.getDice() instanceof GreenDice)) {
                        if (value instanceof GreenDice) {// but in the case of an arcane prism here
                            if (((GreenDice) move.getDice()).getGreenValue() > ((GreenDice) value).getGreenValue()) {
                                value.forget();
                            }
                        } else if (((GreenDice) move.getDice()).getGreenValue() > value.getValue()) {
                            value.forget();
                        }

                    } else {// TODO:Whats happening here
                        if (value instanceof GreenDice) {
                            if (move.getDice().getValue() > ((GreenDice) value).getGreenValue()) {
                                value.forget();
                            }
                        } else if (move.getDice().getValue() > value.getValue()) {
                            value.forget();
                        }
                    }
                } // TODO:is white and green dilemma handled here or not

            }

            return b;

        }

        catch (InvalidMoveException e) {
            // TODO:worst comes to worst just get available dice and print their possible
            // moves lol
            // by printing getpossibleforaDIE
            Move[] m = getPossibleMovesForAvailableDice(board.getPlayer2());
            System.out.println("Choose a move from the following:");
            for (Move value : m) {
                System.out.println(value);
            }
            Scanner sc = new Scanner(System.in);
            String f = sc.nextLine();
            while (!isNumeric(f) || Integer.parseInt(f) > m.length || Integer.parseInt(f) < 1) {
                System.out.println("Invalid input. Please enter a number from 1 to 6.");
                f = sc.nextLine();
            }
            sc.close();
            return makeMove(board.getPlayer2(), m[Integer.parseInt(f)]);
        } catch (PlayerActionException e) {
            System.out.println("Choose a dragon between 1 and 4");
            return makeMove(board.getPlayer2(), move);
        }
    }
    public Move[] getPossibleArcaneMovesAI(Player player) {
        Dice[] availableDice = getArcaneBoostDice();
        ArrayList<Move> possibleMoves = new ArrayList<>();
        for (Dice dice : availableDice) {
            Move[] DieMoves = getPossibleMovesForADie(player, dice);
            Collections.addAll(possibleMoves, DieMoves);
        }

        // Remove duplicates
        for (int i = 0; i < possibleMoves.size(); i++) {
            for (int j = i + 1; j < possibleMoves.size(); j++) {
                if ((possibleMoves.get(i)).compareTo(possibleMoves.get(j)) == 0) {
                    possibleMoves.remove(j);
                    j--; // Adjust index after removal
                }
            }
        }

        Move[] allPossibleMoves = possibleMoves.toArray(new Move[0]);

        int[] Value = new int[allPossibleMoves.length];
        ArrayList<Move> highestpossibleMoves = new ArrayList<>();
        for (int j = 0; j < allPossibleMoves.length; j++) {
            Value[j] = allPossibleMoves[j].checkPoints() + allPossibleMoves[j].checkBonus();
            if (Value[j] > 8) {
                highestpossibleMoves.add(allPossibleMoves[j]);
            }
        }

        Move[] arraym = highestpossibleMoves.toArray(new Move[0]);
        int[] Value2 = new int[highestpossibleMoves.size()];
        for (int j = 0; j < highestpossibleMoves.size(); j++) {
            Value2[j] = arraym[j].checkPoints() + arraym[j].checkBonus();
        }

        // Sort highestpossibleMoves based on Value2
        for (int i = 0; i < highestpossibleMoves.size() - 1; i++) {
            for (int j = 0; j < highestpossibleMoves.size() - i - 1; j++) {
                if (Value2[j] < Value2[j + 1]) {
                    int temp = Value2[j];
                    Value2[j] = Value2[j + 1];
                    Value2[j + 1] = temp;
                    Move r = arraym[j];
                    arraym[j] = arraym[j + 1];
                    arraym[j + 1] = r;
                }
            }
        }

        // Sort allPossibleMoves based on Value
        for (int i = 0; i < Value.length - 1; i++) {
            for (int j = 0; j < Value.length - i - 1; j++) {
                if (Value[j] < Value[j + 1]) {
                    int temp = Value[j];
                    Value[j] = Value[j + 1];
                    Value[j + 1] = temp;
                    Move r = allPossibleMoves[j];
                    allPossibleMoves[j] = allPossibleMoves[j + 1];
                    allPossibleMoves[j + 1] = r;
                }
            }
        }

        Move[] finalpossibleMoves;

        if (getGameStatus().getRoundNumber() <= 4 && !highestpossibleMoves.isEmpty()) {
                finalpossibleMoves = new Move[1];
                finalpossibleMoves[0] = arraym[0];
                return finalpossibleMoves;

        } else if (getGameStatus().getRoundNumber() <=5) {
            finalpossibleMoves = new Move[1];
            finalpossibleMoves[0] = allPossibleMoves[0];
            return finalpossibleMoves;
        }

        return new Move[0]; // Return an empty array if no valid move is found
    }}
