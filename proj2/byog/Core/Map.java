package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;


import java.util.Random;
import java.util.Arrays;

public class Map {


    TERenderer ter;
    TETile[][] world;
    Room[] rooms;
    int roomsNum;
    Random random;
    int widthOfWorld;
    int heightOfWorld;

    Map(Random random, int width, int height, TERenderer ter) {
        this.ter = ter;
        widthOfWorld = width;
        heightOfWorld = height;
        world = new TETile[widthOfWorld][heightOfWorld];
        ter.initialize(width, height);
        this.random = random;
        roomsNum = RandomUtils.uniform(random, 15, 20);
        rooms = new Room[roomsNum];
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


}
