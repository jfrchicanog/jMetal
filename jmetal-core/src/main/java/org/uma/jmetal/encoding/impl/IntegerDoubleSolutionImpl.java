package org.uma.jmetal.encoding.impl;

import org.uma.jmetal45.util.random.PseudoRandom;
import org.uma.jmetal.core.Solution;
import org.uma.jmetal.encoding.IntegerDoubleSolution;
import org.uma.jmetal.problem.IntegerDoubleProblem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Antonio J. Nebro on 03/09/14.
 */
public class IntegerDoubleSolutionImpl
        extends GenericSolutionImpl<Number, IntegerDoubleProblem>
        implements IntegerDoubleSolution {
  private int numberOfIntegerVariables ;
  private int numberOfDoubleVariables ;

  /** Constructor */
  public IntegerDoubleSolutionImpl(IntegerDoubleProblem problem) {
  	this.problem = problem ;
    objectives = new ArrayList<>(problem.getNumberOfObjectives()) ;
    variables = new ArrayList<>(problem.getNumberOfVariables()) ;
    numberOfIntegerVariables = problem.getNumberOfIntegerVariables() ;
    numberOfDoubleVariables = problem.getNumberOfDoubleVariables() ;
    overallConstraintViolationDegree = 0.0 ;

    for (int i = 0 ; i < numberOfIntegerVariables; i++) {
      Integer value = PseudoRandom.randInt((Integer)getLowerBound(i), (Integer)getUpperBound(i)) ;
      variables.add(value) ;
    }

    for (int i = numberOfIntegerVariables ; i < getNumberOfVariables(); i++) {
      Double value = PseudoRandom.randDouble((Double)getLowerBound(i), (Double)getUpperBound(i)) ;
      variables.add(value) ;
    }

    for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
      objectives.add(new Double(0.0)) ;
    }
  }

  /** Copy constructor */
  public IntegerDoubleSolutionImpl(IntegerDoubleSolutionImpl solution) {
    problem = solution.problem ;
    objectives = new ArrayList<>() ;
    for (Double obj : solution.objectives) {
      objectives.add(new Double(obj)) ;
    }
    variables = new ArrayList<>() ;
    for (int i = 0 ; i < numberOfIntegerVariables; i++) {
      variables.add(new Integer((Integer) solution.getVariableValue(i))) ;
    }

    variables = new ArrayList<>() ;
    for (int i = numberOfIntegerVariables ; i < (numberOfIntegerVariables+numberOfDoubleVariables); i++) {
      variables.add(new Double((Double) solution.getVariableValue(i))) ;
    }

    overallConstraintViolationDegree = solution.overallConstraintViolationDegree ;
    attributes = new HashMap(solution.attributes) ;
  }

  @Override
  public Number getUpperBound(int index) {
    return problem.getUpperBound(index);
  }

  @Override
  public int getNumberOfIntegerVariables() {
    return numberOfIntegerVariables;
  }

  @Override
  public int getNumberOfDoubleVariables() {
    return numberOfDoubleVariables;
  }

  @Override
  public Number getLowerBound(int index) {
    return problem.getLowerBound(index) ;
  }

  @Override
  public Solution<?> copy() {
    return new IntegerDoubleSolutionImpl(this);
  }
}
