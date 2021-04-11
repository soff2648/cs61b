package byog.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

public class Display implements Serializable {
    private static final int WIDTH_UI = 40;
    private static final int HEIGHT_UI = 60;

    private static String titleString = "CS61B: THE GAME";
    private static String newGameString = "New Game (N)";
    private static String loadGameString = "Load Game (L)";
    private static String quitGameString = "Quit Game (Q)";

    public Display() {
        StdDraw.setCanvasSize(WIDTH_UI * 16, HEIGHT_UI * 16);
        StdDraw.setXscale(0, WIDTH_UI);
        StdDraw.setYscale(0, HEIGHT_UI);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public void displayTitle() {

        StdDraw.clear();
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        Font font1 = new Font("Monaco", Font.PLAIN, 45);
        StdDraw.setFont(font1);
        StdDraw.text(WIDTH_UI / 2, 3 * HEIGHT_UI / 4, titleString);

        Font font2 = new Font("Monaco", Font.PLAIN, 20);
        StdDraw.setFont(font2);
        StdDraw.text(WIDTH_UI / 2, HEIGHT_UI / 2 - 2, newGameString);
        StdDraw.text(WIDTH_UI / 2, HEIGHT_UI / 2 - 4, loadGameString);
        StdDraw.text(WIDTH_UI / 2, HEIGHT_UI / 2 - 6, quitGameString);
        StdDraw.show();
    }

    public void displaySeedInput(String seedString) {
        StdDraw.clear();
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        Font font1 = new Font("Monaco", Font.PLAIN, 45);
        StdDraw.setFont(font1);
        StdDraw.text(WIDTH_UI / 2, 3 * HEIGHT_UI / 4, titleString);

        Font font2 = new Font("Monaco", Font.PLAIN, 20);
        StdDraw.setFont(font2);
        StdDraw.text(WIDTH_UI / 2, HEIGHT_UI / 2 - 2, newGameString);
        StdDraw.text(WIDTH_UI / 2, HEIGHT_UI / 2 - 4, loadGameString);
        StdDraw.text(WIDTH_UI / 2, HEIGHT_UI / 2 - 6, quitGameString);
        Font font3 = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font3);
        StdDraw.text(WIDTH_UI / 2, HEIGHT_UI / 2 - 10, "Enter Seed: ");
        StdDraw.text(WIDTH_UI / 2, HEIGHT_UI / 2 - 11, seedString);
        StdDraw.show();
    }

    public void displayGameStart() {
        StdDraw.clear();
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        Font font1 = new Font("Monaco", Font.BOLD, 45);
        StdDraw.setFont(font1);
        StdDraw.text(WIDTH_UI / 2, 2 * HEIGHT_UI / 4, "Game Start!");
        StdDraw.show();
        StdDraw.pause(500);
    }

    public static void main(String[] args) {
        Display display = new Display();
        display.displayTitle();
    }


}
