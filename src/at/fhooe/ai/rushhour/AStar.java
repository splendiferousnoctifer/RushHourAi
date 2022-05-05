package at.fhooe.ai.rushhour;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This is the template for a class that performs A* search on a given rush hour
 * puzzle with a given heuristic. The main search computation is carried out by
 * the constructor for this class, which must be filled in. The solution (a path
 * from the initial state to a goal state) is returned as an array of
 * <tt>State</tt>s called <tt>path</tt> (where the first element
 * <tt>path[0]</tt> is the initial state). If no solution is found, the
 * <tt>path</tt> field should be set to <tt>null</tt>. You may also wish to
 * return other information by adding additional fields to the class.
 */
public class AStar {

	/** The solution path is stored here */
	public State[] path;

	List<Node> openList = new ArrayList<Node>();
	List<Node> closedList = new ArrayList<Node>();

	/**
	 * This is the constructor that performs A* search to compute a solution for the
	 * given puzzle using the given heuristic.
	 */
	public AStar(Puzzle puzzle, Heuristic heuristic) {
		int heuristicValue = heuristic.getValue(puzzle.getInitNode().getState());

		Node initNode = puzzle.getInitNode();

		openList.add(initNode);
		boolean toSort = false;
		while (!openList.isEmpty()) {

			if (toSort) {

				openList.sort((Node node1, Node node2) -> {
					if ((node1.getDepth() + heuristic.getValue(node1.getState())) < (node2.getDepth()
							+ heuristic.getValue(node2.getState()))) {
						return -1;
					} else if ((node1.getDepth() + heuristic.getValue(node1.getState())) == (node2.getDepth()
							+ heuristic.getValue(node2.getState()))) {
						return 0;
					} else {
						return 1;
					}
				});
				toSort = false;
			}

			Node current = openList.remove(0);
			int costs = current.getDepth() + heuristicValue;

			if (current.getState().isGoal()) {
				int depth = current.getDepth();
				this.path = new State[depth + 1];

				while (current != null) {
					path[current.getDepth()] = current.getState();
					current = current.getParent();
				}
				return;
			}

			closedList.add(current);

			for (Node expanded : current.expand()) {
				costs = expanded.getDepth() + heuristic.getValue(expanded.getState());

				if (!closedList.contains(expanded)) {
					if (openList.contains(expanded)) {
						Node old = openList.get(openList.indexOf(expanded));
						if (old != null) {
							int oldCosts = old.getDepth() + heuristic.getValue(old.getState());
							if (costs < oldCosts) {
								openList.remove(old);
								openList.add(expanded);
								toSort = true;
							}
						}
					} else {
						openList.add(expanded);
						toSort = true;
					}

				}
			}
		}

	}

}
