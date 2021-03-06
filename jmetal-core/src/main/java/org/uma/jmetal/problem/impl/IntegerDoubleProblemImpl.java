package org.uma.jmetal.problem.impl;

import org.uma.jmetal.core.Solution;
import org.uma.jmetal.problem.IntegerDoubleProblem;

import java.util.List;

public abstract class IntegerDoubleProblemImpl<S extends Solution<Number>>
  extends GenericProblemImpl<S>
  implements IntegerDoubleProblem<S> {

  private int numberOfIntegerVariables ;
  private int numberOfDoubleVariables ;

  private List<Number> lowerLimit ;
  private List<Number> upperLimit ;

  /* Getters */
  public int getNumberOfDoubleVariables() {
    return numberOfDoubleVariables;
  }

  public int getNumberOfIntegerVariables() {
    return numberOfIntegerVariables;
  }

	@Override
	public Number getUpperBound(int index) {
		return upperLimit.get(index);
	}

	@Override
	public Number getLowerBound(int index) {
		return lowerLimit.get(index);
	}

  /* Getters */
  /* Setters */
  protected void setNumberOfDoubleVariables(int numberOfDoubleVariables) {
    this.numberOfDoubleVariables = numberOfDoubleVariables;
  }

  protected void setNumberOfIntegerVariables(int numberOfIntegerVariables) {
    this.numberOfIntegerVariables = numberOfIntegerVariables;
  }

  protected void setLowerLimit(List<Number> lowerLimit) {
    this.lowerLimit = lowerLimit;
  }

  protected void setUpperLimit(List<Number> upperLimit) {
    this.upperLimit = upperLimit;
  }
}
