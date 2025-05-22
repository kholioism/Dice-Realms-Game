# Project Skeleton

## Folder Structure

```
Dice-Realms/
├── src/
│    ├──main/
│    │  └── java/
│    │       └── game/
│    │           ├── collectibles/
│    │           │   ├── ArcaneBoost.java
│    │           │   ├── Bonus.java
│    │           │   ├── ElementalCrest.java
│    │           │   ├── EssenceBonus.java
│    │           │   ├── Power.java
│    │           │   ├── Reward.java
│    │           │   ├── RewardType.java
│    │           │   └── TimeWarp.java
│    │           ├── creatures/
│    │           │   ├── Creature.java
│    │           │   ├── Dragon.java
│    │           │   ├── DragonNumber.java
│    │           │   ├── Guardians.java
│    │           │   ├── Hydra.java
│    │           │   ├── Lion.java
│    │           │   └── Phoenix.java
│    │           ├── dice/
│    │           │   ├── Dice.java
│    │           │   ├── ArcanePrism.java
│    │           │   ├── BlueDice.java
│    │           │   ├── GreenDice.java
│    │           │   ├── MagentaDice.java
│    │           │   ├── RedDice.java
│    │           │   └── YellowDice.java
│    │           ├── engine/
│    │           │   ├── GameBoard.java
│    │           │   ├── CLIGameController.java
│    │           │   ├── GameController.java
│    │           │   ├── GameScore.java
│    │           │   ├── GameStatus.java
│    │           │   ├── Move.java
│    │           │   ├── Player.java
│    │           │   ├── PlayerStatus.java
│    │           │   ├── Realm.java
│    │           │   ├── ScoreSheet.java
│    │           │   ├── Status.java
│    │           │   └── TurnShift.java
│    │           ├── exceptions
│    │           │   ├── CommandFormException.java
│    │           │   ├── DiceRollException.java
│    │           │   ├── ExhaustedResourceException.java
│    │           │   ├── InvalidMoveException.java
│    │           │   ├── InvalidDiceSelectionException.java
│    │           │   ├── PlayerActionException.java
│    │           │   └── RewardException.java
│    │           ├── gui
│    │           │   ├── DiceRealms.java
│    │           │   ├── AISequence.java
│    │           │   ├── GUIGameController.java
│    │           │   ├── hi.java
│    │           │   ├── IntroSceneController.java
│    │           │   ├── ModeSelectController.java
│    │           │   ├── ModeSelectController.java
│    │           │   ├── PlayerNameController.java
│    │           │   ├── PlayerSequence.java
│    │           │   ├── SuperMarketController.java
│    │           │   ├── SuperMarketControllerAI.java
│    │           │   ├── TotalSceneController.java
│    │           │   ├── ScoreSheetSceneController.java
│    │           │   ├── Scene1Controller.java
│    │           │   └── gui.java
│    │           └── Main.java
│    └── test
│        └── CLIGameControllerTest.java
├── target/
│   ├── classes/
│   │   ├── collectibles
│   │   ├── creatures
│   │   ├── dice
│   │   ├── engine
│   │   ├── exceptions
│   │   ├── gui
│   │   └── Main.java
│   └── test-classes
├── .gitignore
├── pom.xml
├── ProjectSkeleton.md
└── README.md
```

## Packages

### game.collectibles

The `game.collectibles` package contains classes for the various collectible items within the game; such as power-ups, elemental crest, color bonus, or the essence bonus.

### game.creatures

In the `game.creatures` package, you'll find classes representing creatures in their corresponding realms; including all necessary features about how to attack them or their current status to update the score sheet accordingly.

### game.dice

The `game.dice` package encompasses classes related to dice functionality within the game. It includes implementations for rolling dice, managing dice states, and handling dice-related actions and interactions.

### game.engine

This package contains the core engine components of the game, including the abstract classes and interfaces that define the game's structure and functionality. It serves as the foundation for implementing various game controllers and managing game logic. Additional classes related to game mechanics and control can be added to this package as needed.

### game.exceptions

The `game.exceptions` package provides classes for defining custom exceptions specific to the game. These exceptions help handle error conditions and unexpected situations, providing meaningful feedback to the player or developer.

### game.gui

The `game.gui` package houses classes related to the graphical user interface (GUI) of the game. This includes components for rendering game graphics, handling user input, and managing the visual presentation of game elements.

## Classes

For each package, add the skeleton details for the class and duplicate as much as needed. As an example, the `GameController.java` skeleton is provided as guideline.

### `GameController` class

- **Package**: `game.engine`
- **Type**: Abstract Class
- **Description**: This abstract class represents the controller for the game. It defines the common blueprint for different controllers used in the game.

#### Methods:

1. `void startGame()`

   - **Description**: Initializes necessary components and starts the game loop.

2. `boolean switchPlayer()`

   - **Description**: Switches the role of the current active player to passive and vice versa, ensuring that the turn-taking mechanism functions correctly.
   - **Return Type**: `boolean`
     - `true` if the switch was successful,
     - `false` otherwise.

3. `Dice[] rollDice()`

   - **Description**: Rolls all available dice for the current turn, assigning each a random number from 1 to 6.
   - **Return Type**: Array of `Dice`
     - An array of the currently rolled dice.

4. `Dice[] getAvailableDice()`

   - **Description**: Gets the dice available for rolling or rerolling.
   - **Return Type**: Array of `Dice`
     - An array of dice available for the current turn.

5. `Dice[] getAllDice()`

   - **Description**: Gets all six dice, providing their current state and value within the game regardless of their location or status.
   - **Return Type**: Array of `Dice`
     - An array of all six dice, with each die's state and value.

6. `Dice[] getForgottenRealmDice()`

   - **Description**: Gets the dice currently available in the Forgotten Realm.
   - **Return Type**: Array of `Dice`
     - An array of dice that are currently in the Forgotten Realm.

7. `Move[] getAllPossibleMoves()`

   - **Description**: Gets all possible moves for all currently rolled dice for the active player.
   - **Return Type**: Array of `Move`
     - An array of all possible moves for all rolled dice.

8. `Move[] getPossibleMoves(Dice dice)`

   - **Description**: Gets all possible moves for a given dice for the active player.
   - **Parameters**:
     - `dice`: The dice to determine possible moves for.
   - **Return Type**: Array of `Move`
     - An array of possible moves for the given dice.

9. `GameBoard getGameBoard()`

   - **Description**: Gets the current game board, including all players and all score sheets.
   - **Return Type**: `GameBoard`
     - The current game board object.

10. `Player getPlayer()`

    - **Description**: Gets the current active player's information.
    - **Return Type**: `Player`
      - The active player object.

11. `ScoreSheet getScoreSheet()`

    - **Description**: Gets the score sheet for the current active player.
    - **Return Type**: `ScoreSheet`
      - The score sheet object for the current active player.

12. `GameStatus getGameStatus()`

    - **Description**: Gets the current game status, including round and turn information for the current active player.
    - **Return Type**: `GameStatus`
      - The current game status object.

13. `GameScore getGameScore()`

    - **Description**: Gets the current score of the game, including scores in each realm, number of elemental crests, and the total score for the current active player.
    - **Return Type**: `GameScore`
      - The current game score object.

14. `TimeWarp getTimeWarpPowers()`

    - **Description**: Gets the number of TimeWarp powers the active player has and their status.
    - **Return Type**: `TimeWarp`
      - The TimeWarp object for the current active player.

15. `ArcaneBoost getArcaneBoostPowers()`

    - **Description**: Gets the number of ArcaneBoost powers the active player has and their status.
    - **Return Type**: `ArcaneBoost`
      - The ArcaneBoost object for the current active player.

16. `boolean selectDice(Dice dice)`

    - **Description**: Selects a dice and adds it to the current turn of the active player, moving all other dice with less value to the Forgotten Realm.
    - **Parameters**:
      - `dice`: The dice to be selected.
    - **Return Type**: `boolean`
      - `true` if the selection was successful,
      - `false` otherwise.

