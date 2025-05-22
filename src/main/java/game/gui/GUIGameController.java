package game.gui;

//import java.sql.Time;
import java.util.*;


import game.collectibles.ArcaneBoost;
import game.collectibles.Bonus;
import game.collectibles.Reward;
import game.collectibles.TimeWarp;
import game.dice.*;
import game.engine.GameBoard;
import game.engine.GameScore;
import game.engine.GameStatus;
import game.engine.Move;
import game.engine.Player;
import game.engine.PlayerStatus;
import game.engine.ScoreSheet;
import game.engine.TurnShift;
import game.exceptions.InvalidMoveException;
import game.exceptions.PlayerActionException;
import game.creatures.*;
import game.exceptions.TimeWarpException;

import java.util.stream.Stream;

public class GUIGameController extends Thread {


    public static Player currentPlayer; 
    public static String p1;
    public static String p2;



    public static void main(String[] args) {
        MyAppThread appThread = new MyAppThread();
        appThread.start();

        GUIGameController gui = new GUIGameController();
        gui.start();
    }

    @Override
    public void start() {
        startGame("hello");
    }

    private static final GameBoard board = new GameBoard(new Player(new GameScore(), new ScoreSheet()),
            new Player(new GameScore(), new ScoreSheet()), new GameStatus(), new RedDice(), new GreenDice(),
            new BlueDice(), new MagentaDice(), new YellowDice(), new ArcanePrism());
    public static boolean notBonus = true;
    private static volatile boolean eventFired = false;
    private static volatile String userInput = null;
    private static volatile int selectedIndex = -1;
    private static volatile Move moveToBeMade = null;
    public static boolean arcaneTime;

    public static boolean getNotBonus() {
        return notBonus;
    }

