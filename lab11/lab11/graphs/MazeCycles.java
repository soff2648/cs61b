package lab11.graphs;

import edu.princeton.cs.algs4.Stack;


/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int s;
    private Maze maze;
    private Stack<Integer> parents;
    private boolean hasCycle;

    public MazeCycles(Maze m) {
        super(m);

        maze = m;
        s = maze.xyTo1D(1, 1);
        parents = new Stack<>();
    }

    @Override
    public void solve() {
        dfs(s, 0);
    }

    private void dfs(int w, int parent) {
        if (hasCycle) {
            return;
        }

        announce();
        marked[w] = true;
        parents.push(w);
        for (int v : maze.adj(w)) {
            if (!marked[v] && !hasCycle) {
                announce();
                marked[v] = true;
                dfs(v, w);
            } else if (v != parent && !hasCycle) {
                hasCycle = true;
                drawCycle(v, parents);
                return;
            }
        }

    }

    public void drawCycle(int w, Stack<Integer> stack) {
        int last = stack.pop();
        int current = w;
        while (last != w) {
            edgeTo[current] = last;
            current = last;
            last = stack.pop();
        }
        edgeTo[current] = last;
        announce();
    }

}