17. `boolean makeMove(Dice dice, Creature creature)`
    - **Description**: Executes a move using the selected dice on a specified creature.
    - **Parameters**:
      - `dice`: The dice selected by the active player for the move.
      - `creature`: The target creature that the move is against.
    - **Return Type**: `boolean`
      - `true` if the move is successfully completed,
      - `false` otherwise.


### ArcaneBoost class

- **Package**: `game.collectibles`
- **Type**: Class
- **Description**: This class represents a magical ability that allows players to select an additional dice to be rolled

#### Methods:

1. `void setcount(int x)`
   - **Description**: This method sets the value of the counter of the Arcane Boost
   - **Parameters**:
      - `x`: it takes an integer representing the number of Arcane Boosts of a player


2. `int getcount()`
   - **Description**: It returns the counter containing the number of Arcane Boosts that a player has
   - **Return Type**: integer
     - the current number of Arcane Boosts

### Bonus class

- **Package**: `game.collectibles`
- **Type**: Class
- **Description**: This class represents all the bonus's in the game

#### Methods:

1. `RewardType getRewardType()`
   - **Description**: This method returns the type of the reward, the fact that it is a bonus
   - **Return Type**: RewardType
      - an enum englobing all of the reward types: bonus, power and crest

### ElementalCrest class

- **Package**: `game.collectibles`
- **Type**: Class
- **Description**: This class represents a magical token, where during the game a player collects these elemental crests and at the end when counting the score, we will take the realm with the lesser score and we will add to the final score the value of this lesser score multiplied by the number of elemental crests collected

#### Methods:

1. `RewardType getRewardType()`
   - **Description**: This method returns that the reward is a crest
   - **Return Type**: RewardType
      - an enum englobing all of the reward types: bonus, power and crest

### Power class

- **Package**: `game.collectibles`
- **Type**: Class
- **Description**: This class represents all of the powers in the game

#### Methods:

1. `RewardType getRewardType()`
   - **Description**: This method returns the type of the reward that it is a power 
   - **Return Type**: RewardType
      - an enum englobing all of the reward types: bonus, power and crest

### Reward class

- **Package**: `game.collectibles`
- **Type**: Class
- **Description**: This class represents all of the rewards in the game

#### Methods:

1. `boolean isObtained()`
   - **Description**: Represents whether or not the current reward has been taken or not
   - **Return Type**: boolean
      - `true` if the player took the reward,
      - `false` otherwise.

2. `boolean isUsed()`
   - **Description**: Represents whether or not the current reward has been used or not
   - **Return Type**: boolean
      - `true` if the player has used the reward,
      - `false` otherwise.

3. `void setObtained()`
   - **Description**: This method sets the value of the obtained variable to true when reward has been taken

4. `void setUsed()`
   - **Description**: This method sets the value of the used variable to true when when reward has been used

### RewardType class

- **Package**: `game.collectibles`
- **Type**: Enum Class
- **Descrption**: it is a class with a fixed set of predefined options, whether the Reward is a bonus, a power or a crest

### TimeWarp class

- **Package**: `game.collectibles`
- **Type**: Class
- **Descrption**: This class represents a magical ability where the active player is able to reroll his dice once again

#### Methods:
1. `void setcount(int x)`
   - **Description**: This method sets the value of the counter of the Time Warp
   - **Parameters**:
      - `x`: it takes an integer representing the number of Time Warps of a player

2. `int getcount()`
   - **Description**: It returns the counter containing the number of Time Warps that a player has
   - **Return Type**: integer
      - the current number of Time Warps

### Creature class

- **Package**: `game.collectibles`
- **Type**: abstract Class
- **Description**: This class represents all creatures in the Dice Realms Game. 

#### Methods:
1. `String toString()`
   - **Description**: prints the score sheet of a realm
   - **Return Type**: `String`
      - the score sheet as a string

2. `boolean makeMove(Dice d)`
   - **Description**: takes the given dice and according to the realm does an attack
   - **Parameter**: 
      - `d`: it takes a dice
   - **Return Type**: `boolean`
     - `true` if the move is done,
     - `false` otherwise.
     
3. `Move[] getPossibleMovesForADie(Dice d)`
   - **Description**: puts all of the possible moves for a single die inside of an array so that the user will know which moves will be possible and which will not
   - **Parameters**:
     - `d`: a given die
   - **Return Type**: `Move[]`
      - an array containing all of the moves possible

4. `Reward[] getReward()`
   - **Description**: puts all of the rewards a player has into an array
   - **Return Type**: array of rewards
      - an array containing all of the rewards

5. `int getScore()`
   - **Description**: gets at every round the current total score of a realm
   - **Return Type**: `int`
      - the total score of a realm

### Dragon class

- **Package**: `game.collectibles`
- **Type**: Class
- **Description**: This class represents the Pyroclast Dragons in the Emberfall Dominion realm. This realm has 4 different dragons: dragon 1, dragon 2, dragon 3 and dragon 4. Each dragon has 3 of the following hit regions: a face, wings, a tail and a heart. To kill a dragon you must hit all 3 of its hit regions.

#### Methods:

1. `int[][] getThe_dragonsvals`
   - **Description**: returns all of the scores for each of the 4 dragons when you hit any of its body regions inside a 2D array
   - **Return Type**: `int[][]`
      - a 2D integer array that represent all of the scores of each of the dragons' body regions

2. `String toString`
   - **Description**: prints the updated score sheet of the Dragons
   - **Return Type**: `String`
      - the updated score sheet as a string

3. `boolean makeMove(Dice dd)`
   - **Description**: 
   - **Parameters**:
      - `dd`: the given red die
   - **Return Type**:

4. `Move[] getPossibleMovesForADie(Dice d)`
   - **Description**: according to the red dice value it puts all of the possible moves inside of an array
   - **Parameter**: 
      - `d`: takes the given red dice
   - **Return Type**: `Move[]`
      - an array with all of the possible moves

5. `Reward[] getReward()`
   - **Description**: after every move it checks if any of the rows and/or columns and/or diagonal have been fully hit and if yes it checks whether or not the player has won any rewards and accordingly puts the rewards inside of a reward array
   - **Return Type**: `Reward[]`
      - an array with all of the rewards that a player has won in his turn

6. `int getScore()`
   - **Description**: gets at every round the current final score of the Dragons
   - **Return Type**: `int`
      - an integer number of the current final score of the Dragons
7. Boolean[] getBonusObtained()
-**Description**: Retrieves an array indicating whether each bonus has been obtained by the dragon.
-**Return Type**: 
   -Boolean[]: Array of Boolean values, where each element represents the status of a bonus (true if obtained, false if not).
8. String[] getBonusArray()
-**Description**: Retrieves an array containing the names of all bonuses obtained by the dragon.
-**Return Type**:
   - String[] :Array of Strings, where each element represents the name of a bonus obtained by the dragon.


### DragonNumber class

- **Package**: `game.collectibles`
- **Type**: enum class
- **Description**: enum representing all the different dragons present in the Emberfall Dominion: Dragon 1, Dragon 2, Dragon 3 and Dragon 4

### Guardians class

- **Package**: `game.collectibles`
- **Type**: Class
- **Description**: This class represents the Gaia Guardians in the Terra's Heartland realm. This realm has 11 Gaia Guardians each needing a unique number of hits to be defeated.
1. String[][] getguardians_hitValues()
-**Description**: Retrieves the hit values of the guardians in the Gaia Guardians class.
-**Return Type**: 
   -String[][]:Returns a 2D array containing the hit values of the guardians. Each row corresponds to a guardian, and each column represents a specific attribute or characteristic associated with that guardian.
