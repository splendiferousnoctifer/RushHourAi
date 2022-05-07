package at.fhooe.ai.rushhour;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

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

	Queue<SortableNode> openList = new PriorityQueue<SortableNode>(20, new Comparator<SortableNode>() {
		// override compare method
		public int compare(SortableNode i, SortableNode j) {
			return Integer.compare(i.getCosts(), j.getCosts());
		}

	});

	Set<SortableNode> closedList = new HashSet<SortableNode>();

	/**
	 * This is the constructor that performs A* search to compute a solution for the
	 * given puzzle using the given heuristic.
	 */
	public AStar(Puzzle puzzle, Heuristic heuristic) {
		int startcosts = heuristic.getValue(puzzle.getInitNode().getState());
		SortableNode initNode = new SortableNode(puzzle.getInitNode(), startcosts);

		openList.add(initNode);

		while (!openList.isEmpty()) {

			SortableNode current = openList.poll();

			if (current.getState().isGoal()) {
				int depth = current.getDepth();
				this.path = new State[depth + 1];
				Node currentNode = current;
				while (currentNode != null) {
					path[currentNode.getDepth()] = currentNode.getState();
					currentNode = currentNode.getParent();
				}
				return;
			}
			closedList.add(current);
			for (Node expanded : current.expand()) {
				int costs = heuristic.getValue(expanded.getState());
				SortableNode expandedComp = new SortableNode(expanded, costs);
				if (closedList.contains(expandedComp)) {
					continue;
				} else if (!openList.contains(expandedComp)) {
					openList.add(expandedComp);
				} else if (openList.contains(expandedComp)) {
					Iterator<SortableNode> itr = openList.iterator();
					SortableNode existing = null;
					while (itr.hasNext()) {
						SortableNode currentNode = itr.next();
						if (currentNode.equals(expandedComp)) {
							existing = currentNode;
						}
					}
					if (existing != null) {
						if (existing.getCosts() >= expandedComp.getCosts()) {
							openList.remove(existing);
							openList.add(expandedComp);
						} else {
							continue;
						}
					}
				}

			}
		}
	}

}
