package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;
import org.junit.Test;

import java.util.Comparator;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    MinPQ<Integer> fringe;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        fringe = new MinPQ<>(new EdgeComparator());
        fringe.insert(s);
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return manhattan(v);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar() {

        int current = fringe.delMin();
        marked[current] = true;
        while (current != t) {

            for (int w : maze.adj(current)) {
                if (distTo[w] > distTo[current] + 1) {
                    distTo[w] = distTo[current] + 1;
                    edgeTo[w] = current;
                    fringe.insert(w);
                }
            }
            current = fringe.delMin();
            announce();
            marked[current] = true;
        }
        marked[current] = true;
        announce();
    }

    private int manhattan(int v) {
        return Math.abs(maze.toX(t) - maze.toX(v)) + Math.abs(maze.toY(t) - maze.toY(v));
    }

    @Override
    public void solve() {
        astar();
    }

    private class EdgeComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer v1, Integer v2) {
            return (distTo[v1] + h(v1)) - (distTo[v2] + h(v2));
        }
    }

    @Test
    public void testComparator() {
        edgeTo[10] = 1;
        edgeTo[11] = 2;
        edgeTo[12] = 3;

    }

}