2. int[][] getguardians_values()
-**Description**: Retrieves the values associated with the guardians in the Gaia Guardians class.
-**Return Type**:
   - int[][]Returns a 2D array containing the values associated with the guardians. Each row corresponds to a guardian, and each column represents a specific numerical value or statistic associated with that guardian.
3. String[] getBonus()
-**Description**: Retrieves the bonus values associated with the Gaia Guardians class.
-**Return Type**: 
   -String[]: Returns an array containing the bonus values. Each element of the array represents a specific bonus associated with the guardians.




### `Hydra` class

- **Package**: `game.creature`
- **Type**: Public Class
- **Description**: This class represents the blue realm where Hydra Serpent exists, it has 5 heads, if you kill it, it regenerates with 6 heads.

#### Methods:

1. `int getCurrenthead()`

   - **Description**: it returns the current head of hydra serpent that will be deated next.
   - **Return Type**: `int`
     - the head to be defeated next.

2. `int getIndex()`

   - **Description**: it returns the index of the score array.
   - **Return Type**: `int`
     - score array index.
3. `public boolean makeMove(Dice d)`

   - **Description**: takes the given dice as an object and gets its value to check whether there is head can be defeated using this specific value and accordingly do the move if possible. 
   - **Parameters**: 
     - `Dice`:given dice which corresponds to Blue Dice Object.
   - **Return Type**: `boolean`
     - `true` if the hydra head id defeated,
     - throws exceptions  if no hydra head can be defeated using the available points or if the hydra serpent is dead.
    

4. `boolean isMoveAcceptable(int points)`

   - **Description**: checks whether the given value of the given dice can defeat the next hydra head. 
   - **Parameters**: 
     - `int`:value of the given dice which corresponds to Blue Dice Object.
   - **Return Type**: `boolean`
     - `true` if the next hydra head that can be defeated using the value given,
     - `false` otherwise.

5. `void  DefeatHead(int points)`

   - **Description**: defeats the current head of the hydra serpent and updates the currenthead variable with the next head of the hydra as well as updates the current score.
   - **Parameters**: 
     - `int`:value of the given dice which corresponds to Blue Dice Object.

6. `public int getScore()`

   - **Description**:returns the total current score of the blue realm.
   - **Return Type**: `int`
     - total current blue realm score.   
7. `public Reward[] getReward()`

   - **Description**: checks whether the defeated head has a corresponding reward and puts it in an array of type reward.
   - **Return Type**: `Reward[]`
     - array with the reward that the player has won in their return.  

8. `public Move[] getPossibleMovesForADie(Dice d)`

   - **Description**: checks the possible hydra head that can be defeated for the specfic value of the given dice and puts it in the an array of type Move.
   - **Parameters**: 
     - `Dice`:given dice which corresponds to Blue Dice Object.
   - **Return Type**: `Move[]`
     - array with possible move of blue realm.

9. `public String toString()`

   - **Description**: returns the updated scoresheet of blue realm with modifications like marking the head defeated and marking the bonuses already collected.

   - **Return Type**: `String`
     - updated scoresheet of blue realm.  
    
10. String[] getBonus()
-**Description**: Retrieves the array of bonus items associated with the Hydra.
-**Return Type**: 
   -String[]:This method returns an array of strings representing the bonus items that can be obtained from the Hydra. Each element in the array corresponds to a specific bonus item.



### `Phoenix` class

- **Package**: `game.creature`
- **Type**: Public Class
- **Description**: This class represents magenta realm where phoeinx creature which requires each spell to be of greater power than the last, however, it is reborn from its ashes when the maximum hit spellis done. 

#### Methods:

1. `int getCounter()`

   - **Description**: it returns the counter of the score array.
   - **Return Type**: `int`
     - score array counter.

2. `public boolean makeMove(Dice d)`

   - **Description**: takes the given dice as an object and gets its value to check whether the current value is greater than pervious value and accordingly do the move if possible, however, if the previous value has reached 6, it makes the move.
   - **Parameters**: 
     - `Dice`:given dice which corresponds to Magenta Dice Object.
   - **Return Type**: `boolean`
     - `true` if the phoeinx is attacked (move has been made) ,
     - `false` otherwise.
    

3. `boolean Acceptable(MagentaDice m)`

   - **Description**: checks whether the current value of the given is greater than pervious value. 
   - **Parameters**: 
     - `MagentaDice`:Magenta Dice Object.
   - **Return Type**: `boolean`
     - `true` if the current value is greater than pervious value,
     - `false` otherwise.

4. `public int getScore()`

   - **Description**:returns the total current score of the magenta realm by adding all previous chosen dice's values.
   - **Return Type**: `int`
     - total current magenta realm score.  

5. `public Reward[] getReward()`

   - **Description**: checks whether the number of attack  has a corresponding reward and puts it in an array of type reward.
   - **Return Type**: `Reward[]`
     - array with the reward that the player has won in their return.  

6. `public Move[] getPossibleMovesForADie(Dice dice)`

   - **Description**: checks the possible attack for the specfic value of the given dice and puts it in the an array of type Move.
   - **Parameters**: 
     - `Dice`:given dice which corresponds to Magenta Dice Object.
   - **Return Type**: `Move[]`
     - array with possible move of magenta realm.

7. `public String toString()`

   - **Description**: returns the updated scoresheet of magenta realm with modifications like writing done the value of dice for each attack and marking the bonuses already collected.

   - **Return Type**: `String`
     - updated scoresheet of magenta realm. 
8. String[] getBonus()
-**Description**: Retrieves an array of bonus strings associated with the Lion class.
-**Return Type**: 
   -String[]:This method returns an array of strings containing bonus information specific to the Lion class. These bonuses may include various benefits or abilities that the Lion possesses in the game.




### `Lion` class

- **Package**: `game.creature`
- **Type**: Public Class
- **Description**: This class represents yellow realm where Solar Lion embodies the pure essence of light and
strength in which each attack can be of any
value. 

#### Methods:

1. `int getCounter()`

   - **Description**: it returns the counter of the score array.
   - **Return Type**: `int`
     - score array counter.

2. `public boolean makeMove(Dice d)`

   - **Description**: takes the given dice as an object and gets its value to check whether lion is dead or not and accordingly do the move.
   - **Parameters**: 
     - `Dice`:given dice which corresponds to Yellow Dice Object.
   - **Return Type**: `boolean`
     - `true` if the lion is attacked (move has been made) ,
     - throws exception if thelion is dead.

3. `public int getScore()`

   - **Description**:returns the total current score of the yellow realm by adding all previous chosen dice's values.
   - **Return Type**: `int`
     - total current yellow realm score.  

4. `public Reward[] getReward()`

   - **Description**: checks whether the number of attack  has a corresponding reward and puts it in an array of type reward.
   - **Return Type**: `Reward[]`
     - array with the reward that the player has won in their return.  

5. `public Move[] getPossibleMovesForADie(Dice dice)`

   - **Description**: checks the possible attack for the specfic value of the given dice and puts it in the an array of type Move.
   - **Parameters**: 
     - `Dice`:given dice which corresponds to Yellow Dice Object.
   - **Return Type**: `Move[]`
     - array with possible move of yellow realm.

6. `public String toString()`

   - **Description**: returns the updated scoresheet of yellow realm with modifications like writing done the value of dice for each attack and marking the bonuses already collected.

   - **Return Type**: `String`
     - updated scoresheet of yellow realm. 
7. String[] getBonus()
-**Description**: Retrieves the array of bonuses associated with the Phoenix.
-**Return Type**:
   - String[] - An array of strings representing the bonuses.
     




### ArcanePrism class

- **Package**: `game.dice`
- **Type**: Class
- **Description**: This class represents the White die, a wild card that can be used as any color the player chooses

### Dice class

- **Package**: `game.dice`
- **Type**:  Class
- **Description**: This class represents implementations for rolling dice, and managing the dice states by getting its value and determining whether it's forgotten, used or arcane boosted at the end of each turn.

