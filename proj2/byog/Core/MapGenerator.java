package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.util.Random;

public class MapGenerator {

    protected static Random random;
    protected static Map map;

    public static void generateMap(Random randomFromSeed, int width, int height, TERenderer ter) {
        random = randomFromSeed;
        map = new Map(random, width, height, ter);
        map.drawBackGround();
        map.getRooms();
        map.generateHallWay();
        map.renderFrame();
    }

    public static TETile[][] mapWorld() {
        return map.world;
    }
}
