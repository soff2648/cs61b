package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class Solver {


    private class SearchNode {
        public WorldState currentState;
        public int moveNumber;
        public SearchNode previousNode;

        public SearchNode(WorldState state, int moves, SearchNode previous) {
            currentState = state;
            this.moveNumber = moves;
            previousNode = previous;
        }

    }



    private class searchNodeCompare implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode a, SearchNode b) {
            int distanceA;
            if (distances.containsKey(a.currentState)) {
                distanceA = distances.get(a.currentState);
            } else {
                distances.put(a.currentState, a.currentState.estimatedDistanceToGoal());
                distanceA = distances.get(a.currentState);
            }
            int distanceB;
            if (distances.containsKey(b.currentState)) {
                distanceB = distances.get(b.currentState);
            } else {
                distances.put(b.currentState, b.currentState.estimatedDistanceToGoal());
                distanceB = distances.get(b.currentState);
            }

            return (a.moveNumber + distanceA) - (b.moveNumber + distanceB);
        }
    }
    private MinPQ<SearchNode> pq = new MinPQ<>(new searchNodeCompare());
    private SearchNode initNode;
    private SearchNode endNode;

    private HashMap<WorldState, Integer> distances;


    public Solver(WorldState initial) {
        initNode = new SearchNode(initial, 0, null);
        pq.insert(initNode);
        distances= new HashMap<>();
    }

    public int moves() {
        SearchNode current = pq.delMin();
        while (!current.currentState.isGoal()) {
            for (var state : current.currentState.neighbors()) {
                if (current.previousNode == null) {
                    SearchNode node = new SearchNode(state, current.moveNumber + 1, current);
                    pq.insert(node);
                } else if (!state.equals(current.previousNode.currentState)) {
                    SearchNode node = new SearchNode(state, current.moveNumber + 1, current);
                    pq.insert(node);
                }
            }
            current = pq.delMin();
        }
        endNode = current;
        //System.out.println(counter2);
        return current.moveNumber;
    }

    public Iterable<WorldState> solution() {
        Stack<WorldState> stack = new Stack<>();
        stack.push(endNode.currentState);
        List<WorldState> list = new ArrayList<>();
        var current = endNode;
        while (!current.currentState.equals(initNode.currentState)) {
            stack.push(current.previousNode.currentState);
            current = current.previousNode;
        }
        while (!stack.isEmpty()) {
            list.add(stack.pop());
        }
        return list;
    }




}