#### Methods:

1. `void roll()`
   - **Description**: Roll the dice randomly between 1 and 6 inclusively.


2. `int getValue()`
   - **Description**: Gets the value of the roll.
   - **Return Type**: `int`
     - an integer corresponding to the current value of the roll

3. `void setValue(int x)`
   - **Description**: Sets the value of the roll to a given x
   - **Parameters**: 
      - `x`: a given integer     

4. `void use()`
   - **Description**: The dice that the player decided to use.

5. `void forget()`
   - **Description**: The dices with value less than the used dice value go to the forgetten realm.

6. `void arcaneBoosted()`
   - **Description**: sets the variable arcaneBoosted to true in the case where the player used the dice using arcaneboost.

7. `void reset()`
   - **Description**: reset all of the dice values to be used in the new realm

8. `void ifMoveIsPossible()`
   - *Description*: checks whether the move of dice of specific color and roll integer is possible.

9. `boolean isForgotten()`
   - **Description**: Checks whether the dice with specific color is in the forgetten realm or not.
   - **Return Type**: boolean
     - `true`: if dice is in forgetten realm,
     - `false`: otherwise.
     
10. `boolean isUsed()`
   - **Description**: Checks whether this dice with specific color have been used by player before.
   - **Return Type**: boolean
     - `true`: if the dice is used,
     - `false`: otherwise.

11. `boolean isArcaneBoosted()`
   - **Description**: Checks whether the dice has been previously used this turn using the arcane boost power.
   - **Return Type**: boolean
     - `true`: if the player has used it.
     - `false`: otherwise.

12. `Realm getRealm()`
   - **Description**: gets the realm of the current dice color
   - **Return Type**: `Realm`
      - enum englobing all of the different realm colors: yellow, magenta, blue, green and red

13. `String toString()`
   - **Description**: it returns the dice realm and the dice value of a specific dice
   - **Return Type**: `String`
      - a dice's realm and value as a string

### RedDice class

- **Package**: `game.dice`
- **Type**: Class
- **Description**: class that represents the red dice, used in the Emberfall Dominion

#### Methods:

### `BlueDice` class

- **Package**: `game.dice`
- **Type**: Public Class
- **Description**: This class represents Blue Dice which its value is used in Blue Realm to defeat head hydra.

### `MagentaDice` class

- **Package**: `game.dice`
- **Type**: Public Class
- **Description**: This class represents Magenta Dice which its value is used in Magenta Realm to attack the phoenix.

### `LionDice` class

- **Package**: `game.dice`
- **Type**: Public Class
- **Description**: This class represents Yellow Dice which its value is used in Yellow Realm to attack the lion.


### `GreenDice` class

- **Package**: `game.dice`
- **Type**: Public Class
- **Description**: This class represents Green Dice which its value is used in Green Realm to defeat Gaia gurdian.
   
#### Methods:

1. `public int getValue()`

   - **Description**: it returns the total values of green and white dice.
   - **Return Type**: `int`
     - the summation of green and white dice values.

2. `public void setWhite(ArcanePrism a)`

   - **Description**: set the whiteDiceValue to the  value of ArcanPrism
   - **Parameters**: 
      - `ArcanPrism`:given dice which corresponds to ArcanPrism Dice Object.

3. `public void setWhite(int x)`

   - **Description**: takes values and set as whiteDiceValue.
   - **Parameters**: 
      - `int`: value of white dice.

4. `public int getWhiteValue()`

   - **Description**: it returns the value of the ArcanePrism dice.
   - **Return Type**: `int`
     - the ArcanePrism dice value.
 
5. `public int getGreenValue()`

   - **Description**: it returns the value of the Grrendice.
   - **Return Type**: `int`
     - the Green dice value.

6. `public String toString()`

   - **Description**: returns Dice realm color and the green dice Value.
   - **Return Type**: `String`
     - realm color of dice and green dice value in a string. 


2. `void selectsDragon()`
   - **Description**: we select one of the 4 dragons 

### Move class

-**Package**: `game.engine`
-**Type**: Class
-**Description**: This class determines the target creature using the value of the dice.

#### Methods:

1. `Creature getTarget()`
   - **Description**: Gets the target of the move.
   - **Return Type**: `Creature`
     - One of the invented characters of the game.

2. `void setTarget(Creature target)`
   - **Description**: Sets the target of the move.
   - **Parameters**: 
     - `target`: The specific creature to be attacked.

3. `Dice getDice()`
   - **Description**: Gets the dice value.
   - **Return Type**: `Dice`
     - 6-face cube to be rolled.

4. `void setDice(Dice dice)`
   - **Description**: Sets the dice value.
   - **Parameters**:
     - `dice`: 6-face cube to be rolled.

5. `int compareTo(Move m)`
   - **Description**:
   - **Parameters**:
   - **Return Type**:

6. `String toString()`
   - **Description**:
   - **Return Type**:

### GameBoard class

- **Package**: `game.engine`
- **Type**:  Class
- **Description**: This class represents the information about player 1 and player 2 as well as the current status of the game.

#### Methods:

1. `public ArcanPrism getWhite()`
   - **Description**: This methods white as type of ArcanePrism object.
   - **Return Type**: `ArcanPrism`
       white of ArcanePrism object.

2. `public void roll()`
   - **Description**: This methods roll the dices of each color if it's neither forgetten nor used. 

3. `public Player getPlayer1()`
   - **Description**: This methods returns the first player as type of player object.
   - **Return Type**: `player`
       The first player.
   

4. `public Player getPlayer2()`
   - **Description**: This methods returns the second player as type of player object.
   - **Return Type**: `player`
       The second player.

5. `public GameStatus getStatus()`
   - **Description**: This methods returns the current status of the game as type of GameStatus object.
   - **Return Type**: `GameStatus`
       The current status of the game.

6. `public void setStatus(GameStatus status)`
   - **Description**: This method sets the current status of the game.
   - **Parameters**: 
     - `GameStatus`: The current game status object.
 
7. `public Dice[] getDice()`
   - **Description**: This methods returns a Dice array.
   - **Return Type**: `Dice[]`
       Array of Dice.
     

### GameScore class
-**Package**: `game.engine`
-**Type**:  public Class
-**Description**: This class gets the current score of the game, including scores in each realm, number of elemental crests, and the total score for the current active player.

#### Methods:

1. `int getTotal()`
   - **Description**: This methods gets the total score of all realms of the game.
   - **Return Type**: `int`
     - integer which represents the total score of the game.

2. `public int[] getScore()`
   - **Description**: This methods gets the total score for each realm individually.
   - **Return Type**: `int[]`
     - integer array which represents the total score for each realm.

3. `public int getRedRealmScore()`
   - **Description**: This methods gets the total score of Red realm from the score array.
   - **Return Type**: `int`
     - score of red realm.

4. `public int getGreenRealmScore()`
   - **Description**: This methods gets the total score of Green realm from the score array.
   - **Return Type**: `int`
     - score of green realm. 

5. `public int getBlueRealmScore()`
   - **Description**: This methods gets the total score of Blue realm from the score array.
   - **Return Type**: `int`
     - score of blue realm.     

6. `public int getMagentaRealmScore()`
   - **Description**: This methods gets the total score of Magenta realm from the score array.
   - **Return Type**: `int`
     - score of magenta realm.     

7. `public int getYeelowaRealmScore()`
   - **Description**: This methods gets the total score of Yellow realm from the score array. 
   - **Return Type**: `int`
     - score of yellow realm.   

### GameStatus class

-**Package**: `game.engine`
-**Type**: Enum Class
-**Description**: This class represents the current status of the game.

#### Methods:

1.`void nextTurn()`
   -**Description**: Proceeds to next turn using the enum TurnShift

2.`void nextState()`
   -**Description**: Proceeds to next state using the enum Status

3.`void nextRound()`
   -**Description**: Represents the start of the tursnhift enum again and increments round number

