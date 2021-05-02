package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;

    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> fringe = new ArrayDeque<>();
        fringe.add(s);
        marked[s] = true;
        announce();


        while (!fringe.isEmpty()) {
            int ver = fringe.remove();
            marked[ver] = true;
            for (int w : maze.adj(ver)) {
                if (!marked[w]) {
                    distTo[w] = distTo[ver] + 1;
                    edgeTo[w] = ver;
                    marked[w] = true;
                    announce();
                    if (w == t) {
                        return;
                    }
                    fringe.add(w);
                }
            }
        }

    }

    @Override
    public void solve() {
        bfs();
    }
}

