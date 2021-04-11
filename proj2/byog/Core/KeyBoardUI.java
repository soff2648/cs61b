package byog.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.io.Serializable;

public class KeyBoardUI implements Serializable {


    public String getLatestInput() {
        char newInput = StdDraw.nextKeyTyped();
        String returnString = "" + newInput;
        return returnString.toLowerCase();
    }

}