4.`void endTurn()`
   -**Description**: Manages the special cases, where if the player has done any moves that prevents him from continuing, his turn will end and he will become passive

5.`Status getState()`
   -**Description**: Returns the current status of the game, whether it is in initialization state or ongoing or calculation or result state.
   -**Return Type**: `Status`
      - The Status class is an enum class that represents the 4 different stages of the game

6.`TurnShift getTurn()`
   -**Description**: Returns which player is active and in which round as well as which player is passive
   -**Return Type**: `TurnShift`
      - The Turnshift class is an enum class that represents all of the 3 different turns of each player where they are active and also the turns where they are passive


7.`int getRoundNumber()`
   -**Description**: Returns the number of the current round we are in
   -**Return Type**: `int`
      - Integer representing the current round of the game. 

### Player class

- **Package**: `game.engine`
- **Type**:  Class
- **Description**: This class represents the players and whether they are active or passive.

#### Methods:

1. `Player getPlayer()`
   - **Description**: Gets the current active player's information.
   - **Return Type**: `Player`
     - the active player object

2. `PlayerStatus getPlayerStatus`
   - **Description**: gets the starus of the player, whether they are active or passive
   - **Return Type**: `PlayerStatus`
      - whether a player is active or passive

3. `void switchPlayerStatus()`
   - **Description**: switched a players status, if they are active they will become passive and if they are passive they will become active

4. `GameScore getGameScore() `
   - **Description**: It gets the current score of the game.
   - **Return Type**: `GameScore`
     - The current gamescore object

5. `void setGameScore()`
   - **Description**: It sets the current score of the game.
   - **Parameters**:
     - `GameScore`: the current gamescore object

6. `ScoreSheet getScoreSheet()`
   - **Description**: It gets the score sheet for the player who is active now.
   - **Return Type**: `ScoreSheet`
     - The score sheet object for the current active player.

7. `void setScoreSheet()`
   - **Description**: It sets the score sheet for the current active player.
   - **Parameters**:
     - `Scoresheet`: The score sheet object for the current active player.

8. `Dice[] getUsedDice()`
   - **Description**: Gets the dice which were used.
   - **Return Type**: `Dice[]`
     - An array of the used dice by the active player

9. `int getElementalCrest()`
   - **Description**: It gets the number of the elemental crests for the current active player.
   - **Return Type**: `int`
     - integer representing the count of elemental crests

10. `void setName(String name)`
   - **Description**: sets the name of the player
   - **Parameters**:
      - `name`: the name of the player

11. `String getName()`
   - **Description**: gets the name of the player
   - **Return Type**: `String`
      - string containing the name of the player

### ScoreSheet class

- **Package**: `game.engine`
- **Type**: Class
- **Description**: This class represents the scores of all of the different creatures

#### Methods:

1. `Lion getLion()`
   - **Description**: gets the lion creature
   - **Return Type**: `Lion`
      - one of the invented characters in the game

2. `Dragon getDragon`
   - **Description**: gets the dragon creature
   - **Return Type**: `Dragon`
      - one of the invented characters in the game

3. `Guardians getGuardians`
   - **Description**: gets the guardian creature
   - **Return Type**: `Guardians`
      - one of the invented characters in the game

4. `Hydra getHydra()`
   - **Description**: gets the hydra creature
   - **Return Type**: `Hydra`
      - one of the invented characters in the game

5. `Phoenix getPhoenix`
   - **Description**: gets the phoenix creature
   - **Return Type**: `Phoenix`
      - one of the invented characters in the game

6. `String toString()`
   - **Description**: Returns a string representing the scores of all of the 5 different creatures
   - **Return Type**: `String`
      - The string class will print out the results of the scores

7. `int getElementalCrest()`
   - **Description**: gets the number of elemental crest
   - **Return Type**: `int`
      - number of elemental crests

8. `void incrementElementalCrest()`
   - **Description**: increments the number of elemental crests

9. `void incrementTimeWarp()`
   - **Description**: increments the number of time warps that a player has 

10. `void useTimeWarp()`
   - **Description**: lets the player use a time warp

11. `boolean timeWarpAvailable()`
   - **Description**: checks whether or not a time warp is available
   - **Return Type**: `boolean`
      -`true`: if time warp is available,
      -`false`: otherwise.

12. `void incrementArcaneBoost()`
   - **Description**: increments the number of arcane boosts by one

13. `boolean arcaneBoostAvailable()`
   - **Description**: checks whether or not the arcane boost can be used or not
   - **Return Type**: `boolean`
      -`true`: if it can be used,
      -`false`: otherwise.

14. `void useArcaneBoost()`
   - **Description**: lets a player use an arcane boost

15. `Creature getCreatureByRealm(Dice x)`
   - **Description**: takes a dice and depending on the realm of the dice return the corresponding creature
   - **Parameters**:
      `x`: a given dice
   - **Return Type**: `Creature`
      - one of the invented characters of the game

### Status class

-**Package**: `game.engine`
-**Type**: Enum Class
-**Description**: The Status class is an enum class that represents the 4 different stages of the game: initialization, ongoing, calculation and finally the result stage.

### TurnShift class

-**Package**: `game.engine`
-**Type**: Enum Class
-**Description**: The Turnshift class is an enum class that represents all of the 3 different turns of each player where they are active and also the turns where they are passive

### `PlayerStatus` class

- **Package**: `game.engine`
- **Type**: Enum Class
- **Description**: This enum class englobes active and passive. Active represents the player whose turn is currently underway and passive represents the player waiting for their turn while observing the active
wizard's actions. 

### `Realm` class

- **Package**: `game.engine`
- **Type**: Enum Class
- **Description**: This enum class englobes the five majestic realms' colors plus the ArcanePrism color which is white. 

### CLIGameController class

- **Package**: `game.engine`
- **Type**: Enum Class
- **Description**: Responsible for making the game loop function. It is the engine that links all of the different realms together with the user as well as with all of the reward.

#### Methods:

1. `void startGame()`
   - **Description**: method in charge of initiating the game by asking for the names of the 2 players and then informing the players in which round they are in, who's turn it is and what they are suppose to do by calling all of the methods related to the game flow

2. `void handleReward(Reward reward, Player p)`
   - **Description**: a method that given a specific reward and player handles how the player receives his reward and how it will be used
   - **Parameters**:
      - `reward`: one of the reward types: bonus, power or elemental crest
      - `p`: one of the users playing the game

3. `Move[] getPossiblleMovesForARealm(Player player, Dice dice)`
   - **Description**: a method that returns all of tbe possible moves for a realm inside of a move array
   - **Parameters**:
      - `player`: one of tbe users playing the game
      - `dice`: a given dice
   - **Return Type**: `Move[]`
      - an array containing multiple moves

4. `Reward[] getReward(Player activePlayer, Move move)`
   - **Description**: according to a given move and active player it returns the corresponding reward inside of an array of previously collected rewards
   - **Parameters**: 
      - `activePlayer`: the player whos currently playing his turn
      - `move`: a given attack
   - **Return Type**: `Reward[]`
      - an array englobing all of the collected rewards

5. `boolean switchPlayer()`
   - **Description**: switches the active and passive players, where who in the previous round was an active player will become the passive one and where the passive player will become the active one
   - **Return Type**:  `boolean`
      - `true`: if a switch between players has occured with no issue
      - `false`: if the switch has not occured

6. `Dice[] rollDice()`
   - **Description**: Method that rolls all of the different colored dices and returns them in an array
   - **Return Type**: `Dice[]`
      - an array of dices

7. `Dice[] getAvailableDice()`
   - **Description**: returns in a dice array all of the dices that are available and can be chosen by the player
   - **Return Type**: `Dice[]`
      - an array of dices

8. `Dice[] getAllDice()`
   - **Description**: gets all of the dices in the game board
   - **Return Type**: `Dice[]`
      - an array of dices

