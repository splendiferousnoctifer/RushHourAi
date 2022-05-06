package at.fhooe.ai.rushhour;

import java.util.List;
import java.util.ArrayList;
/**
 * This is a template for the class corresponding to your original
 * advanced heuristic.  This class is an implementation of the
 * <tt>Heuristic</tt> interface.  After thinking of an original
 * heuristic, you should implement it here, filling in the constructor
 * and the <tt>getValue</tt> method.
 */
public class AdvancedHeuristic implements Heuristic {
	int numCars;
	int carSize;
	int carPosition;
	int variablePosition;
	List<Integer> blockingCars;
	int blockingCount; 

	Puzzle puzzle;
    /**
     * This is the required constructor, which must be of the given form.
     */
    public AdvancedHeuristic(Puzzle puzzle) {
    	this.puzzle = puzzle;
  	  	this.carSize = puzzle.getCarSize(0);
  	  	this.carPosition = puzzle.getFixedPosition(0);
  	  	this.numCars = this.puzzle.getNumCars();
  	  	this.blockingCars = new ArrayList<Integer>();
  	  	this.blockingCount = 0;
    }
	
    /**
     * This method returns the value of the heuristic function at the
     * given state.
     */
    public int getValue(State state) {
    	if (state.isGoal()) {
			return 0;
		}
    	this.blockingCount = 0;
    	int ourEnd = this.variablePosition = state.getVariablePosition(0) + carSize;
    	int distance =  6-ourEnd;
    	
    	blockingCars = getBlockingCars(0, state);
    	
    	for(int car : blockingCars) {
    		
    	}
    	
    	return 0;
    }
   
    
    private List<Integer> getBlockingCars(int car, State state){
    	List<Integer> blocking = new ArrayList<Integer>();
    	
        for(int i = 1; i < this.numCars; i++) {
        	if(this.puzzle.getCarOrient(car) == this.puzzle.getCarOrient(i)) {
        		continue;
        	}
        	int ourEnd = state.getVariablePosition(car) + this.puzzle.getCarSize(car);
        	
        	if(this.puzzle.getFixedPosition(i) < ourEnd) {
        		continue;
        	}
        	if(state.getVariablePosition(i)+this.puzzle.getCarSize(i) >= this.puzzle.getCarSize(car) && state.getVariablePosition(i) <= this.puzzle.getCarSize(car)) {
        		blocking.add(i);
        		blockingCount++;
        	}
        }
        return blocking;
    }
    
}
