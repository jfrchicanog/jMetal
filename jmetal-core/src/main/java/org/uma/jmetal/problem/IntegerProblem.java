package org.uma.jmetal.problem;

import org.uma.jmetal.core.Problem;
import org.uma.jmetal.encoding.IntegerSolution;

/** Interface representing integer problems */
public interface IntegerProblem extends Problem<IntegerSolution> {
  public Integer getLowerBound(int index) ;
  public Integer getUpperBound(int index) ;
}
