package at.fhooe.ai.rushhour;
/**
 * This is a template for the class corresponding to the blocking heuristic.
 * This heuristic returns zero for goal states, and otherwise returns one plus
 * the number of cars blocking the path of the goal car to the exit. This class
 * is an implementation of the <tt>Heuristic</tt> interface, and must be
 * implemented by filling in the constructor and the <tt>getValue</tt> method.
 */
public class BlockingHeuristic implements Heuristic {
	int numCars;
	int carSize;
	int carPosition;
	Puzzle puzzle;
	

  /**
   * This is the required constructor, which must be of the given form.
   */
  public BlockingHeuristic(Puzzle puzzle) {
	  this.puzzle = puzzle;
	  this.numCars = puzzle.getNumCars();
	  this.carSize = puzzle.getCarSize(0);
	  this.carPosition = puzzle.getFixedPosition(0);
  }

  /**
   * This method returns the value of the heuristic function at the given state.
   */
  public int getValue(State state) {
    if(state.isGoal()) {
    	return 0;
    }
    int blocking = 1;
    for(int i = 1; i < this.numCars; i++) {
    	if(!this.puzzle.getCarOrient(i)) {
    		continue;
    	}
    	int ourEnd = state.getVariablePosition(0) + this.carSize;
    	
    	if(this.puzzle.getFixedPosition(i) < ourEnd) {
    		continue;
    	}
    	if(state.getVariablePosition(i)+this.puzzle.getCarSize(i) > this.carPosition && state.getVariablePosition(i) <= this.carPosition) {
    		blocking++;
    	}
    }
    return blocking;
  }

}
