package at.fhooe.ai.rushhour;

import java.util.ArrayList;
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

	List<SortableNode> openList = new ArrayList<SortableNode>();
	List<SortableNode> closedList = new ArrayList<SortableNode>();

	/**
	 * This is the constructor that performs A* search to compute a solution for the
	 * given puzzle using the given heuristic.
	 */
	public AStar(Puzzle puzzle, Heuristic heuristic) {
		int costs = heuristic.getValue(puzzle.getInitNode().getState()) + puzzle.getInitNode().getDepth();

		SortableNode initNode = new SortableNode(puzzle.getInitNode(), costs);

		openList.add(initNode);
		boolean toSort = false;
		while (!openList.isEmpty()) {
			
			if(toSort) {
			openList.sort((SortableNode node1, SortableNode node2) -> {
				return node1.compareTo(node2);
			});
			
			toSort=false;
			}
			Node current = openList.remove(0);
			
			if (current.getState().isGoal()) {
				int depth = current.getDepth();
				this.path = new State[depth + 1];

				while (current != null) {
					path[current.getDepth()] = current.getState();
					current = current.getParent();
				}
				return;
			}

			closedList.add((SortableNode)current);

			for (Node expanded : current.expand()) {
				costs = expanded.getDepth() + heuristic.getValue(expanded.getState());
				SortableNode expandedComp = new SortableNode(expanded, costs);

				if (!closedList.contains(expandedComp)) {
					if (openList.contains(expandedComp)) {
						SortableNode old = openList.get(openList.indexOf(expandedComp));
						if (old != null) {
							if (expandedComp.compareTo(old) > 0) {
								openList.remove(old);
								openList.add(expandedComp);
								toSort = true;
							}
						}
					} else {
						openList.add(expandedComp);
						toSort = true;
					}

				}
			}
		}

	}

}
