package at.fhooe.ai.rushhour;

public class SortableNode extends Node  implements Comparable<SortableNode> {
	
	private int costs;

	public SortableNode(Node node, int costs) {
		super(node.getState(), node.getDepth(), node.getParent());
		this.costs = costs;
	}

	@Override
	public int compareTo(SortableNode o) {
		if (this.costs < o.costs) {
			return -1;
		} else if (this.costs == o.costs) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int hashCode() {
		return this.getState().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SortableNode other = (SortableNode) obj;
		return ((Node) other).getState().equals(this.getState());
	}

}
