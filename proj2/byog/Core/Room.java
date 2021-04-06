package byog.Core;

import byog.TileEngine.TETile;

import java.util.Random;

import byog.TileEngine.Tileset;

public class Room implements Comparable<Room> {
    int xPos;
    int yPos;
    int widthOfRoom;
    int heightOfRoom;
    Random random;
    TETile[][] world;

    Room(Random random, Map map) {
        this.random = random;
        xPos = RandomUtils.uniform(random, map.widthOfWorld);
        yPos = RandomUtils.uniform(random, map.heightOfWorld);
        widthOfRoom = RandomUtils.uniform(random,  4, 15);
        heightOfRoom = RandomUtils.uniform(random, 4, 15);
        this.world = map.world;
    }

    Room(int x, int y, int w, int h) {
        xPos = x;
        yPos = y;
        widthOfRoom = w;
        heightOfRoom = h;
    }

    public Position upperRight() {
        return new Position(xPos, yPos + heightOfRoom - 1);
    }

    public Position bottomLeft() {
        return new Position(xPos + widthOfRoom - 1, yPos);
    }



    public boolean checkOverlap(Room other) {
        // If one rectangle is on left side of other
        if (other.upperRight().getxPos() > bottomLeft().getxPos()
                || upperRight().getxPos() > other.bottomLeft().getxPos()) {
            return false;
        }
        // if one rectangle is above the other
        if (other.bottomLeft().getyPos() > upperRight().getyPos()
                || bottomLeft().getyPos() > other.upperRight().getyPos()) {
            return false;
        }
        return true;

    }

    public void roomRender() {
        for (int y = yPos; y < yPos + this.heightOfRoom; y++) {
            for (int x = xPos; x < xPos + this.widthOfRoom; x++) {
                if (y == yPos || y == yPos + this.heightOfRoom - 1) {
                    world[x][y] = Tileset.WALL;
                } else if (x == xPos || x == xPos + this.widthOfRoom - 1) {
                    world[x][y] = Tileset.WALL;
                } else {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
    }

    public Position randomPointInside() {
        int pointX = RandomUtils.uniform(random, xPos + 1, xPos + this.widthOfRoom - 1);
        int pointY = RandomUtils.uniform(random, yPos + 1, yPos + this.heightOfRoom - 1);
        return new Position(pointX, pointY);
    }

    @Override
    public int compareTo(Room o) {
        if (this.xPos != o.xPos) {
            return this.xPos - o.xPos;
        }

        return this.yPos - o.yPos;
    }





}
