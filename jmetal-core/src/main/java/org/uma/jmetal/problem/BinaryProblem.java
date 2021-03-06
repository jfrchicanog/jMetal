package org.uma.jmetal.problem;

import org.uma.jmetal.core.Problem;
import org.uma.jmetal.encoding.BinarySolution;

/** Interface representing binary problems */
public interface BinaryProblem extends Problem<BinarySolution> {
  public int getNumberOfBits(int index) ;
}