9. `Dice[] getArcaneBoostDice()`
   - **Description**: method that searched for an arcane boosted dice
   - **Return Type**: `Dice[]`
      - an array of dices

10. `Dice[] getForgottenRealDice()`
   - **Description**: gets all of the dices that have been sent to the forgotten realm
   - **Return Type**: `Dice[]`
      - an array of dices

11. `Move[] getAllPossibleMoves(Player player)`
   - **Description**: represents all of the possible moves of a player put inside of a move array
   - **Parameters**: 
      - `player`: one of the users who are playing the game
   - **Return Type**: `Move[]`
      - an array of moves

12. `Move[] getPossibleMovesForAvailableDice(Player player)`
   - **Description**: returns inside of a move array all of the possible moves a player can play according to the current available dices
   - **Parameters**:
      - `player`: one of the users who are playing the game
   - **Return Type**: `Move[]`
      - an array of moves

13. `Move[] getPossibleMovesForForgottenDice(Player player)`
   - **Description**: returns in a move array all of the possible moves for the forgotten dice
   - **Parameters**: 
      - `player`: one of the users who are playing the game
   - **Return Type**: `Move[]`
      - an array of moves

14. `Move[] getPossiblemovesForADie(Player player , Dice dice)`
   - **Description**: returns in a move array the set of possible moves for a single die

15. `Move[] getPossibleArcaneMoves(Player player)`
   - **Description**: method that returns all of the possible Arcane moves
   - **Parameters**:
      - `player`: one of the users who are playinh the game
   - **Return Type**: `Move[]`
      - an array of moves

16. `GameBoard getGameBoard()`
   - **Description**:returns the game board of the game
   - **Return Type**: `GameBoard`
      - the game board of the game

17. `Player getActivePlayer()`
   - **Description**: gets and returns the active player
   - **Return Type**: `Player`
      - one of the users playing the game
   
18. `Player getPassivePlayer`
   - **Description**: gets and returns the passive player
   - **Return Type**: `Player`
      - one of the users playing the game

19. `ScoreSheet getScoreSheet(Player player)`
   - **Description**: returns according to a given player his/her score sheet
   - **Parameters**: 
      - `player`: one of the users playing the game
   - **Return Type**: `ScoreSheet`
      -  the table containing the score sheet of the game

20. `GameStatus getGameStatus()`
   - **Description**: gets and returns the game status of the game board
   - **Return Type**: `GameStatus`
      - the status of the game

21. `GameScore getGameScore(Player player)`
   - **Description**: gets and returns the game score of a given player
   - **Parameters**: 
      - `player`: one of the users playing the game
   - **Return Type**: `GameScore`
      - the score of the game

22. `TimeWarp[] getTimeWarpPowers(Player player)`
   - **Description**: gets the time warp powers from the score sheet
   - **Parameters**: 
      - `player`: one of the users playing the game
   - **Return Type**: `TimeWarp[]`
      - an array of time warp powers

23. `ArcaneBoost[] getArcaneBoostPowers(Player player)`
   - **Description**: gets the arcane boost powers from the score sheet
   - **Parameters**:
    `player`: one of the users of the game
   - **Return Type**: `ArcaneBoost[]`
      - an arrat of arcane boosts
   
24. `boolean selectDice(Dice dice, Player player)`
   - **Description**: gets all of the dices chosen by each player whether it is during their passive or active turns
   - **Parameters**:
      - `dice`: a given dice
      - `player`: one of the users playing the game
   - **Return Type**: `boolean`
      - `true`: when the dice gets selected according to the specified dice player 
      - `false`: otherwise

25. `boolean makeMove(Player player, Move move)`
   - **Description**: method that calls the makeMove methods of a realm depending on the given move so that it can do an attack
   - **Parameters**: 
      - `player`: one of the users playing the game
      - `move`: represents a move in the game 
   - **Return Type**: `boolean`
      - `true`: after the attack has be successfully done
      - `false`: otherwise

26. `void setValue(Dice d)`
   - **Description**:
   - **Parameters**:
      - `d`: a given dice

27. `void updateScore(Player p, Creature c)`
   - **Description**: updates the score of the given realm of the given player
   - **Parameters**:
      - `p`: one of the users playing the game
      - `c`: one of the invented characters of the game

28. `String printGameScore(Player p)`
   - **Description**: prints the final game score of a given player
   - **Parameters**:
      - `p`: one of the users playing the game
   - **Return Type**: `String`
      - representing the final game score


###  AISequence class

-**Package**: `game.gui`
-**Type**:  enum Class
-**Description**: this enum class is responsible for making the game loop function of AI through 5 different stages:  rollDice, chooseMove, makeMove, handleReward and incrementTurn;

### DiceRealms class
-**Package**: game.gui
-**Type**: JavaFX Application Class
-**Description**: Entry point for the Dice Realms game application.

#### Method: 
1. void start(Stage primaryStage)
-**Description**: Initializes the primary stage for the application, loads the FXML file for the main scene, and sets up the stage properties.

2. void main(String[] args)
-**Description**: Entry point for the JavaFX application, launches the application.


### gui Class
-**Package**: game.gui
-**Type**: JavaFX Application Class
-**Description**: Responsible for creating and managing the graphical user interface (GUI) of the game.

#### Methods:
1. void start(Stage stage):
-**Description**: Initializes and displays the main stage of the GUI application.
-**Parameters**:
   -stage: The primary stage for this application, onto which the application scene can be set.

2. main(String[] args):

-**Description**: The main entry point for launching the JavaFX application.
-**Parameters**:
   - args: The command-line arguments passed to the application.   


### GUIGameController class
-**Package**: game.gui
-**Type**:  Class
-**Description**: Extends GameController and manages the game flow and user interactions in the GUI.
#### Methods:
1.	boolean getNotBonus()
-**Description**: Retrieves the value of the notBonus field.
-**Return Type**: 
-boolean: This occurs when the notBonus variable is set to true
2.	void startGame()
-**Description**: Starts the game.

3.	Void roundDisplay()
-**Description**: Displays the current round number.

4.	Void  ActiveTurnDisplay()
-**Description**: Displays the active player's turn.

5.	Void HandleStartOfRoundReward()
-**Description**: Handles the rewards at the start of a round.

6.	Void  RollDiceRequest()
-**Description**: Requests the active player to roll the dice.

7.	Void RollDice()
-**Description**: Rolls the dice for the active player.

