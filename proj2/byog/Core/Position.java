package byog.Core;

import java.io.Serializable;

public class Position implements Serializable {
    protected int xPos;
    protected int yPos;

    public Position(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

}
