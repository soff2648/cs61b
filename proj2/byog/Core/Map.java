package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;


import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;
import java.util.Arrays;

public class Map implements Serializable {


    TERenderer ter;
    TETile[][] world;
    Room[] rooms;
    int roomsNum;
    Random random;
    int widthOfWorld;
    int heightOfWorld;
    Position door;

    Map(Random random, int width, int height, TERenderer ter) {
        this.ter = ter;
        widthOfWorld = width;
        heightOfWorld = height;
        world = new TETile[widthOfWorld][heightOfWorld];
        ter.initialize(width, height);
        this.random = random;
        roomsNum = RandomUtils.uniform(random, 15, 20);
        rooms = new Room[roomsNum];
        generateInitalMap();
    }

    Map(Random random, int width, int height) {
        widthOfWorld = width;
        heightOfWorld = height;
        world = new TETile[widthOfWorld][heightOfWorld];
        this.random = random;
        roomsNum = RandomUtils.uniform(random, 15, 20);
        rooms = new Room[roomsNum];
        generateInitalMap();
    }

    Map(int width, int height) {
        widthOfWorld = width;
        heightOfWorld = height;
        world = new TETile[widthOfWorld][heightOfWorld];
        drawBackGround();
    }

    void drawBackGround() {

        for (int i = 0; i < widthOfWorld; i++) {
            for (int j = 0; j < heightOfWorld; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    void renderFrame() {
        ter.renderFrame(world);
    }

    void getRooms() {
        for (int i = 0; i < roomsNum; i++) {
            Room newRoom = new Room(random, this);
            while (roomInvalid(newRoom)) {
                newRoom = new Room(random, this);
            }
            rooms[i] = newRoom;
            drawRoom(newRoom);
        }
        Arrays.sort(rooms);
    }

    boolean roomInvalid(Room newRoom) {
        if (newRoom.upperRight().getyPos() >= heightOfWorld
                || newRoom.bottomLeft().getxPos() >= widthOfWorld) {
            return true;
        }

        for (var room : rooms) {
            if (room == null) {
                return false;
            }
            if (room.checkOverlap(newRoom)) {
                return true;

            }
        }
        return false;
    }

    void drawRoom(Room room) {
        room.roomRender();
    }

    void generateHallWay() {
        HallWay hw;
        for (int i = 0; i < roomsNum - 1; i++) {
            hw = new HallWay(random, rooms[i], rooms[i + 1], world);
            hw.pathOfHallWay();
        }
    }

    public void createDoor() {
        int randomRoom = RandomUtils.uniform(random, roomsNum);
        boolean isValidPoint = false;

        while (!isValidPoint) {
            door = rooms[randomRoom].randomPointOnWall();
            isValidPoint = checkIfOnWall(door);
        }
        world[door.getxPos()][door.getyPos()] = Tileset.LOCKED_DOOR;
    }

    public Position initialPositionOfPlayer() {
        int randomRoom = RandomUtils.uniform(random, roomsNum);
        Position positionOfPlayer = null;
        boolean isValidPoint = false;

        while (!isValidPoint) {
            positionOfPlayer = rooms[randomRoom].randomPointInside();
            isValidPoint = checkIfFloor(positionOfPlayer);
        }

        return positionOfPlayer;
    }

    private boolean checkIfFloor(Position player) {
        var result = world[player.getxPos()][player.getyPos()];
        return world[player.getxPos()][player.getyPos()] == Tileset.FLOOR;
    }


    private boolean checkIfOnWall(Position position) {
        java.util.Map<TETile, Integer> counter = new HashMap<>();
        int x = position.getxPos();
        int y = position.getyPos();
        if (x >= widthOfWorld - 1 || x <= 1 || y <= 1 || y >= heightOfWorld - 1) {
            return false;
        }

        try {
            if (counter.containsKey(world[x + 1][y])) {
                counter.put(world[x + 1][y], counter.get(world[x + 1][y]) + 1);
            } else {
                counter.put(world[x + 1][y], 1);
            }
            if (counter.containsKey(world[x - 1][y])) {
                counter.put(world[x - 1][y], counter.get(world[x - 1][y]) + 1);
            } else {
                counter.put(world[x - 1][y], 1);
            }
            if (counter.containsKey(world[x][y + 1])) {
                counter.put(world[x][y + 1], counter.get(world[x][y + 1]) + 1);
            } else {
                counter.put(world[x][y + 1], 1);
            }
            if (counter.containsKey(world[x][y - 1])) {
                counter.put(world[x][y - 1], counter.get(world[x][y - 1]) + 1);
            } else {
                counter.put(world[x][y - 1], 1);
            }
        } catch (Exception e) {
            return false;
        }
        if (counter.get(Tileset.NOTHING) == null
                || counter.get(Tileset.FLOOR) == null
                || counter.get(Tileset.WALL) == null) {
            return false;
        }

        return counter.get(Tileset.NOTHING) == 1
                && counter.get(Tileset.FLOOR) == 1
                && counter.get(Tileset.WALL) == 2;
    }

    public void updatePlayer(Player player) {
        if (player != null) {
            drawPosition(player.previousPosition, Tileset.FLOOR);
            drawPosition(player.currentPosition, Tileset.PLAYER);
        }
    }

    public void drawPosition(Position position, TETile tile) {
        world[position.getxPos()][position.getyPos()] = tile;
    }

    private void generateInitalMap() {
        drawBackGround();
        getRooms();
        generateHallWay();
        createDoor();
    }

    public void displayInitialMap(Player player) {

        if (player != null) {
            updatePlayer(player);
        }

        renderFrame();
    }

    public void display(String helpMessage, Player player) {
        StdDraw.clear(Color.BLACK);
        renderFrame();
        displayHelpMessage(helpMessage);
        updatePlayer(player);
        StdDraw.show();
        StdDraw.pause(50);
    }

    public void display(int x, int y, String message) {
        Font font = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.ORANGE);
        StdDraw.text(x, y, message);
        StdDraw.show();
    }

    private void displayHelpMessage(String helpMessage) {
        Font font = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.textLeft(0, 29, helpMessage);
    }

    public boolean checkPosition(Position position, TETile tile) {
        return world[position.getxPos()][position.getyPos()].equals(tile);
    }

}
