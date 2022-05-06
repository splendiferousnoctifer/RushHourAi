package at.fhooe.ai.rushhour;

import java.util.Objects;

public class SortableNode extends Node {
	
	private int totalCosts;

	public SortableNode(Node node, int h) {
		super(node.getState(), node.getDepth(), node.getParent());
		this.totalCosts = node.getDepth() + h;
	}

	
	public int getCosts() {
		return this.totalCosts;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(this.getState());
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
		return Objects.equals(this.getState(), other.getState());
	}
	
	

}
