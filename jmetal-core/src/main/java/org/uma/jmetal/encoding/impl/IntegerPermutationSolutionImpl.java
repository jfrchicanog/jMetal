package org.uma.jmetal.encoding.impl;

import org.uma.jmetal.core.Solution;
import org.uma.jmetal.encoding.PermutationSolution;
import org.uma.jmetal.problem.PermutationProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Antonio J. Nebro on 03/09/14.
 */
public class IntegerPermutationSolutionImpl
        extends GenericSolutionImpl<List<Integer>, PermutationProblem>
        implements PermutationSolution<List<Integer>> {

  /** Constructor */
  public IntegerPermutationSolutionImpl(PermutationProblem problem) {
    this.problem = problem ;
    objectives = new ArrayList<>(problem.getNumberOfObjectives()) ;
    variables = new ArrayList<>(problem.getNumberOfVariables()) ;
    overallConstraintViolationDegree = 0.0 ;

    for (int i = 0; i < problem.getNumberOfVariables(); i++) {
      ArrayList<Integer> randomSequence = new ArrayList<>(problem.getPermutationLength(i));

      for (int j = 0; j < problem.getPermutationLength(i); i++) {
        randomSequence.add(i);
      }

      java.util.Collections.shuffle(randomSequence);
      variables.set(i, randomSequence) ;
    }
  }

  /** Copy Constructor */
  public IntegerPermutationSolutionImpl(IntegerPermutationSolutionImpl solution) {
    problem = solution.problem ;
    objectives = new ArrayList<>() ;
    for (Double obj : solution.objectives) {
      objectives.add(new Double(obj)) ;
    }
    variables = new ArrayList<>() ;
    for (List<Integer> var : solution.variables) {
      ArrayList<Integer> list = new ArrayList<>() ;
      for (Integer element : var) {
        var.add(new Integer(element)) ;
      }
      variables.add(list) ;
    }

    overallConstraintViolationDegree = solution.overallConstraintViolationDegree ;
    attributes = new HashMap(solution.attributes) ;
  }

  @Override
  public Solution<?> copy() {
    return new IntegerPermutationSolutionImpl(this);
  }
}