8.	Void TimeWarp(String s)
-**Description**: Handles time warp scenarios based on user input.
-**Parameter**: 
-s - User input indicating whether to use time warp.
9.	Void  pickDice(Dice dice)
-**Description**: Handles the selection of a dice by the player.
-**Parameter**: 
-dice - The selected dice.
10.	Void  pickMove(Move m)
-**Description**: Handles the selection of a move by the player.
11.	Void  PassiveTurn(Dice dice)
-**Description**: Executes the passive turn for the player.
-**Parameter**: 
-dice - The selected dice.
12.	Void  pickMovePassive(Move m)
-**Description:** Handles the selection of a move during the passive turn.
-**Parameter**: 
-m - The selected move.
13.	Void : ArcaneBoostActive(String e)
-**Description**: Handles the activation of Arcane Boost.
-**Parameter**: 
-e - User input indicating whether to use Arcane Boost.
14.	Void  ArcaneActiveDice(Dice dice)
-**Description**: Handles the selection of a dice during Arcane Boost activation.
-**Parameter**: 
-dice - The selected dice.
15. void ArcaneActiveMove 
-**Description**: Handles an active move with an arcane dice.
16. void ArcaneBoostPassive 
-**Description**: Handles the passive player's decision to use ArcaneBoost.
17. void ArcanePassiveDice 
-**Description**: Handles the selection of a move with a passive arcane dice.
18.	Void ArcanePassiveMove 
-**Description**: Handles a passive move with an arcane dice.
19.	Void EndTurn method
-**Description**: Ends the current player's turn.
20.	Void WinScene method
-**Description**: Displays the win scene and announces the winner.
21.	Void handleReward method
-**Description**: Handles the rewards earned by the players during the game.
22.	public Move[] getPossibleMovesForARealm(Player player, Dice dice)
-**Description**: Returns an array of possible moves for a given player and dice according to the realm.
-**Return Type**: Move[] (Array of possible moves)
23.	public Reward[] getReward(Player activePlayer, Move move)
-**Description**: Returns the rewards for a given move and active player.
-**Return Type**: Reward[] (Array of rewards)
24.	public boolean switchPlayer()
-**Description**: Switches the active player.
-**Return Type**: boolean (True if the player switch is successful, false otherwise)
25.	public Dice[] rollDice()
-**Description**: Rolls the dice and returns an array of rolled dice.
-**Return Type:** Dice[] (Array of rolled dice)
26.	public Dice[] getAvailableDice()
-**Description**: Returns an array of available dice that can be used for making moves.
-**Return Type**: Dice[] (Array of available dice)
27.	public Dice[] getAllDice()
-**Description**: Returns an array of all dice on the game board.
-**Return Type**: Dice[] (Array of all dice)
28.	public Dice[] getArcaneBoostDice()
-**Description**: Returns an array of dice that can be boosted with an Arcane Prism.
-**Return Type**: Dice[] (Array of dice that can be boosted)
29.	public Dice[] getForgottenRealmDice()
-**Description**: Returns an array of dice in the forgotten realm.
-**Return Type**: Dice[] (Array of dice in the forgotten realm)
30.	public Move[] getAllPossibleMoves(Player player)
-**Description**: Returns all possible moves for a given player.
-**Return Type**: Move[] (Array of all possible moves)
31.	public Move[] getPossibleMovesForAvailableDice(Player player)
-**Description**: Returns an array of possible moves for a given player using available dice.
-**Return Type**: Move[] (Array of possible moves)
32.	public Move[] getPossibleMovesForForgottenDice(Player player)
-**Description**: Returns an array of possible moves for a given player using forgotten realm dice.
-**Return Type**: Move[] (Array of possible moves)
33.	public Move[] getPossibleMovesForADie(Player player, Dice dice)
-**Description**: Returns an array of possible moves for a given player and dice.
-**Return Type**: Move[] (Array of possible moves)
34.	public Move[] getPossibleArcaneMoves(Player player)
-**Description**: Returns an array of possible moves for a player using Arcane Prism boosted dice.
-**Return Type**: Move[] (Array of possible moves)
35.	public GameBoard getGameBoard()
-**Description**: Returns the current game board.
-**Return Type**: GameBoard (Current game board)
36.	public Player getActivePlayer()
-**Description**: Returns the active player.
-**Return Type**: Player (Active player)
37.	public Player getPassivePlayer()
-**Description**: Returns the passive player.
-**Return Type**: Player (Passive player)
38.	public ScoreSheet getScoreSheet(Player player)
-**Description**: Returns the score sheet of a given player.
-**Return Type**: ScoreSheet (Score sheet of the player)
39.	public GameStatus getGameStatus()
-**Description**: Returns the current game status.
-**Return Type**: GameStatus (Current game status)
40.	public GameScore getGameScore(Player player)
-**Description**: Returns the game score of a given player.
-**Return Type**: GameScore (Game score of the player)
41.	public TimeWarp[] getTimeWarpPowers(Player player)
-**Description**: Returns an array of time warp powers for a given player.
-**Return Type**: TimeWarp[] (Array of time warp powers)
42.	public ArcaneBoost[] getArcaneBoostPowers(Player player)
-**Description**: Returns an array of Arcane Boost powers for a given player.
-**Return Type**: ArcaneBoost[] (Array of Arcane Boost powers)
43.	public boolean selectDice(Dice dice, Player player)
-**Description**: Selects a dice for a given player to make a move.
-**Return Type**: boolean (True if the dice selection is successful, false otherwise)
44.	public boolean makeMove(Player player, Move move)
-**Description**: Makes a move for a given player.
-**Return Type**: boolean (True if the move is successful, false otherwise)
45.	public void setValue(Dice dice)
-**Description**: Sets the value of a dice.
46.	updateScore(Player p, Creature c)
-**Description**: Updates the score of a player based on the creature's score.
47.	-printGameScore(Player p)
-**Description**: Prints the game score of a player.
-**Return Type**: String (Returns a string representation of the player's game score)
48.	isNumeric(String strNum)
-**Description**: Checks if a string is numeric.
-**Return Type**: boolean (Returns true if the string is numeric, false otherwise)
49.	handleRewardAI(Reward reward, Player p)
-**Description**: Handles reward actions for the AI player.
50.	getAIMoves12()
-**Description**: Gets possible moves for the AI.
-**Return Type**: Move[] (Returns an array of possible moves for the AI)
51.	MoveChooser3p(int round)
-**Description**: Chooses a move for the AI in a 3-player game.
-**Return Type**: Move (Returns a single move chosen for the AI)
52.	moveChooserPassive(int round)
-**Description**: Chooses a move for the AI in a passive round.
-**Return Type**: Move (Returns a single move chosen for the AI)
53.	moveChooser12(int round)
-**Description**: Chooses a move for the AI in a 1v2 game.
-**Return Type**: Move (Returns a single move chosen for the AI)
54.	makeMoveAI(Move move)
-**Description**: Makes a move for the AI player.
-**Return Type**: boolean (Returns true if the move was successfully made, false otherwise)

### hi class
-**Package**: game.gui
-**Type**: JavaFX Application
-**Description**: Launches the JavaFX application, loads the initial FXML scene, and displays the primary stage.

#### Methods:
1. start(Stage primaryStage):
-**Description**: Initializes and sets up the primary stage for the JavaFX application.
-**Parameters**:
   -primaryStage: the main stage for this application, provided by the JavaFX runtime.
-**Throws**: Exception
-**Exception Handling**: Catches and prints any exceptions that occur during the loading of the FXML file, printing a stack trace and a message to the console.

2. main(String[] args):
-**Description**: The main entry point for the application, launches the JavaFX application.
-**Parameters**:
   -args: command-line arguments passed to the application.

### IntroSceneController Class
-**Package**: game.gui
-**Type**: Controller Class
-**Description**: Manages the introductory scene of the Dice Realms game. Provides functionality for menu navigation and starting the game.

#### Methods:
1. void menu(MouseEvent event)
-**Description**: Handles the menu button click event.
***Parameters**:
   -event: The mouse event triggered by the menu button.

2. void start(MouseEvent me)
-**Description**: Handles the start button click event. Transitions to the Choice of Play scene.
-**Parameters**:
   -me: The mouse event triggered by the start button.
-**Exception Handling**:
Catches and prints any exceptions that occur during the scene transition.

### ModeSelectController Class
-**Package**: game.gui
-**Type**: Controller Class
-**Description**: Manages the selection of game modes (AI or PvP) in the GUI. Handles the transition to the player name input scene for PvP mode.
#### Methods:
1. void AINames(MouseEvent event)
-**Description**: Handles the event when the AI mode button is clicked. (Currently not implemented)
-**Parameters**:
   -event: The mouse event triggered by clicking the AI mode button.

2. void PvPNames(MouseEvent event)
-**Description**: Handles the event when the PvP mode button is clicked. Transitions to the player name input scene.
-**Parameters**:
   -event: The mouse event triggered by clicking the PvP mode button.
-**Exception Handling**: Catches any exceptions during the scene transition and prints the stack trace.


### PlayerNameController Class
-**Package**: game.gui
-**Type**: Controller Class
-**Description**: Handles the functionality for inputting and setting player names, and managing interactions within the player name input scene.

#### Methods:
1. void initialize():
-**Description**: Initializes the text fields with prompt text if they are not null.

2. void handleInput():
-**Description**: Prints the input text from textfield1 to the console.

3. void handleInput2():
-**Description**: Prints the input text from textfield2 to the console.

4. void handleMouseDragOver1(MouseEvent event):
-**Description**: Changes the background color of textfield1 to yellow when the mouse is dragged over it.
-**Parameters**:
   -event: MouseEvent triggered by dragging the mouse over textfield1.

5. void handleMouseDragOver2(MouseEvent event):
-**Description**: Changes the background color of textfield2 to yellow when the mouse is dragged over it.
-**Parameters**:
   -event: MouseEvent triggered by dragging the mouse over textfield2.

6. void submit1(MouseEvent me):
-**Description**: Sets the text of name1 and updates the first player's name in the game board.
-**Parameters**:
   -me: MouseEvent triggered by clicking the button to submit the first player's name.

7. void submit2(MouseEvent me):
-**Description**: Sets the text of name2 and updates the second player's name in the game board.
-**Parameters**:
   -me: MouseEvent triggered by clicking the button to submit the second player's name.

8. void handleMouseDragExited1(MouseEvent event):
-**Description**: Resets the background color of textfield1 to its default style when the mouse exits.
-**Parameters**:
   -event: MouseEvent triggered by the mouse exiting textfield1.

9. void handleMouseDragExited2(MouseEvent event):
-**Description**: Resets the background color of textfield2 to its default style when the mouse exits.
-**Parameters**:
   -event: MouseEvent triggered by the mouse exiting textfield2.

10. void gonext(MouseEvent m):
-**Description**: Loads the SupermarketScene.fxml and transitions to the next scene.
-**Parameters**:
   -m: MouseEvent triggered by clicking the button to proceed to the next scene.
-**Exception Handling**: Includes exception handling to catch and print.

### PlayerSequence enum:
-**Package**: game.gui
-**Description**: Enum representing different sequences in the player's turn.

### SuperMarketController class
-**Package**: game.gui
-**Type**: Controller Class
-**Description**: Controls the user interface of the supermarket section in the game.

#### Methods:
1. static void setTextOut(String s)
-**Description**: Sets the text of the UserComm label.
-**Parameters**:
   -s: String - The text to set.

2. static void displayRolls(Dice[] d)
-**Description**: Displays the values of the dice on the corresponding labels.
-**Parameters**:
   -d: Dice[] - An array of dice objects.
   
3. static void waitForRoll()
-**Description**: Waits for the user to roll the dice.

4. static void waitForConfirm()
-**Description**: Waits for the user to confirm.

5. void incrementElementalCrest()
-**Description**: Increments the count of elemental crests.

6. void incrementArcaneBoost()
-**Description**: Increments the count of arcane boosts and updates the corresponding image views.

7. void incrementTimeWarp()
-**Description**: Increments the count of time warps and updates the corresponding image views.
8. void initialize(URL url, ResourceBundle resourceBundle)
-**Description**: Initializes the SuperMarketController with the provided URL and ResourceBundle.
-**Parameters**:
   -url - The URL to be initialized.
   -resourceBundle - The ResourceBundle to be initialized.

### Scene1Controller class
-**Package**: game.gui
-**Type**: controller Class
-**Description-**: Responsible for making the game loop function. It is the engine that links all of the different realms together with the user as well as with all of the reward.
#### Method:
1.	Move[] getPossiblleMovesForARealm(Player player, Dice dice)
-**Description-**: a method that returns all of the possible moves for a realm inside of a move array
-**Parameter-**:
   -player: one of the users playing the game
   -dice: a given dice
-**Return Type**: 
   -Move[]: an array containing multiple moves

### ScoreSheetSceneController class
-**Package**: game.gui
-**Type**: JavaFX Controller Class
-**Description**: Manages the score sheet scene, updating the labels with player scores and rewards.
#### Methods:
1.	 void initializeGreenScores()
-**Description**: Initializes the green scores for both players on the score sheet.
2.	Void initializeGreenRewards()
-**Description**: Initializes the green rewards for both players on the score sheet.
3.	Void initializeRedScores()
-**Description**: Initializes the red scores for both players on the score sheet.
4.	void initializeRedRewards()
-**Description**: Initializes the red rewards for both players on the score sheet.
5.	Void initializeYellowBlueMagentaRewards()
-**Description**: Initializes the yellow, blue, and magenta rewards for both players on the score sheet.
6.	Void setGUIGameController(GUIGameController gameController)
-**Description**: Sets the GUI game controller for the ScoreSheetSceneController instance.

### SuperMarketController class
-**Package**: game.gui
-**Type**: JavaFX Controller Class
-**Description**: Manages the supermarket scene, handling dice rolling, button clicks, and updating UI elements accordingly.
#### Methods:
1.	void initialize(URL location, ResourceBundle resources)
-**Description**: Initializes the supermarket scene, setting up UI components and printing a completion message.
2.	void updateComboBoxOptions(Move[] m)
-**Description**: Updates the options available in a ComboBox based on the provided array of moves.
3.	void initializeComboBoxOptions()
-**Description**: Initializes the ComboBox options with default values.
4.	void clearComboBoxOptions()
-**Description**: Clears all options from the ComboBox.
5.	void handleButton1(ActionEvent event)
-**Description**: Handles the action when Button 1 (associated with RedDice) is clicked, invoking the handleAll method.
6.	void handleButton2(ActionEvent event)
-**Description**: Handles the action when Button 2 (associated with GreenDice) is clicked, invoking the handleAll method.
7.	void handleButton3(ActionEvent event)
-**Description**: Handles the action when Button 3 (associated with BlueDice) is clicked, invoking the handleAll method.
8.	void handleButton4(ActionEvent event)
-**Description**: Handles the action when Button 4 (associated with MagentaDice) is clicked, invoking the handleAll method.
9.	void handleButton5(ActionEvent event)
-**Description**: Handles the action when Button 5 (associated with YellowDice) is clicked, invoking the handleAll method.
10. ´void handleButton6(ActionEvent event)
-**Description**: Handles the action when Button 6 (associated with WhiteDice) is clicked, invoking the handleAll method.
11. void rollDice(MouseEvent event)
-**Description**: Rolls all dice and updates their values on the UI.
12. void handleInvalidDiceSelectionException(InvalidDiceSelectionException e)
-**Description**: Handles the case when an invalid dice selection exception occurs, showing an error alert.
13. void printReward(String s)
-**Description**: Prints a reward message based on the provided string.
14. void handleReward(Reward reward, Player p)
-**Description**: Handles the reward received by a player, updating their score sheet accordingly.
15. void handleClick(String s) throws InvalidDiceSelectionException
-**Description**: Handles the click action based on the provided string, invoking appropriate methods.
16. void handleAll(Button dice) throws InvalidDiceSelectionException
-**Description**: Handles the click action for all dice buttons, delegating to the handleClick method.

### TotalSceneController class
-**Package**: game.gui
-**Type**: Controller Class
-**Description**: Manages the total score scene in the GUI.
#### Methods
1.	Void initializeTotalScore()
-**Description**: Initializes the total score display by setting the text of the labels to the respective scores of the players. Assigns labels to local variables for better readability and sets their text to the scores of Player 1 and Player 2.

2. Void PopUpWindow(MouseEvent event)
-**Description**: Displays a popup window showing the winner at the end of the game.
-**Parameters**:
o	event: MouseEvent
	The mouse event triggering the popup.


3. totalScoreP1()
-**Description**: Calculates the total score for Player 1, including the bonus for the smallest score multiplied by the number of crests.
-**Return Type**: 
-int: Returns the total calculated score for Player 1.
4. totalScoreP2()
-**Description**: Calculates the total score for Player 2, including the bonus for the smallest score multiplied by the number of crests.
-**Return Type**:
- int: Returns the total calculated score for Player 2.












         