    private static synchronized Move waitForMove(){
        moveToBeMade = null;
        while (moveToBeMade == null) {
            try{
                GUIGameController.class.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Move m = moveToBeMade;
        return m;
    }

    // waiting for string
    private static synchronized String waitForInput() {
        userInput = null;
        while (userInput == null) {
            try {
                GUIGameController.class.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        String input = userInput;
        return input;
    }

    // wait for dice roll
    private static void waitForEventToFire() {
        synchronized (GUIGameController.class) {
            try {
                GUIGameController.class.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // wait for button selection
    private static synchronized int waitForSelection() {
        selectedIndex = -1;
        while (selectedIndex == -1) {
            try {
                GUIGameController.class.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        int selection = selectedIndex;
        return selection;
    }

    private static void getInput() {
        do {
            waitForEventToFire();
        } while (!eventFired);
        eventFired = false;
    }

    private static void getInputText() {
        waitForInput();
    }

    private static void getButtonSelection() {
        waitForSelection();
    }

    private static void getMoveToBeMade() {
        waitForMove();
    }

    public static void notifyEventFired() {
        synchronized (GUIGameController.class) {
            eventFired = true;
            GUIGameController.class.notifyAll();
        }
    }

    public static synchronized void setUserInput(String input) {
        userInput = input;
        GUIGameController.class.notifyAll();
    }

    public static synchronized void setSelectedButton(int index) {
        selectedIndex = index;
        GUIGameController.class.notifyAll();
    }

    public static synchronized void setMoveToBeMade(Move move) {
        moveToBeMade = move;
        GUIGameController.class.notifyAll();
    }

    public static GameBoard getBoard() {
        return board;
    }
    public static boolean TimeWarpTime = false;

    //AI Mode
    public static void startGame(int ignoredS) {
        for (int i = 0; i < 6; i++) {
            System.out.println("ROUND " + getGameStatus().getRoundNumber());
            currentPlayer=board.getPlayer1();
            //TODO:currentplayer
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
                getInput();
                rollDice();
                System.out.println("controller roll works");

                Dice[] d = getAvailableDice();
                for (int j = 0; j < getAvailableDice().length; j++) {
                    System.out.println((j + 1) + ". " + d[j]);
                }
                String c;
                System.out.println("hello");
                 TimeWarpTime = true;
                while (board.getPlayer1().getScoreSheet().timeWarpAvailable()) {
                    System.out.println("u have " + board.getPlayer1().getScoreSheet().getTimeWarpCount()
                            + " timewarps, Do you want to reroll? Enter Y for yes or N for no.");
                       

                    getInputText();
                    c = userInput.toUpperCase();
                    setUserInput(null);
                    while (c == null || (!c.equals("Y") && !c.equals("N"))) {
                        System.out.println("Invalid input. Please enter Y for yes or N for no.");
                        getInputText();
                        c = userInput.toUpperCase();
                        setUserInput(null);
                    }
                    if (c.equals("Y")) {
                        board.getPlayer1().getScoreSheet().useTimeWarp();
                        rollDice();
                        System.out.println("Press to roll again");
                        getInput();
                        for (int j = 0; j < getAvailableDice().length; j++) {
                            System.out.println((j + 1) + ". " + d[j]);
                        }
                    } else {
                        break;
                    }
                }

                TimeWarpTime = false;
                // Reward[] reward = new Reward[0];
                if (getPossibleMovesForAvailableDice(board.getPlayer1()).length > 0) {
                    boolean moveMade = false;
                    String turnnumber = "";
                    switch (board.getStatus().getTurn()) {
                        case P1AT1:
                        case P2AT1:
                            turnnumber = "first";
                            break;
                        case P1AT2:
                        case P2AT2:
                            turnnumber = "second";
                            break;
                        case P1AT3:
                        case P2AT3:
                            turnnumber = "third";
                            break;
                        default:
                            System.out.println(board.getStatus().getTurn() + "bayez");
                            break;
                    }
                    String e;
                    Move[] m;
                    Dice dice;
                    Move move;
                    while (!moveMade) {

                        System.out.println(board.getPlayer1().getName() + ", play your " + turnnumber + " turn.");
                        System.out.println("Choose a dice from 1 to " + (getAllDice().length));
                        System.out.println("Choose a dice from 1 to " + (getAvailableDice().length));
                        getButtonSelection();
                        int x = selectedIndex;
                        setSelectedButton(-1);
                        while (x < 0 || x > 5) {

                            System.out.println("Invalid input. Enter a number from 1 to " + (d.length) + ".");
                            getButtonSelection();
                            x = selectedIndex;
                            setSelectedButton(-1);
                        }
                        dice = getAllDice()[x];
                        m = getPossibleMovesForADie(board.getPlayer1(), dice);
                        if (m.length == 1) {
                            if (notBonus) {
                                dice.use();
                            }
                            moveMade = makeMove(board.getPlayer1(), m[0]);
                            // reward = getReward(getActivePlayer(), m[0]);
                            Creature targetedCreature = m[0].getTarget();
                            System.out.println(targetedCreature.getClass().getSimpleName());
                        } else if (m.length == 0) {

                            System.out.println("No available moves for selected dice. Choose another dice.");
                        } else {
                            System.out.println("Choose a move 1-" + m.length);
                            for (int j = 0; j < m.length; j++) {
                                System.out.println((j + 1) + ". " + m[j]);
                            }
                            waitForMove();
                            move = moveToBeMade;
                            setMoveToBeMade(null);//
                            System.out.println("helo again");
                            if (notBonus) {
                                dice.use();
                            }
                            moveMade = makeMove(board.getPlayer1(), move);
                            Creature targetedCreature = move.getTarget();
                            System.out.println(targetedCreature.getClass().getSimpleName());
                        }

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
            //TODO:currentplayer
            currentPlayer=board.getPlayer2();
            if (getPossibleMovesForForgottenDice(board.getPlayer2()).length > 0) {
                boolean moveMade = false;

                System.out.println("Time for " + board.getPlayer2().getName() + "'s passive turn");
                while (!moveMade) {
                    for (int z = 0; z < getForgottenRealmDice().length; z++) {
                        System.out.println((z + 1) + ". " + getForgottenRealmDice()[z]);
                    }
                    System.out.println("Time for the AI's move. Press enter to see the move.");
                    Move chosen = moveChooserPassive(i + 1);

                    moveMade = makeMoveAI(chosen);
                    Creature targetedCreature = chosen.getTarget();
                    System.out.println(targetedCreature.getClass().getSimpleName());
                }
            } else {
                System.out.println("No available moves for passive player.");
            }

            Move[] m;
            Move move;
            String n;
            board.getStatus().endTurn();
            String e;
            // reward = new Reward[0];
            currentPlayer=board.getPlayer1();
            //TODO:CURRENTPLAYER
            while (board.getPlayer1().getScoreSheet().arcaneBoostAvailable()
                    && getPossibleArcaneMoves(board.getPlayer1()).length > 0) {
                System.out.println(
                        board.getPlayer1().getName()
                                + ", do you want to use ArcaneBoost? Enter Y for yes or N for no.");

                getInputText();
                e = userInput.toUpperCase();
                setUserInput(null);
                while (e == null || (!e.equals("Y") && !e.equals("N"))) {
                    System.out.println("Invalid input. Please enter Y for yes or N for no.");
                    getInputText();
                    e = userInput.toUpperCase();
                    setUserInput(null);
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
                        getButtonSelection();
                        int x = selectedIndex;
                        setSelectedButton(-1);
                        while (x < 0 || x > 5) {
                            System.out.println("Invalid input. Enter a number from 1 to "
                                    + (getArcaneBoostDice().length) + ".");
                            getButtonSelection();
                            x = selectedIndex;
                            setSelectedButton(-1);
                        }
                        Dice dice = getAllDice()[x];
                        m = getPossibleMovesForADie(board.getPlayer1(), dice);
                        if (m.length == 1) {
                            if (notBonus) {
                                dice.use();
                            }
                            moveMade = makeMove(board.getPlayer1(),
                                    m[0]);
                            // reward = getReward(getActivePlayer(), m[0]);
                            Creature targetedCreature = m[0].getTarget();
                            System.out.println(targetedCreature.getClass().getSimpleName());
                        } else if (m.length == 0) {

                            System.out.println("No available moves for selected dice. Choose another dice.");
                        } else {
                            System.out.println("Choose a move 1-" + m.length);
                            for (int j = 0; j < m.length; j++) {
                                System.out.println((j + 1) + ". " + m[j]);
                            }
                            waitForMove();
                            move = moveToBeMade;
                            setMoveToBeMade(null);
                            if (notBonus) {
                                dice.use();
                            }
                            moveMade = makeMove(board.getPlayer1(), move);
                            Creature targetedCreature = move.getTarget();
                            System.out.println(targetedCreature.getClass().getSimpleName());

                            // reward = getReward(getActivePlayer(), move);
                            // Creature targetedCreature = move.getTarget();
                            // System.out.println(targetedCreature.getClass().getSimpleName());
                        }

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
            // TODO:arcane boost decision
            //TODO:currentplayer
            currentPlayer = board.getPlayer2();
            while (board.getPlayer2().getScoreSheet().arcaneBoostAvailable()
                    && getPossibleArcaneMoves(getPassivePlayer()).length > 0) {
                System.out.println(
                        getPassivePlayer().getName()
                                + ", do you want to use ArcaneBoost? Enter Y for yes or N for no.");
                getInputText();
                e = userInput.toUpperCase();
                setUserInput(null);
                while (e == null || (!e.equals("Y") && !e.equals("N"))) {
                    System.out.println("Invalid input. Please enter Y for yes or N for no.");
                    getInputText();
                    e = userInput.toUpperCase();
                    setUserInput(null);
                }
                if (e.equals("Y")) {
                    getPassivePlayer().getScoreSheet().useArcaneBoost();
                    for (int j = 0; j < getArcaneBoostDice().length; j++) {
                        System.out.println((j + 1) + ". " + getArcaneBoostDice()[j]);
                    }
                    boolean moveMade = false;
                    while (!moveMade) {
                        System.out.println("Choose a dice from 1 to " + (getArcaneBoostDice().length));
                        getButtonSelection();
                        int x = selectedIndex;
                        setSelectedButton(-1);
                        while (x < 0 || x > 5) {
                            System.out.println("Invalid input. Enter a number from 1 to "
                                    + (getArcaneBoostDice().length) + ".");
                            getButtonSelection();
                            x = selectedIndex;
                            setSelectedButton(-1);
                        }
                        dice = getArcaneBoostDice()[x];
                        m = getPossibleMovesForADie(getPassivePlayer(), dice);
                        if (m.length == 1) {
                            if (notBonus) {
                                dice.use();
                            }
                            moveMade = makeMove(getPassivePlayer(), m[0]); // TODO:aloo hwa hena lw arcane boost
                            // yehsal eh
                            // reward = getReward(getPassivePlayer(), m[0]);
                            Creature targetedCreature = m[0].getTarget();
                            System.out.println(targetedCreature.getClass().getSimpleName());
                        } else if (m.length == 0) {

                            System.out.println("No available moves for selected dice. Choose another dice.");
                        } else {
                            System.out.println("Choose a move 1-" + m.length);
                            for (int j = 0; j < m.length; j++) {
                                System.out.println((j + 1) + ". " + m[j]);
                            }
                            waitForMove();
                            move = moveToBeMade;
                            setMoveToBeMade(null);
                            if (notBonus) {
                                dice.use();
                            }
                            moveMade = makeMove(getPassivePlayer(), move);
                            Creature targetedCreature = move.getTarget();
                            System.out.println(targetedCreature.getClass().getSimpleName());

                            // reward = getReward(getPassivePlayer(), move)
                        }
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

            for (int b = 0; b < 1; b++) {
                System.out.println("AI's Turn");
                handleRewardAI(board.getPlayer2().getScoreSheet().getTurnRewards()[i], board.getPlayer2());
                boolean moveMade = false;
                Move chosen = null;
                while (!moveMade) {
                    System.out.println(("Press enter to see AI's dice"));
                    getInput();
                    rollDice();
                    Dice[] d = getAvailableDice();
                    for (int j = 0; j < getAvailableDice().length; j++) {
                        System.out.println((j + 1) + ". " + d[j]);
                    }
                    try {
                        Move c = moveChooser12(i + 1);
                        moveMade = true;
                        chosen = c;
                    } catch (TimeWarpException ex) {
                        board.getPlayer2().getScoreSheet().useTimeWarp();
                        System.out.println("AI just used timewarp.");
                    }
                }
                if (chosen == null) {
                    System.out.println("No available moves for AI.");
                    board.getStatus().endTurn();
                    break;
                }
                System.out.println("Press enter to see AI's move for first turn.");
                getInput();

                System.out.println("AI is going to hit the " + chosen.getTarget().getClass().getSimpleName());
                if (notBonus) {
                    switch (chosen.getDice().getRealm()) {
                        case RED:
                            getAllDice()[0].use();
                            break;
                        case GREEN:
                            getAllDice()[1].use();
                            break;
                        case BLUE:
                            getAllDice()[2].use();
                            break;
                        case MAGENTA:
                            getAllDice()[3].use();
                            break;
                        case YELLOW:
                           getAllDice()[4].use();
                            break;
                        case WHITE:
                            getAllDice()[5].use();
                            break;
                    }
                }
                makeMoveAI(chosen);
                Creature targetedCreature = chosen.getTarget();
                System.out.println(targetedCreature.getClass().getSimpleName());

                chosen = null;
                moveMade = false;

                if (getAvailableDice().length == 0) {
                    System.out.println("AI has no more moves");
                    board.getStatus().endTurn();
                    break;
                }

                while (!moveMade) {
                    System.out.println(("Press enter to see AI's dice"));
                    getInput();
                    rollDice();
                    Dice[] d = getAvailableDice();
                    for (int j = 0; j < getAvailableDice().length; j++) {
                        System.out.println((j + 1) + ". " + d[j]);
                    }
                    try {
                        Move c = moveChooser12(i + 1);
                        moveMade = true;
                        chosen = c;
                    } catch (TimeWarpException ex) {
                        board.getPlayer2().getScoreSheet().useTimeWarp();
                        System.out.println("AI just used timewarp.");
                    }
                }
                if (chosen == null) {
                    System.out.println("No available moves for AI.");
                    break;
                }
                System.out.println("Press enter to see AI's move for second turn.");
                getInput();

                System.out.println("AI is going to hit the " + chosen.getTarget().getClass().getSimpleName());
                if (notBonus) {
                    switch (chosen.getDice().getRealm()) {
                        case RED:
                            getAllDice()[0].use();
                            break;
                        case GREEN:
                            getAllDice()[1].use();
                            break;
                        case BLUE:
                            getAllDice()[2].use();
                            break;
                        case MAGENTA:
                            getAllDice()[3].use();
                            break;
                        case YELLOW:
                            getAllDice()[4].use();
                            break;
                        case WHITE:
                            getAllDice()[5].use();
                            break;
                    }
                }
                makeMoveAI(chosen);
                targetedCreature = chosen.getTarget();
                System.out.println(targetedCreature.getClass().getSimpleName());

                chosen = null;
                moveMade = false;

                while (!moveMade) {
                    System.out.println(("Press enter to see AI's dice"));
                    getInput();
                    rollDice();
                    Dice[] d = getAvailableDice();
                    for (int j = 0; j < getAvailableDice().length; j++) {
                        System.out.println((j + 1) + ". " + d[j]);
                    }
                    try {
                        Move c = MoveChooser3p(i + 1);
                        moveMade = true;
                        chosen = c;
                    } catch (TimeWarpException ex) {
                        board.getPlayer2().getScoreSheet().useTimeWarp();
                        System.out.println("AI just used timewarp.");
                    }
                }
                if (chosen == null) {
                    System.out.println("No available moves for AI.");
                    break;
                }
                System.out.println("Press enter to see AI's move for second turn.");
                getInput();

                System.out.println("AI is going to hit the " + chosen.getTarget().getClass().getSimpleName());
                if (notBonus) {
                    chosen.getDice().use();
                }
                makeMoveAI(chosen);
                targetedCreature = chosen.getTarget();
                System.out.println(targetedCreature.getClass().getSimpleName());

                if (notBonus) {
                    switch (chosen.getDice().getRealm()) {
                        case RED:
                            getAllDice()[0].use();
                            break;
                        case GREEN:
                            getAllDice()[1].use();
                            break;
                        case BLUE:
                            getAllDice()[2].use();
                            break;
                        case MAGENTA:
                            getAllDice()[3].use();
                            break;
                        case YELLOW:
                            getAllDice()[4].use();
                            break;
                        case WHITE:
                            getAllDice()[5].use();
                            break;
                    }
                }
            }
currentPlayer=board.getPlayer1();
            for (int j = 0; j < getAllDice().length; j++) {
                if (!getAllDice()[j].isForgotten() && !getAllDice()[j].isUsed()) {
                    getAllDice()[j].forget();
                }
            }

            if (getPossibleMovesForForgottenDice(getPassivePlayer()).length > 0) {
                boolean moveMade = false;
                System.out.println("Time for " + getPassivePlayer().getName() + "'s passive turn");
                while (!moveMade) {
                    System.out.println("Choose a dice from 1 to " + (getForgottenRealmDice().length));
                    for (int z = 0; z < getForgottenRealmDice().length; z++) {
                        System.out.println((z + 1) + ". " + getForgottenRealmDice()[z]);
                    }
                    getButtonSelection();
                    int x = selectedIndex;
                    setSelectedButton(-1);
                    while (x < 0 || x > 5) {
                        System.out.println(
                                "Invalid input. Enter a number from 1 to " + (getForgottenRealmDice().length)
                                        + ".");
                        getButtonSelection();
                        x = selectedIndex;
                        setSelectedButton(-1);
                    }
                    dice = getForgottenRealmDice()[x];
                    m = getPossibleMovesForADie(getPassivePlayer(), dice);
                    waitForMove();
                    move = moveToBeMade;
                    setMoveToBeMade(null);
                    moveMade = makeMove(getPassivePlayer(), move);
                    Creature targetedCreature = move.getTarget();
                    System.out.println(targetedCreature.getClass().getSimpleName());

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
            // TODO:arcane boost decision
            currentPlayer = board.getPlayer2();
            while (board.getPlayer2().getScoreSheet().arcaneBoostAvailable()
                    && getPossibleArcaneMoves(getPassivePlayer()).length > 0) {
                System.out.println(
                        getPassivePlayer().getName()
                                + ", do you want to use ArcaneBoost? Enter Y for yes or N for no.");
                getInputText();
                e = userInput.toUpperCase();
                setUserInput(null);
                while (e == null || (!e.equals("Y") && !e.equals("N"))) {
                    System.out.println("Invalid input. Please enter Y for yes or N for no.");
                    getInputText();
                    e = userInput.toUpperCase();
                    setUserInput(null);
                }
                if (e.equals("Y")) {
                    getPassivePlayer().getScoreSheet().useArcaneBoost();
                    for (int j = 0; j < getArcaneBoostDice().length; j++) {
                        System.out.println((j + 1) + ". " + getArcaneBoostDice()[j]);
                    }
                    boolean moveMade = false;
                    while (!moveMade) {
                        System.out.println("Choose a dice from 1 to " + (getArcaneBoostDice().length));
                        getButtonSelection();
                        int x = selectedIndex;
                        setSelectedButton(-1);
                        while (x < 0 || x > 5) {
                            System.out.println("Invalid input. Enter a number from 1 to "
                                    + (getArcaneBoostDice().length) + ".");
                            getButtonSelection();
                            x = selectedIndex;
                            setSelectedButton(-1);
                        }
                        dice = getArcaneBoostDice()[x];
                            waitForMove();
                            move = moveToBeMade;
                            setMoveToBeMade(null);
                            if (notBonus) {
                                dice.use();
                            }
                            moveMade = makeMove(getPassivePlayer(), move);
                            Creature targetedCreature = move.getTarget();
                            System.out.println(targetedCreature.getClass().getSimpleName());

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
                getAllDice()[i].unarcane();
            }
            currentPlayer=board.getPlayer1();


            while (board.getPlayer1().getScoreSheet().arcaneBoostAvailable()
                    && getPossibleArcaneMoves(board.getPlayer1()).length > 0) {
                System.out.println(
                        board.getPlayer1().getName()
                                + ", do you want to use ArcaneBoost? Enter Y for yes or N for no.");
                getInputText();
                e = userInput.toUpperCase();
                setUserInput(null);
                while (e == null || (!e.equals("Y") && !e.equals("N"))) {
                    System.out.println("Invalid input. Please enter Y for yes or N for no.");
                    getInputText();
                    e = userInput.toUpperCase();
                    setUserInput(null);
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
                        getButtonSelection();
                        int x = selectedIndex;
                        setSelectedButton(-1);
                        while (x < 0 || x > 5) {
                            System.out.println("Invalid input. Enter a number from 1 to "
                                    + (getArcaneBoostDice().length) + ".");
                            getButtonSelection();
                            x = selectedIndex;
                            setSelectedButton(-1);
                        }
                        dice = getArcaneBoostDice()[x];
                        m = getPossibleMovesForADie(board.getPlayer1(), dice);
                            System.out.println("Choose a move 1-" + m.length);
                            for (int j = 0; j < m.length; j++) {
                                System.out.println((j + 1) + ". " + m[j]);
                            }
                            waitForMove();
                            move = moveToBeMade;
                            setMoveToBeMade(null);
                            if (notBonus) {
                                dice.use();


                                moveMade = makeMove(board.getPlayer1(), move);
                                Creature targetedCreature = move.getTarget();
                                System.out.println(targetedCreature.getClass().getSimpleName());

                            // reward = getReward(getActivePlayer(), move);
                            // Creature targetedCreature = move.getTarget();
                            // System.out.println(targetedCreature.getClass().getSimpleName());
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
    public static void startGame(String ignoredS) {
        for (int i = 0; i < 6; i++) {
            System.out.println("ROUND " + getGameStatus().getRoundNumber());
            for (int l = 0; l < 2; l++) {
                System.out.println(getActivePlayer().getName() + "'s turn");
                currentPlayer = getActivePlayer();
                // System.out.println(getActivePlayer().getScoreSheet().getTurnRewards()[i]);
                handleReward(getActivePlayer().getScoreSheet().getTurnRewards()[i], getActivePlayer());
                for (int k = 0; k < 3; k++) {
                    if (getAvailableDice().length == 0
                            && getPossibleMovesForAvailableDice(getActivePlayer()).length == 0) {
                        getGameStatus().endTurn();
                        break;
                    }

                    System.out.println("Press enter to roll dice");
                    rollDice();
                    getInput();
                    Dice[] d = getAvailableDice();
                    String c;

                    while (getActivePlayer().getScoreSheet().timeWarpAvailable()) {
                        TimeWarpTime = true;
                        System.out.println("Do you want to reroll?");
                        getInputText();
                        c = userInput.toUpperCase();
                        setUserInput(null);
                        while (c == null || (!c.equals("Y") && !c.equals("N"))) {
                            System.out.println("Invalid input. Please enter Y for yes or N for no.");
                            getInputText();
                            c = userInput.toUpperCase();
                            setUserInput(null);
                        }
                        if (c.equals("Y")) {
                            getActivePlayer().getScoreSheet().useTimeWarp();
                            rollDice();
                            System.out.println("Press to roll again");
                            getInput();
                        } else {
                            break;
                        }
                    }
                    TimeWarpTime=false;
                    // Reward[] reward = new Reward[0];
                    if (getPossibleMovesForAvailableDice(getActivePlayer()).length > 0) {
                        boolean moveMade = false;
                        String turnnumber = "";
                        String p = "";
                        if (board.getPlayer1().getPlayerStatus().equals(PlayerStatus.ACTIVE)) {
                            p += " player 1";
                        } else {
                            p += "player 2";
                        }
                        switch (board.getStatus().getTurn()) {
                            case P1AT1:
                            case P2AT1:
                                turnnumber = "first";
                                break;
                            case P1AT2:
                            case P2AT2:
                                turnnumber = "second";
                                break;
                            case P1AT3:
                            case P2AT3:
                                turnnumber = "third";
                                break;
                            default:
                                System.out.println(board.getStatus().getTurn() + "bayez");
                                break;
                        }
                        String e;
                        Move[] m;
                        Dice dice;
                        Move move;
                        while (!moveMade) {

                            System.out.println("Play your " + turnnumber + " turn.");
                            System.out.println("Choose a dice from 1 to " + (getAvailableDice().length));
                            getButtonSelection();
                            int x = selectedIndex;
                            setSelectedButton(-1);
                            while (x < 0 || x > 5) {
                                System.out.println("Invalid input. Enter a number from 1 to " + (d.length) + ".");
                                getButtonSelection();
                                x = selectedIndex;
                                setSelectedButton(-1);
                            }
                            dice = getAllDice()[x];
                            m = getPossibleMovesForADie(getActivePlayer(), dice);
                                System.out.println("Choose a move");
                                waitForMove();
                                move = moveToBeMade;
                                setMoveToBeMade(null);
                                if (notBonus) {
                                    dice.use();
                                }
                                moveMade = makeMove(getActivePlayer(), move);
                                Creature targetedCreature = move.getTarget();
                                System.out.println("You hit the " + targetedCreature.getClass().getSimpleName());

                                // reward = getReward(getActivePlayer(), move);
                                // System.out.println("the bonus u just claimed is of length" +
                                // reward.length);
                                // // TODO:remove
                                // Creature targetedCreature = move.getTarget();
                                // System.out.println(targetedCreature.getClass().getSimpleName());
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

                            // board.getStatus().endTurn();
                            // break;

                        }
                    } else {
                        board.getStatus().endTurn();
                        break;
                    }

                    board.getStatus().nextTurn();

                }
                //TODO:CURRENT
                currentPlayer = getPassivePlayer();
                for (int j = 0; j < getAllDice().length; j++) {
                    if (!getAllDice()[j].isForgotten() && !getAllDice()[j].isUsed()) {
                        getAllDice()[j].forget();
                    }
                }
                Move[] m;
                String n;
                Move move;
                // Reward[] reward = new Reward[0];
                
                if (getPossibleMovesForForgottenDice(getPassivePlayer()).length > 0) {
                    boolean moveMade = false;
                    System.out.println("Time for the passive turn");
                    while (!moveMade) {
                        System.out.println("Choose a dice");
                        getButtonSelection();
                        int x = selectedIndex;
                        setSelectedButton(-1);
                        while (x < 0 || x > 5) {
                            System.out.println(
                                    "Invalid input. Enter a number from 1 to " + (getForgottenRealmDice().length)
                                            + ".");
                            getButtonSelection();
                            x = selectedIndex;
                            setSelectedButton(-1);
                        }
                        Dice dice = getAllDice()[x];
                        m = getPossibleMovesForADie(getPassivePlayer(), dice);

                            System.out.println("Choose a move.");
                            waitForMove();
                            move = moveToBeMade;
                            setMoveToBeMade(null);

                                moveMade = makeMove(getPassivePlayer(), move);
                                Creature targetedCreature = move.getTarget();
                                System.out.println("You hit the " + targetedCreature.getClass().getSimpleName());


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
                //TODO:CURRENTT
                currentPlayer = getActivePlayer();
                board.getStatus().endTurn();
                String e;
                // reward = new Reward[0];
                arcaneTime = true;
                while (getActivePlayer().getScoreSheet().arcaneBoostAvailable()
                        && getPossibleArcaneMoves(getActivePlayer()).length > 0) {
                    System.out.println(getActivePlayer().getName() + "'s arcane boost!");
                    System.out.println("Do you want to use ArcaneBoost?");
                    getInputText();
                    e = userInput.toUpperCase();
                    setUserInput(null);
                    while (e == null || (!e.equals("Y") && !e.equals("N"))) {
                        System.out.println("Invalid input. Please enter Y for yes or N for no.");
                        getInputText();
                        e = userInput.toUpperCase();
                        setUserInput(null);
                    }
                    if (e.equals("Y")) {
                        getActivePlayer().getScoreSheet().useArcaneBoost();
                        System.out.println("ARCANE BOOST TIME");
                        boolean moveMade = false;
                        while (!moveMade) {

                            System.out.println("Choose a dice.");
                            getButtonSelection();
                            int x = selectedIndex;
                            setSelectedButton(-1);
                            while (x < 0 || x > 5) {
                                System.out.println("Invalid input. Enter a number from 1 to "
                                        + (getArcaneBoostDice().length) + ".");
                                getButtonSelection();
                                x = selectedIndex;
                                setSelectedButton(-1);
                            }
                            Dice dice = getAllDice()[x];
                            m = getPossibleMovesForADie(getActivePlayer(), dice);
                                System.out.println("Choose a move.");
                                waitForMove();
                                move = moveToBeMade;
                                setMoveToBeMade(null);
                                if (notBonus) {
                                    dice.arcaneBoosted();
                                }
                                    moveMade = makeMove(getActivePlayer(), move);
                                    Creature targetedCreature = move.getTarget();
                                    System.out.println("You hit the " + targetedCreature.getClass().getSimpleName());

                                // reward = getReward(getActivePlayer(), move);
                                // Creature targetedCreature = move.getTarget();
                                // System.out.println(targetedCreature.getClass().getSimpleName());

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


                currentPlayer=getPassivePlayer();
                for (int j = 0; j < getAllDice().length; j++) {
                    getAllDice()[i].unarcane();
                }

                Dice dice;
                // reward = new Reward[0];
                while (getPassivePlayer().getScoreSheet().arcaneBoostAvailable()
                        && getPossibleArcaneMoves(getPassivePlayer()).length > 0) {
                    System.out.println(getPassivePlayer().getName() + "'s arcane boost!");
                    System.out.println("Do you want to use ArcaneBoost?");
                    getInputText();
                    e = userInput.toUpperCase();
                    setUserInput(null);
                    while (e == null || (!e.equals("Y") && !e.equals("N"))) {
                        System.out.println("Invalid input. Please enter Y for yes or N for no.");
                        getInputText();
                        e = userInput;
                        setUserInput(null);
                    }
                    if (e.equals("Y")) {
                        getPassivePlayer().getScoreSheet().useArcaneBoost();
                        System.out.println("ARCANE BOOST TIME");

                        boolean moveMade = false;
                        while (!moveMade) {

                            System.out.println("Choose a dice.");
                            getButtonSelection();
                            int x = selectedIndex;
                            setSelectedButton(-1);
                            while (x < 0 || x > 5) {
                                System.out.println("Invalid input. Enter a number from 1 to "
                                        + (getArcaneBoostDice().length) + ".");
                                getButtonSelection();
                                x = selectedIndex;
                                setSelectedButton(-1);
                            }
                            dice = getAllDice()[x];
                            m = getPossibleMovesForADie(getPassivePlayer(), dice);

                                System.out.println("Choose a move 1.");
                                waitForMove();
                                move = moveToBeMade;
                                setMoveToBeMade(null);
                                if (notBonus) {
                                    dice.arcaneBoosted();
                                }

                                    moveMade = makeMove(getPassivePlayer(), move);
                                    Creature targetedCreature = move.getTarget();
                                    System.out.println("You hit the " + targetedCreature.getClass().getSimpleName());

                                // reward = getReward(getPassivePlayer(), move);

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
                arcaneTime = false;

                for (int j = 0; j < getAllDice().length; j++) {
                    getAllDice()[j].reset();
                }
                switchPlayer();
                System.out.println("Switching players");
            }
            System.out.println("End of round" + getGameStatus().getRoundNumber());
            getGameStatus().nextRound();
        }
        Player winner;
        if (board.getPlayer1().getGameScore().getTotal() > board.getPlayer2().getGameScore().getTotal()) {
            winner = board.getPlayer1();
        } else {
            winner = board.getPlayer2();
        }
    }
    // TODO:no endgame implemented
    // } catch (NumberFormatException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }

    // public static void handleElemental(Reward reward,Player p){
    // String x = reward.getClass().getSimpleName();
    // if(x.equals("ElementalCrest"))
    // {p.incrementElementalCrest();
    // System.out.println("You got an elementalcrest!");}

    // }
public static String theKindOfBonus;
public static String theColourOfBonus;
    public static void handleReward(Reward reward, Player p) {
        if (reward == null)
            return;
        notBonus = false;
        theKindOfBonus=reward.getClass().getSimpleName();
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
                Move move;
                theColourOfBonus=b.getColor()+"";
                switch (b.getColor()) {
                    case RED:
                        moveMade = false;
                        while (!moveMade) {
                            if (getPossibleMovesForARealm(p, new RedDice()).length > 0) {
                                System.out.println("RED BONUS TIME!!!");
                                Move[] m = getPossibleMovesForARealm(p, new RedDice());
                                System.out.println("Choose a move.");
                                waitForMove();
                                move = moveToBeMade;
                                setMoveToBeMade(null);
                                moveMade = makeMove(p, move);
                                if (!moveMade) {
                                    System.out.println("Invalid move");
                                } else {
                                    System.out.println("You hit the " +
                                            p.getScoreSheet().getCreatureByRealm(move.getDice()).getClass().getSimpleName());
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
                                System.out.println("Choose a move.");
                                waitForMove();
                                move = moveToBeMade;
                                setMoveToBeMade(null);
                                // TODO:note eno mfrood el dice is initialized with one mafeesh haga hatkhosh
                                // forgotton with makemove
                                moveMade = makeMove(p, move);
                                if (!moveMade) {
                                    System.out.println("Invalid move");
                                } else {
                                    System.out.println("You hit the " +
                                            p.getScoreSheet().getCreatureByRealm(move.getDice()).getClass().getSimpleName());
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
                                System.out.println("Choose a move.");
                                waitForMove();
                                move = moveToBeMade;
                                setMoveToBeMade(null);
                                // TODO:shoofo lw el blue dice nafsaha beeb2a forgotton uslan wla la
                                moveMade = makeMove(p, move);
                                if (!moveMade) {
                                    System.out.println("Invalid move");
                                } else {
                                    System.out.println("You hit the " +
                                            p.getScoreSheet().getCreatureByRealm(move.getDice()).getClass().getSimpleName());
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
                                System.out.println("Choose a move.");
                                waitForMove();
                                move = moveToBeMade;
                                setMoveToBeMade(null);
                                moveMade = makeMove(p, move);
                                if (!moveMade) {
                                    System.out.println("Invalid move");
                                } else {
                                    System.out.println("You hit the " +
                                            p.getScoreSheet().getCreatureByRealm(move.getDice()).getClass().getSimpleName());
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
                                System.out.println("Choose a move.");
                                waitForMove();
                                move = moveToBeMade;
                                setMoveToBeMade(null);
                                // GreenDice g = (GreenDice) k.getDice();
                                // GreenDice d = new GreenDice();
                                // if(g.getValue()<7){
                                // d.setValue(6);
                                // d.setWhite(g.get);

                                // }
                                moveMade = makeMove(p, move);
                                if (!moveMade) {
                                    System.out.println("Invalid move");
                                } else {
                                    System.out.println("You hit the " +
                                            p.getScoreSheet().getCreatureByRealm(move.getDice()).getClass().getSimpleName());
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
                                System.out.println(
                                        "ESSENCE BONUS TIME!!!\nChoose a realm through the dice");
                                getButtonSelection();
                                int f = selectedIndex;
                                setSelectedButton(-1);
                                Move[] m = null;
                                // Dice d = null;
                                do {
                                    if (f==5) {

                                        System.out.println("Choose a dice other than the arcane prism.");
                                        getButtonSelection();
                                        f = selectedIndex;
                                        setSelectedButton(-1);
                                        continue;
                                    }
                                    switch (f) {
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
                                    if(m==null){
                                        System.out.println("idk leh bas m b null");
                                    continue;}
                                    if (m.length == 0) {
                                        System.out.println("This realm has no possible moves.\nPlease choose another realm to hit.");
                                        getButtonSelection();
                                        f = selectedIndex;
                                        setSelectedButton(-1);
                                    }
                                } while (f==5 || m == null || (m.length == 0));

                                // RedDice reddice = new RedDice(Integer.parseInt(f));
                                // Move[] dd = getPossibleMovesForADie(p, reddice);
                                if (m.length == 1) {
                                    moveMade = makeMove(p, m[0]);
                                    // reward = getReward(getActivePlayer(), m[0]);
                                    Creature targetedCreature = m[0].getTarget();
                                    System.out.println("You hit the " + targetedCreature.getClass().getSimpleName());
                                } else {
                                    System.out.println("Choose a move");
                                    waitForMove();
                                    move = moveToBeMade;
                                    setMoveToBeMade(null);
                                    moveMade = makeMove(p, move);
                                    // RedDice redDiceOfMoveDice = (RedDice) dd[y - 1].getDice();
                                    // reddice.selectsDragon(redDiceOfMoveDice.getDragonNumberInteger());

                                    // moveMade = makeMove(p, new Move(d, p.getScoreSheet().getCreatureByRealm(d)));
                                    if (!moveMade) {
                                        System.out.println("Invalid move somehow for some reason");// TODO:why
                                        // would
                                        // it
                                        // ever
                                        // output
                                        // that!!
                                    } else {
                                        System.out.println(p.getScoreSheet()
                                                .getCreatureByRealm(move.getDice()).getClass()
                                                .getSimpleName());
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
        theColourOfBonus = "no";
        theKindOfBonus="no";
        // } catch (NumberFormatException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
//        System.out.println("NYAHAHAHAHA TESTAHEL ALF MAJESTIC LAKE ;)");
    }

    public static Move[] getPossibleMovesForARealm(Player player, Dice dice) {
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

    public static Reward[] getReward(Player activePlayer, Move move) {
        Creature c = move.getTarget();
        return c.getReward();
    }

    public static boolean switchPlayer() {
        try {
            board.getPlayer1().switchPlayerStatus();
            board.getPlayer2().switchPlayerStatus();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Dice[] rollDice() {
        board.roll();
        return getAllDice();
    }

    public static Dice[] getAvailableDice() {
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

    public static Dice[] getAllDice() {
        return board.getDice();
    }

    public static Dice[] getArcaneBoostDice() {
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

    public static Dice[] getForgottenRealmDice() {
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

    public static Move[] getAllPossibleMoves(Player player) {
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

    public static Move[] getPossibleMovesForAvailableDice(Player player) {
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

        // another implentation rather than changing all of them null bas actually howa
        // beye3melohom sort !!!!!
        // hence howa lazem aghayaro internally!
        // i cant just add them to the string

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

    public static Move[] getPossibleMovesForForgottenDice(Player player) {
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

    // farah's method
    public static Move[] getPossibleMovesForADie(Player player, Dice dice) {

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
                GreenDice g = new GreenDice(dice.getValue());
                g.setWhite(board.getGreen().getGreenValue());
                Move[] a = player.getScoreSheet().getDragon().getPossibleMovesForADie(new RedDice(dice.getValue()));
                Move[] b = player.getScoreSheet().getGuardians().getPossibleMovesForADie(g); // TODO:
                Move[] c = player.getScoreSheet().getHydra().getPossibleMovesForADie(new BlueDice(dice.getValue()));
                Move[] d = player.getScoreSheet().getPhoenix()
                        .getPossibleMovesForADie(new MagentaDice(dice.getValue()));
                Move[] e = player.getScoreSheet().getLion().getPossibleMovesForADie(new YellowDice(dice.getValue()));
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
    public static Move[] getPossibleArcaneMoves(Player player) {
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

    public static GameBoard getGameBoard() {
        return board;
    }

    public static Player getActivePlayer() {
        if (board.getPlayer1().getPlayerStatus() == PlayerStatus.ACTIVE) {
            return board.getPlayer1();
        }
        return board.getPlayer2();
    }

    public static Player getPassivePlayer() {
        if (board.getPlayer1().getPlayerStatus() == PlayerStatus.ACTIVE) {
            return board.getPlayer2();
        }
        return board.getPlayer1();
    }

    public static ScoreSheet getScoreSheet(Player player) {
        return player.getScoreSheet();
    }

    public static GameStatus getGameStatus() {
        return board.getStatus();
    }

    public static GameScore getGameScore(Player player) {
        return player.getGameScore();
    }

    public static TimeWarp[] getTimeWarpPowers(Player player) {
        return player.getScoreSheet().getTimeWarp();
    }

    public static ArcaneBoost[] getArcaneBoostPowers(Player player) {
        return player.getScoreSheet().getArcaneBoost();
    }

    public static boolean selectDice(Dice dice, Player player) {
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

    public static boolean makeMove(Player player, Move move) {
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
                // System.out.println("elemental handled with red");//TODO:Fix ino
                // yeraga3 all
                // rewards cuz incase
                // // le3eb fel config files!!
                // //TODO: r we prepared lw ghayar an elemental crest in any other realm?
                // }

            } else if (dice instanceof GreenDice) {
                if (notBonus) {
                    ArcanePrism a = (ArcanePrism) board.getDice()[5];
                    ((GreenDice) dice).setWhite(a);
                } else {
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
                // System.out.println("Choose a color to cast your Arcane Prism
                // into.");
                // System.out.println("1-5");
                // System.out.println("1.Red 2.Green 3.Blue 4.Magenta 5.Yellow");
                // int x = sc.nextInt();
                // sc.close();
                Creature x = move.getTarget();
                if (x instanceof Dragon) {

                    //System.out.println("YA FALHOOSA WESELTY EL ARCANE RED");// TODO:do a backup
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
                    if (notBonus) {
                        GreenDice g = (GreenDice) board.getDice()[1];
                        ArcanePrism myarcane = new ArcanePrism(g.getGreenValue());
                        GreenDice actualDice = new GreenDice(dice.getValue());
                        actualDice.setWhite(myarcane);
                        b = ss.getGuardians().makeMove(actualDice);
                    } else {
                        b = ss.getGuardians().makeMove(dice);
                    }
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
                    // System.out.println("Choose a color to cast your Arcane Prism
                    // into.");
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
                System.out.println(value.toString());
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

    public static void setValue(Dice d) {
        // TODO:zabaty set value 3shan te3mel hesab el green wel red!!
    }

    public static void updateScore(Player p, Creature c) {
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

    public static String printGameScore(Player p) {
        // String s = System.out.println(p.getGameScore()); WHY IS THIS
        // NOT WORKING
        String s = p.getGameScore().toString();
        s = p.getName() + "'s Game Score:\n" + s;
        s += "| Crests  |" + "     " + p.getScoreSheet().getElementalCrest() + " |\n";
        s += "+---------+-------+\n";
        s += "| Total   |    ";
        // int[] theRealmScores = new int[5];
        s += (p.getGameScore().getTotal());
        if (p.getGameScore().getTotal() < 10) {
            s += "  |\n";
        } else if (p.getGameScore().getTotal() < 100) {
            s += " |\n";
        } else
            s += "|\n";
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

    public static void handleRewardAI(Reward reward, Player p) {
        if (reward == null)
            return;
        notBonus = false;
        Scanner sc = new Scanner(System.in);
        switch (reward.getClass().getSimpleName()) {
            case "ArcaneBoost":
                p.getScoreSheet().incrementArcaneBoost();
                System.out.println("The AI got an arcane boost!");
                // TODO:ya3ni lw gatlo w howa passive hayeyhsal eh
                break;
            case "TimeWarp":
                p.getScoreSheet().incrementTimeWarp();
                System.out.println("The AI got a time warp!");
                break;
            case "ElementalCrest":
                p.getScoreSheet().incrementElementalCrest();
                p.getGameScore().incrementElementalCrest();
                System.out.println("The AI got an elemental crest!");
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
                                Move[] dd1 = getPossibleMovesForADie(p, d1);
                                for (int j = 0; j < dd1.length; j++) {
                                    value[j] = dd1[j].checkPoints() + dd1[j].checkBonus();
                                }
                                Move finalmove = dd1[0];
                                for (int j = 1; j < dd1.length; j++) {
                                    if (value[j] > value[j - 1]) {
                                        finalmove = dd1[j];
                                        finalvalue = value[j];
                                    }
                                }
                                int finaldicevalue = 1;

                                for (int i = 2; i < 7; i++) {
                                    RedDice d = new RedDice(i);
                                    Move[] dd = getPossibleMovesForADie(p, d);
                                    for (int j = 0; j < dd.length; j++) {
                                        value[j] = dd[j].checkPoints() + dd[j].checkBonus();
                                    }
                                    for (int j = 1; j < dd.length; j++) {
                                        if (value[j] > value[j - 1]) {
                                            tempmove = dd[j];
                                            tempvalue = value[j];
                                        }
                                    }
                                    if (tempvalue > finalvalue) {
                                        finalmove = tempmove;
                                        finalvalue = tempvalue;
                                        finaldicevalue = i;
                                    }
                                }
                                RedDice finald = new RedDice(finaldicevalue);
                                RedDice reddice = (RedDice) finalmove.getDice();
                                finald.selectsDragon(reddice.getDragonNumberInteger());
                                moveMade = makeMove(p, new Move(finald, p.getScoreSheet().getCreatureByRealm(finald)));
                                System.out.println("The AI has hit the " +
                                        p.getScoreSheet().getCreatureByRealm(finald).getClass().getSimpleName());
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
                                System.out.println("The AI has hit the " +
                                        p.getScoreSheet().getCreatureByRealm(d).getClass().getSimpleName());
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
                                System.out.println("The AI has hit the " +
                                        p.getScoreSheet().getCreatureByRealm(d).getClass().getSimpleName());
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
                                if (!moveMade) {
                                    System.out.println("Invalid move");
                                } else {
                                    System.out.println("The AI has hit the " +
                                            p.getScoreSheet().getCreatureByRealm(d).getClass().getSimpleName());
                                }
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
                                Move[] dd1 = getPossibleMovesForADie(p, d1);
                                for (int j = 0; j < dd1.length; j++) {
                                    value[j] = dd1[j].checkPoints() + dd1[j].checkBonus();
                                }
                                Move finalmove = dd1[0];
                                for (int j = 1; j < dd1.length; j++) {
                                    if (value[j] > value[j - 1]) {
                                        finalmove = dd1[j];
                                        finalvalue = value[j];
                                    }
                                }
                                int finaldicevalue = 1;

                                for (int i = 2; i < 7; i++) {
                                    GreenDice d = new GreenDice(i);
                                    Move[] dd = getPossibleMovesForADie(p, d);
                                    for (int j = 0; j < dd.length; j++) {
                                        value[j] = dd[j].checkPoints() + dd[j].checkBonus();
                                    }
                                    for (int j = 1; j < dd.length; j++) {
                                        if (value[j] > value[j - 1]) {
                                            tempmove = dd[j];
                                            tempvalue = value[j];
                                        }
                                    }
                                    if (tempvalue > finalvalue) {
                                        finalmove = tempmove;
                                        finalvalue = tempvalue;
                                        finaldicevalue = i;
                                    }
                                }
                                GreenDice finald = new GreenDice(finaldicevalue);
                                moveMade = makeMove(p, new Move(finald, p.getScoreSheet().getCreatureByRealm(finald)));
                                if (!moveMade) {
                                    System.out.println("Invalid move");
                                } else {
                                    System.out.println(
                                            p.getScoreSheet().getCreatureByRealm(finald).getClass().getSimpleName());
                                }
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
                                    if (!moveMade) {
                                        System.out.println("Invalid move");
                                    } else {
                                        System.out.println(
                                                p.getScoreSheet().getCreatureByRealm(d).getClass().getSimpleName());
                                    }
                                } else if (getPossibleMovesForARealm(p, new YellowDice()).length > 0) {
                                    System.out.println("BONUS TIME!!!");
                                    YellowDice d = new YellowDice(6);
                                    moveMade = makeMove(p, new Move(d, p.getScoreSheet().getCreatureByRealm(d)));
                                } else if (getPossibleMovesForARealm(p, new BlueDice()).length > 0) {
                                    System.out.println("BONUS TIME!!!");
                                    BlueDice d = new BlueDice(6);
                                    moveMade = makeMove(p, new Move(d, p.getScoreSheet().getCreatureByRealm(d)));
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

    public static Move[] getAIMoves12() {
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

    public static Move MoveChooser3p(int round) throws TimeWarpException {
        ArrayList<Move> move = new ArrayList<Move>();
        Move[] m = null;
        for (int i = 0; i < getAvailableDice().length; i++) {
            m = getPossibleMovesForADie(board.getPlayer2(), getAvailableDice()[i]);
            move.addAll(Arrays.asList(m));
        }
        Move[] move3p = new Move[move.size()];
        Move move3ps;
        int[] diceValue = new int[move3p.length];
        for (int j = 0; j < move3p.length; j++) {
            diceValue[j] = move3p[j].checkPoints() + move3p[j].checkBonus();
        }
        move3ps = move3p[0];
        int curr = 0;
        for (int j = 0; j < move3p.length; j++) {
            if (diceValue[j] > diceValue[curr]) {
                move3ps = move3p[j];
                curr = j;
            }
        }
        if ((move3ps.checkPoints() + move3ps.checkBonus()) > 8) {
            return move3ps;
        }
        if (board.getPlayer2().getScoreSheet().timeWarpAvailable()) {
            throw new TimeWarpException();
        } else {
            return move3ps;
        }

    }

    public static Move moveChooserPassive(int round) {
        Move[] moves = getPossibleMovesForForgottenDice(board.getPlayer2());
        Move move;
        if (round == 1 || round == 2 || round == 3 || round == 4 || round == 5) {
            move = moves[0];
        } else {
            move = moves[1];
        }
        return move;
    }

    public static Move moveChooser12(int round) throws TimeWarpException {
        Move[] moves = getAIMoves12();
        Move move;
        if (round == 1 || round == 2 || round == 6) {
            if (moves.length == 2 || moves.length == 3) {
                int[] value = new int[moves.length];

                for (int j = 0; j < moves.length; j++) {
                    value[j] = moves[j].checkPoints();
                }
                move = moves[0];
                int curr = 0;
                for (int j = 0; j < moves.length; j++) {
                    if (value[j] > value[curr]) {
                        move = moves[j];
                        curr = j;
                    }
                }
                if (move.checkPoints() > 4) {
                    return move;
                }
                if (moves.length == 3) {
                    if (moves[0].getDice().getValue() == moves[1].getDice().getValue()
                            && moves[1].getDice().getValue() == moves[2].getDice().getValue()) {
                        return move;
                    }
                } else {
                    if (moves[0].getDice().getValue() == moves[1].getDice().getValue()) {
                        return move;
                    }
                }

                if (round == 1 || round == 2) {
                    int lowestDice = 0;
                    int j;
                    for (j = 1; j < 3; j++) {
                        if (moves[j].getDice().getValue() < moves[lowestDice].getDice().getValue()) {
                            lowestDice = j;
                        }
                    }
                    return moves[lowestDice];
                } else {
                    if (board.getPlayer2().getScoreSheet().timeWarpAvailable()) {
                        throw new TimeWarpException();
                    } else {
                        int lowestDice = 0;
                        int j;
                        for (j = 1; j < 3; j++) {
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
                i = i + 2;
            }
            int[] value = new int[moves.length];

            for (int j = 0; j < i; j++) {
                value[j] = moves[j].checkPoints();
            }
            move = moves[0];
            int curr = 0;
            for (int j = 0; j < moves.length; j++) {
                if (value[j] > value[curr]) {
                    move = moves[j];
                    curr = j;
                }
            }
            return move;
        } else { // round == 3,4,5
            if (moves.length == 2 || moves.length == 3) {
                int[] value = new int[moves.length];

                for (int j = 0; j < moves.length; j++) {
                    value[j] = moves[j].checkPoints() + moves[j].checkBonus();
                }
                move = moves[0];
                int curr = 0;
                for (int j = 0; j < moves.length; j++) {
                    if (value[j] > value[curr]) {
                        move = moves[j];
                        curr = j;
                    }
                }
                if ((move.checkPoints() + move.checkBonus()) > 8) {
                    return move;
                }
                if (moves.length == 3) {
                    if (moves[0].getDice().getValue() == moves[1].getDice().getValue()
                            && moves[1].getDice().getValue() == moves[2].getDice().getValue()) {
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
                    int j;
                    for (j = 1; j < 3; j++) {
                        if (moves[j].getDice().getValue() < moves[lowestDice].getDice().getValue()) {
                            lowestDice = j;
                        }
                    }
                    return moves[lowestDice];
                }
                // TODO: if no rerolls, choose the lower move
                // TODO: else choose the remaining move
                // TODO: return here
            }
            int i = moves.length / 2;
            if (board.getStatus().getTurn() == TurnShift.P2AT2 && !board.getDice()[5].isForgotten()) {
                i = i + 2;
            }
            int[] value = new int[moves.length];

            for (int j = 0; j < i; j++) {
                value[j] = moves[j].checkPoints() + moves[j].checkBonus();
            }
            move = moves[0];
            int curr = 0;
            for (int j = 0; j < moves.length; j++) {
                if (value[j] > value[curr]) {
                    move = moves[j];
                    curr = j;
                }
            }
            return move;
        }

    }

    public static boolean makeMoveAI(Move move) {
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
                // System.out.println("elemental handled with red");//TODO:Fix ino
                // yeraga3 all
                // rewards cuz incase
                // // le3eb fel config files!!
                // //TODO: r we prepared lw ghayar an elemental crest in any other realm?
                // }

            } else if (dice instanceof GreenDice) {
                if (notBonus) {
                    ArcanePrism a = (ArcanePrism) board.getDice()[5];
                    ((GreenDice) dice).setWhite(a);
                } else {
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
                // System.out.println("Choose a color to cast your Arcane Prism
                // into.");
                // System.out.println("1-5");
                // System.out.println("1.Red 2.Green 3.Blue 4.Magenta 5.Yellow");
                // int x = sc.nextInt();
                // sc.close();
                Creature x = move.getTarget();
                if (x instanceof Dragon) {

                    //System.out.println("YA FALHOOSA WESELTY EL ARCANE RED");// TODO:do a backup
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
                    if (notBonus) {
                        GreenDice g = (GreenDice) board.getDice()[1];
                        ArcanePrism myarcane = new ArcanePrism(g.getGreenValue());
                        GreenDice actualDice = new GreenDice(dice.getValue());
                        actualDice.setWhite(myarcane);
                        b = ss.getGuardians().makeMove(actualDice);
                    } else {
                        b = ss.getGuardians().makeMove(dice);
                    }
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
                    // System.out.println("Choose a color to cast your Arcane Prism
                    // into.");
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
                System.out.println(value.toString());
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
}