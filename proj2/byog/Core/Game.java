package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;


import java.awt.Color;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Game implements Serializable {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final String WASD = "wasd";
    public static final String NUMBERS = "1234567890";
    KeyBoardUI keyboard;
    Random mainRandom;
    StringBuilder gameMemory;
    Display display;
    Map map;
    Player player;
    boolean gameOver = false;
    boolean gameStart = false;
    boolean seedTyped = false;
    boolean newGame = true;

    boolean inGame = false;
    boolean inTitlePage = false;
    boolean haveSeed = false;
    boolean isNewGame = true;
    boolean gameWinned = false;

    long seed;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */

    public Game() {
        keyboard = new KeyBoardUI();
        gameMemory = new StringBuilder();
    }

    public void playWithKeyboard() {
        inTitlePage = true;
        display = new Display();
        while (true) {
            inTitlePage = true;
            while (inTitlePage) {

                display.displayTitle();
                if (!StdDraw.hasNextKeyTyped()) {
                    continue;
                }
                String inputString = keyboard.getLatestInput();
                if (!checkIfNTyped(inputString)) {
                    isNewGame = true;
                    haveSeed = false;
                    break;
                }

                if (checkIfLTyped(inputString)) {
                    inTitlePage = false;
                    //playWithInputString(gameMemory.toString());
                    isNewGame = false;
                    readMemory(gameMemory);
                }
            }
            StringBuilder seedNumber = new StringBuilder();

            while (!haveSeed) {
                display.displaySeedInput(seedNumber.toString());
                if (!StdDraw.hasNextKeyTyped()) {
                    continue;
                }
                getSeedInput(seedNumber);
            }

            if (isNewGame) {
                generateMap();
            } else {
                generateMap(player);
            }
            StringBuilder quitString = new StringBuilder();
            inGame = true;
            while (!gameWinned && inGame) {
                displayHelpMessage();
                if (!StdDraw.hasNextKeyTyped()) {
                    continue;
                }
                String inputString = keyboard.getLatestInput();
                if (checkIfQuit(inputString, quitString)) {
                    inGame = false;
                }
                moveWASD(inputString);
            }
            if (gameWinned) {
                gameWinned();
            }
        }
    }

    private void moveWASD(String inputString) {
        if (WASD.contains(inputString)) {
            if (inputString.equals("w")) {
                gameWinned = player.moveUp(map);
                gameMemory.append(inputString);
            } else if (inputString.equals("d")) {
                gameWinned = player.moveRight(map);
                gameMemory.append(inputString);
            } else if (inputString.equals("s")) {
                gameWinned = player.moveDown(map);
                gameMemory.append(inputString);
            } else if (inputString.equals("a")) {
                gameWinned = player.moveLeft(map);
                gameMemory.append(inputString);
            }
        }
    }

    private void getSeedInput(StringBuilder seedNumber) {

        String inputString = keyboard.getLatestInput();
        if (NUMBERS.contains(inputString)) {
            gameMemory.append(inputString);
            seedNumber.append(inputString);

        } else if (inputString.equals("s") && seedNumber.length() > 0) {
            display.displaySeedInput(seedNumber.toString());
            gameMemory.append(inputString);
            seed = Long.parseLong(seedNumber.toString());
            mainRandom = new Random(seed);
            display.displayGameStart();
            haveSeed = true;
        }
    }

    private boolean checkIfNTyped(String inputString) {
        if (inputString.equals("n")) {
            inTitlePage = false;
            gameMemory.append(inputString);
        }
        return inTitlePage;
    }

    private boolean checkIfLTyped(String inputString) {
        if (inputString.equals("l")) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("memory.txt"));
                Memory game = (Memory) ois.readObject();
                if (game != null) {
                    gameMemory = new StringBuilder(game.inputString);
                    player = game.player;
                    return true;
                }

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("io");
                return false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("class not found");
                return false;
            } catch (ClassCastException e) {
                return false;
            }
        }
        return false;
    }

    private boolean checkIfQuit(String inputString, StringBuilder quitString) {
        if (inputString.equals(":")) {
            quitString.append(inputString);
            return false;
        }
        if (quitString.toString().contains(":")) {
            if (inputString.equals("q")) {
                gameOver = true;
                map.display(WIDTH / 2, HEIGHT / 2, "you quit the game");
                gameMemory.append(quitString);
                gameMemory.append(inputString);
                StdDraw.pause(50);
                StdDraw.clear(Color.BLACK);
                StdDraw.show();
                inGame = false;
                display = new Display();
                try {
                    FileOutputStream file = new FileOutputStream("memory.txt");
                    ObjectOutputStream oos = new ObjectOutputStream(file);
                    oos.writeObject(new Memory(player, gameMemory.toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            } else {
                quitString.deleteCharAt(0);
            }
        }
        return false;
    }

    private void readMemory(StringBuilder memory) {
        char[] chars = memory.toString().toLowerCase().toCharArray();
        StringBuilder seedString = new StringBuilder();
        int count = 0;
        if (chars[count] == 'n') {
            count += 1;
        }
        while (chars[count] != 's') {
            seedString.append(chars[count]);
            count += 1;
        }
        haveSeed = true;
        seed = Long.parseLong(seedString.toString());
        mainRandom = new Random(seed);
    }

//    public void playWithKeyboard() {
//
//        display.displayTitle();
//        //Start a new game
//        while (!gameStart) {
//
//            if (!StdDraw.hasNextKeyTyped()) {
//                continue;
//            }
//            String inputString = keyboard.getLatestInput();
//
//            if (inputString.equals("n")) {
//                gameStart = true;
//                gameMemory.append(inputString);
//            }
//
//            if (inputString.equals("l")) {
//                if (gameMemory.length()!= 0) {
//                    newGame = false;
//                    gameMemory.append(inputString);
//                    playWithInputString(gameMemory.toString());
//                }
//            }
//        }
//
//
//        //input of seed
//        StringBuilder seedNumber = new StringBuilder();
//        while (!seedTyped) {
//            display.displaySeedInput(seedNumber.toString());
//            if (!StdDraw.hasNextKeyTyped()) {
//                continue;
//            }
//            System.out.println("wait for Seed");
//            String inputString = keyboard.getLatestInput();
//            if (numbers.contains(inputString)) {
//                gameMemory.append(inputString);
//                seedNumber.append(inputString);
//
//            } else if (inputString.equals("s") && seedNumber.length() > 0) {
//                gameMemory.append(inputString);
//                seed = Long.parseLong(seedNumber.toString());
//                mainRandom = new Random(seed);
//                display.displayGameStart();
//                seedTyped = true;
//            }
//        }
//        if (newGame) {
//            generateMap();
//        }
//        StringBuilder quitString = new StringBuilder();
//        while (!gameOver) {
//            displayHelpMessage(map);
//            System.out.println(gameMemory.toString());
//            if (!StdDraw.hasNextKeyTyped()) {
//                continue;
//            }
//            String inputString = keyboard.getLatestInput();
//            System.out.println(inputString);
//            if (inputString.equals(":")) {
//                quitString.append(inputString);
//                continue;
//            }
//            if (quitString.toString().contains(":")) {
//                if (inputString.equals("q")) {
//                    gameOver = true;
//                    map.display(WIDTH/2, HEIGHT/2, "you quit the game");
//                    gameMemory.append(quitString);
//                    gameMemory.append(inputString);
//                    StdDraw.pause(1000);
//                    StdDraw.clear(Color.BLACK);
//                    StdDraw.show();
//                } else {
//                    quitString.deleteCharAt(0);
//                }
//            }
//
//            moveWASD(inputString);
//        }
//        displayHelpMessage(map);
//        map.display(WIDTH/2, HEIGHT/2, "you win the game");
//        StdDraw.pause(1000);
//        StdDraw.clear(Color.BLACK);
//        StdDraw.show();
//        System.out.println(gameMemory.toString());
//        startAgain();
//
//    }

    public void startAgain() {
        gameStart = false;
        gameStart = false;
        seedTyped = false;
        display = new Display();
        display.displayTitle();
        //playWithKeyboard();
    }

    private void generateMap() {
        map = new Map(mainRandom, WIDTH, HEIGHT, ter);
        player = new Player(map);
        map.displayInitialMap(player);
    }

    private void generateMap(Player p) {
        this.map = new Map(mainRandom, WIDTH, HEIGHT, ter);
        this.player = p;
        map.displayInitialMap(player);

    }

    private void updatePlayer(Map m) {
        m.updatePlayer(player);
    }

    private void displayHelpMessage() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        if (x < WIDTH && y < HEIGHT) {
            String helpMessage = checkTETile(x, y, map);
            map.display(helpMessage, player);
        }
    }

    private String checkTETile(int x, int y, Map m) {
        String returnTileName;
        var tile = m.world[x][y];
        if (tile.equals(Tileset.FLOOR)) {
            returnTileName = "Floor";
        } else if (tile.equals(Tileset.PLAYER)) {
            returnTileName = "Player";
        } else if (tile.equals(Tileset.WALL)) {
            returnTileName = "Wall";
        } else if (tile.equals(Tileset.LOCKED_DOOR)) {
            returnTileName = "Locked Door";
        } else {
            returnTileName = "Nothing";
        }
        return returnTileName;
    }

    private void gameWinned() {
        displayHelpMessage();
        map.display(WIDTH / 2, HEIGHT / 2, "you win the game");
        StdDraw.pause(1000);
        StdDraw.clear(Color.BLACK);
        StdDraw.show();
        display = new Display();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TO DO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        char[] chars = input.toLowerCase().toCharArray();
        int count = 0;
        if (chars[count] == 'n') {
            gameStart = true;
            count += 1;
        }
        StringBuilder seedString = new StringBuilder();
        while (count < chars.length && chars[count] != 's') {
            if (NUMBERS.contains(chars[count] + "")){
                seedString.append(chars[count]);
                count += 1;
            }
        }

        try {
            if (chars[count] == 's') {
                seedTyped = true;
            }
        } catch (Exception e) {
            map = new Map(WIDTH, HEIGHT);
            return map.world;
        }

        if (seedTyped) {
            seed = Long.parseLong(seedString.toString());
            mainRandom = new Random(seed);
            map = new Map(mainRandom, WIDTH, HEIGHT);
            player = new Player(map);
            updatePlayer(map);

            for (count += 1; count < chars.length; count++) {

                String inputString = "" + chars[count];
                if (WASD.contains(inputString)) {
                    if (inputString.equals("w")) {
                        gameWinned = player.moveUp(map);
                    } else if (inputString.equals("d")) {
                        gameWinned = player.moveRight(map);
                    } else if (inputString.equals("s")) {
                        gameWinned = player.moveDown(map);
                    } else if (inputString.equals("a")) {
                        gameWinned = player.moveLeft(map);
                    }
                }
                updatePlayer(map);
                if (inputString.equals(":") && chars[count + 1] == 'q') {
                    count += 1;
                }
            }

            TETile[][] finalWorldFrame = map.world;
            return finalWorldFrame;
        }

        return null;
    }
}



