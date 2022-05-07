package at.fhooe.ai.rushhour;

import java.util.List;
import java.util.ArrayList;

/**
 * This is a template for the class corresponding to your original advanced
 * heuristic. This class is an implementation of the <tt>Heuristic</tt>
 * interface. After thinking of an original heuristic, you should implement it
 * here, filling in the constructor and the <tt>getValue</tt> method.
 */
public class AdvancedHeuristic implements Heuristic {
	int numCars;
	int carSize;
	int carPosition;
	int variablePosition;
	List<Integer> blockingCars;
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
	}

	/**
	 * This method returns the value of the heuristic function at the given state.
	 */
	public int getValue(State state) {
		if (state.isGoal()) {
			return 0;
		}
		int blockingCount = 0;
		int ourEnd = this.variablePosition = state.getVariablePosition(0) + carSize;
		int distance = 6 - ourEnd;

		blockingCars.add(0);
		for (int car : getinitBlockingCars(0, state)) {

			blockingCount += this.getAllBlockingCars(car, this.getMoveSpace(0, car, 0,state, true),
					this.getMoveSpace(0, car, 0,state, false), state);
		}
		return blockingCount;
	}

	private int getAllBlockingCars(int car, int moveSpaceFront, int moveSpaceBack, State state) {
		blockingCars.add(car);
		
		int blocking = 1;
		for (int i = 0; i < numCars; i++) {
			int movesForward = 0;
			int movesBackward = 0;
			if (i != car && !blockingCars.contains(i)) {
				if (this.isPotentiallyBlocking(car, i, state)) {					
					if (this.isCarRelevent(car, i, state, true)) {
						if (!this.isEnoughSpace(car, i, moveSpaceFront, true, state)) {
							movesForward += this.getAllBlockingCars(i, this.getMoveSpace(car, i, moveSpaceFront,state, true),
									this.getMoveSpace(car, i, moveSpaceBack, state, false), state);
							movesBackward = Integer.MAX_VALUE;
						} else if (this.bumpIntoWall(car, moveSpaceFront, true, state)) {
							movesForward = Integer.MAX_VALUE;
						}
					} else if (this.isCarRelevent(car, i, state, false)) {
						if (!this.isEnoughSpace(car, i, moveSpaceBack, false, state)) {
							movesBackward += this.getAllBlockingCars(i, this.getMoveSpace(car, i, moveSpaceFront, state, true),
									this.getMoveSpace(car, i, moveSpaceBack, state, false), state);
							movesForward = Integer.MAX_VALUE;
						} else if (this.bumpIntoWall(car, moveSpaceBack, false, state)) {
							movesBackward = Integer.MAX_VALUE;
						}
					}
				}
				blocking += Math.min(movesForward, movesBackward);
			}
			
		}
		
		return blocking;
	}

	private boolean isEnoughSpace(int car, int blockingCar, int spaceNeeded, boolean forward, State state) {
		
		if(forward) {
			if(this.isOrientationEqual(car, blockingCar)) {
				return spaceNeeded <= Math.abs(state.getVariablePosition(car) + this.puzzle.getCarSize(car) - state.getVariablePosition(blockingCar));
			}
			return spaceNeeded <= Math.abs(state.getVariablePosition(car) + this.puzzle.getCarSize(car) - this.puzzle.getFixedPosition(blockingCar));
		} else {
			if(this.isOrientationEqual(car, blockingCar)) {
				return spaceNeeded <= Math.abs(state.getVariablePosition(car) - (state.getVariablePosition(blockingCar) + this.puzzle.getCarSize(blockingCar)));
			}
			return spaceNeeded <= Math.abs(state.getVariablePosition(car)- (this.puzzle.getFixedPosition(blockingCar) + 1)) ;
		}

	}
	
	private boolean isOrientationEqual(int car, int blockingCar) {
		return this.puzzle.getCarOrient(car) == this.puzzle.getCarOrient(blockingCar);
	}

	private boolean bumpIntoWall(int car, int moveSpace, boolean forward, State state) {
		if (forward) {
			return (state.getVariablePosition(car) + this.puzzle.getCarSize(car) + moveSpace > this.puzzle
					.getGridSize());
		} else {
			return (state.getVariablePosition(car) - moveSpace < 0);
		}
	}

	private List<Integer> getinitBlockingCars(int car, State state) {
		List<Integer> blocking = new ArrayList<Integer>();

		for (int i = 1; i < this.numCars; i++) {
			if (this.isOrientationEqual(car, i)) {
				continue;
			}
			int ourEnd = state.getVariablePosition(car) + this.puzzle.getCarSize(car);

			if (this.puzzle.getFixedPosition(i) < ourEnd) {
				continue;
			}
			if (state.getVariablePosition(i) + this.puzzle.getCarSize(i) > this.puzzle.getFixedPosition(car)
					&& state.getVariablePosition(i) <= this.puzzle.getFixedPosition(car)) {
				blocking.add(i);
			}
		}
		return blocking;
	}

	private boolean isPotentiallyBlocking(int car, int blockingCar, State state) {
		if (this.isOrientationEqual(car, blockingCar)) {
			return this.puzzle.getFixedPosition(car) == this.puzzle.getFixedPosition(blockingCar);
		}

		if (this.puzzle.getFixedPosition(car) >= state.getVariablePosition(blockingCar)
				+ this.puzzle.getCarSize(blockingCar)) {
			return false;
		}

		if (this.puzzle.getFixedPosition(car) == state.getVariablePosition(blockingCar)) {
			return true;
		}

		if (this.puzzle.getFixedPosition(car) >= state.getVariablePosition(blockingCar) && this.puzzle
				.getFixedPosition(car) < state.getVariablePosition(blockingCar) + this.puzzle.getCarSize(blockingCar)) {
			return true;
		}

		return false;
	}

	private boolean isCarRelevent(int car, int blockingCar, State state, boolean forward) {
		if(this.isOrientationEqual(car, blockingCar)) {
			return (state.getVariablePosition(blockingCar) < state.getVariablePosition(car) && !forward) || (state.getVariablePosition(blockingCar) > state.getVariablePosition(car) && forward);
		}
		return (this.puzzle.getFixedPosition(blockingCar) > state.getVariablePosition(car) + this.puzzle.getCarSize(car)
				&& forward)
				|| (this.puzzle.getFixedPosition(blockingCar) < state.getVariablePosition(car)
						+ this.puzzle.getCarSize(car) && !forward);
	}

	private int getMoveSpace(int car, int blockingcar, int moveSpacePrev, State state, boolean forward) {
		if(this.isOrientationEqual(car, blockingcar)) {
			return moveSpacePrev; 
		}
		if (forward) {
			return Math.abs(this.puzzle.getFixedPosition(car) - state.getVariablePosition(blockingcar)) + 1;
		} else {
			return Math.abs(this.puzzle.getFixedPosition(car)
					- (state.getVariablePosition(blockingcar) + this.puzzle.getCarSize(blockingcar)));
		}
	}
}
