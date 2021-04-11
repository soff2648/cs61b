package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

public class HallWay implements Serializable {
    Position start;
    Position end;
    Random random;
    Room roomOne;
    Room roomTwo;
    TETile[][] world;

    public HallWay(Random random, Room startRoom, Room endRoom, TETile[][] world) {
        this.random = random;
        this.roomOne = startRoom;
        this.roomTwo = endRoom;
        this.world = world;
        start = startRoom.randomPointInside();
        end = endRoom.randomPointInside();
    }

    public void pathOfHallWay() {
        if (start.getxPos() < end.getxPos() && start.getyPos() >= end.getyPos()) {
            drawLSUpperLeftToE();
        } else if (start.getxPos() < end.getxPos() && start.getyPos() < end.getyPos()) {
            //horizontal Line
            drawLSLowerLeftToE();
        } else if (start.getxPos() > end.getxPos() && start.getyPos() < end.getyPos()) {
            drawLSLowerRightToE();
        } else if (start.getxPos() > end.getxPos() && start.getyPos() > end.getyPos()) {
            drawLSUpperRightToE();
        } else if (start.getxPos() == end.getxPos()) {
            drawVertical();
        } else if (start.getyPos() == end.getyPos()) {
            drawHorizontal();
        }

    }
    /*
         s ---- e
         */
    private void drawHorizontal() {
        int largerX = Math.max(start.getxPos(), end.getxPos());
        int smallerX = Math.min(start.getxPos(), end.getxPos());
        int lineY = start.getyPos();
        for (int j = smallerX; j <= largerX; j++) {
            drawWallIfNothing(j, lineY - 1);
            world[j][lineY] = Tileset.FLOOR;
            drawWallIfNothing(j, lineY + 1);
        }
    }

    /*
              s
              |
              |
              e
         */
    private void drawVertical() {
        int largerY = Math.max(start.getyPos(), end.getyPos());
        int smallerY = Math.min(start.getyPos(), end.getyPos());
        int lineX = start.getxPos();
        for (int i = smallerY; i <= largerY; i++) {
            drawWallIfNothing(lineX + 1, i);
            world[lineX][i] = Tileset.FLOOR;
            drawWallIfNothing(lineX - 1, i);
        }
    }

    /*
                  s
                  |
            e ----|
    */
    private void drawLSUpperRightToE() {
        //horizontal Line
        int lineY = end.getyPos();
        int lineX = start.getxPos();
        for (int i = end.getxPos(); i <= lineX; i++) {
            drawWallIfNothing(i, lineY - 1);
            drawWallIfNothing(i, lineY + 1);
            world[i][lineY] = Tileset.FLOOR;
        }
        //vertical Line
        drawWallIfNothing(lineX + 1, lineY - 1);
        for (int j = end.getyPos(); j <= start.getyPos(); j++) {
            drawWallIfNothing(lineX + 1, j);
            drawWallIfNothing(lineX - 1, j);
            world[lineX][j] = Tileset.FLOOR;
        }
    }
    /*
            e
            |
            |
            -----s
    */
    private void drawLSLowerRightToE() {
        int lineX = end.getxPos();
        int lineY = start.getyPos();
        for (int i = end.getyPos(); i >= lineY; i--) {
            drawWallIfNothing(lineX - 1, i);
            drawWallIfNothing(lineX + 1, i);
            world[lineX][i] = Tileset.FLOOR;
        }
        drawWallIfNothing(lineX - 1, lineY - 1);
        //horizontal Line
        for (int j = lineX; j <= start.getxPos(); j++) {
            drawWallIfNothing(j, lineY + 1);
            drawWallIfNothing(j, lineY - 1);
            world[j][lineY] = Tileset.FLOOR;
        }
    }

    /*
                  e
                  |
            s ----|
    */
    private void drawLSLowerLeftToE() {
        int lineY = start.getyPos();
        int lineX = end.getxPos();
        for (int i = start.getxPos(); i <= lineX; i++) {
            drawWallIfNothing(i, lineY + 1);
            drawWallIfNothing(i, lineY - 1);
            world[i][lineY] = Tileset.FLOOR;
        }
        drawWallIfNothing(lineX + 1, lineY - 1);
        //vertical Line
        for (int j = start.getyPos(); j <= end.getyPos(); j++) {
            drawWallIfNothing(lineX - 1, j);
            drawWallIfNothing(lineX + 1, j);
            world[lineX][j] = Tileset.FLOOR;
        }
    }

    /*
            s
            |
            |
            -----e
         */
    private void drawLSUpperLeftToE() {
        //vertical line
        int lineX = start.getxPos();
        int lineY = end.getyPos();
        for (int i = start.getyPos(); i >= lineY; i--) {
            drawWallIfNothing(lineX - 1, i);
            world[lineX][i] = Tileset.FLOOR;
            drawWallIfNothing(lineX + 1, i);
        }
        //horizontal Line
        drawWallIfNothing(lineX - 1, lineY - 1);
        for (int j = lineX; j <= end.getxPos(); j++) {
            drawWallIfNothing(j, lineY - 1);
            drawWallIfNothing(j, lineY + 1);
            world[j][lineY] = Tileset.FLOOR;
        }
    }

    private void drawWallIfNothing(int x, int y) {
        if (world[x][y] == Tileset.NOTHING) {
            world[x][y] = Tileset.WALL;
        }
    }

}
