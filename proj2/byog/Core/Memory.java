package byog.Core;

import java.io.Serializable;

public class Memory implements Serializable {
    Player player;
    String inputString;

    public Memory(Player player, String inputString) {
        this.player = player;
        this.inputString = inputString;
    }
}
