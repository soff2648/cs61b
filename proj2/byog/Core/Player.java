package byog.Core;

import byog.TileEngine.Tileset;

import java.io.Serializable;

public class Player implements Serializable {

    protected Position currentPosition;
    protected Position previousPosition;

    public Player(Map map) {
        Position init = map.initialPositionOfPlayer();
        int x = init.getxPos();
        int y = init.getyPos();
        currentPosition = new Position(x, y);
        previousPosition = new Position(x, y);
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public Position getPreviousPosition() {
        return previousPosition;
    }

    public boolean moveUp(Map map) {
        previousPosition = currentPosition;
        var nextPostion = new Position(currentPosition.getxPos(), currentPosition.getyPos() + 1);
        if (map.checkPosition(nextPostion, Tileset.WALL)) {
            return false;
        }
        if (map.checkPosition(nextPostion, Tileset.LOCKED_DOOR)) {
            map.drawPosition(nextPostion, Tileset.UNLOCKED_DOOR);
            return true;
        }
        currentPosition = nextPostion;
        return false;
    }

    public boolean moveRight(Map map) {
        previousPosition = currentPosition;
        var nextPostion = new Position(currentPosition.getxPos() + 1, currentPosition.getyPos());
        if (map.checkPosition(nextPostion, Tileset.WALL)) {
            return false;
        }
        if (map.checkPosition(nextPostion, Tileset.LOCKED_DOOR)) {
            map.drawPosition(nextPostion, Tileset.UNLOCKED_DOOR);
            return true;
        }
        currentPosition = nextPostion;
        return false;
    }

    public boolean moveDown(Map map) {
        previousPosition = currentPosition;
        var nextPostion = new Position(currentPosition.getxPos(), currentPosition.getyPos() - 1);
        if (map.checkPosition(nextPostion, Tileset.WALL)) {
            return false;
        }
        if (map.checkPosition(nextPostion, Tileset.LOCKED_DOOR)) {
            map.drawPosition(nextPostion, Tileset.UNLOCKED_DOOR);
            return true;
        }
        currentPosition = nextPostion;
        return false;
    }

    public boolean moveLeft(Map map) {
        previousPosition = currentPosition;
        var nextPostion = new Position(currentPosition.getxPos() - 1, currentPosition.getyPos());
        if (map.checkPosition(nextPostion, Tileset.WALL)) {
            return false;
        }
        if (map.checkPosition(nextPostion, Tileset.LOCKED_DOOR)) {
            map.drawPosition(nextPostion, Tileset.UNLOCKED_DOOR);
            return true;
        }
        currentPosition = nextPostion;
        return false;
    }

}
