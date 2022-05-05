package at.fhooe.ai.rushhour;
/**
 * This is a template for the class corresponding to your original
 * advanced heuristic.  This class is an implementation of the
 * <tt>Heuristic</tt> interface.  After thinking of an original
 * heuristic, you should implement it here, filling in the constructor
 * and the <tt>getValue</tt> method.
 */
public class AdvancedHeuristic implements Heuristic {
	int carSize;
	int carPosition;
	int variablePosition;
	Puzzle puzzle;
	

    /**
     * This is the required constructor, which must be of the given form.
     */
    public AdvancedHeuristic(Puzzle puzzle) {
    	this.puzzle = puzzle;
  	  	this.carSize = puzzle.getCarSize(0);
  	  	this.carPosition = puzzle.getFixedPosition(0);
    }
	
    /**
     * This method returns the value of the heuristic function at the
     * given state.
     */
    public int getValue(State state) {
    	int ourEnd = this.variablePosition = state.getVariablePosition(0) + carSize;
    	return 6-ourEnd;
    }

}
