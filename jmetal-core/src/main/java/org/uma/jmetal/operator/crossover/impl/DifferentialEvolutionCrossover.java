//  DifferentialEvolutionCrossover.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package org.uma.jmetal.operator.crossover.impl;

import org.uma.jmetal45.util.JMetalException;
import org.uma.jmetal45.util.JMetalLogger;
import org.uma.jmetal45.util.random.PseudoRandom;
import org.uma.jmetal.encoding.DoubleSolution;
import org.uma.jmetal.operator.crossover.CrossoverOperator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * Differential evolution crossover operator
 * Comments:
 * - The operator receives two parameters: the current individual and an array
 * of three parent individuals
 * - The best and rand variants depends on the third parent, according whether
 * it represents the current of the "best" individual or a random one.
 * The implementation of both variants are the same, due to that the parent
 * selection is external to the crossover operator.
 * - Implemented variants:
 * - rand/1/bin (best/1/bin)
 * - rand/1/exp (best/1/exp)
 * - current-to-rand/1 (current-to-best/1)
 * - current-to-rand/1/bin (current-to-best/1/bin)
 * - current-to-rand/1/exp (current-to-best/1/exp)
 */
public class DifferentialEvolutionCrossover implements
  CrossoverOperator<List<DoubleSolution>, List<DoubleSolution>> {

  private static final double DEFAULT_CR = 0.5;
  private static final double DEFAULT_F = 0.5;
  private static final double DEFAULT_K = 0.5;

  private static final String DEFAULT_DE_VARIANT = "rand/1/bin";

  private static final String[] VALID_VARIANTS = {
    "rand/1/bin",
    "best/1/bin",
    "rand/1/exp",
    "best/1/exp",
    "current-to-rand/1",
    "current-to-best/1",
    "current-to-rand/1/bin",
    "current-to-best/1/bin",
    "current-to-rand/1/exp",
    "current-to-best/1/exp"
  } ;

  private double cr;
  private double f;
  private double k;
  // DE variant (rand/1/bin, rand/1/exp, etc.)
  private String variant;

  private DoubleSolution currentSolution ;

  /** Constructor */
  public DifferentialEvolutionCrossover(Builder builder) {
    cr = builder.cr;
    f = builder.f;
    k = builder.k;
    variant = builder.variant;
  }

  /* Getters */
  public double getCr() {
    return cr;
  }

  public double getF() {
    return f;
  }

  public double getK() {
    return k;
  }

  public String getVariant() {
    return variant;
  }

  /** Builder class */
  public static class Builder {
    private double cr;
    private double f;
    private double k;
    // DE variant (rand/1/bin, rand/1/exp, etc.)
    private String variant;

    public Builder() {
      cr = DEFAULT_CR ;
      f = DEFAULT_F ;
      k = DEFAULT_K ;
      variant = DEFAULT_DE_VARIANT ;
    }

    public Builder setCr(double cr) {
      if ((cr < 0) || (cr > 1.0)) {
        throw new JMetalException("Invalid CR value: " + cr ) ;
      } else {
        this.cr = cr ;
      }

      return this ;
    }

    public Builder setF(double f) {
      if ((f < 0) || (f > 1.0)) {
        throw new JMetalException("Invalid F value: " + f) ;
      } else {
        this.f = f;
      }

      return this ;
    }

    public Builder setK(double k) {
      if ((k < 0) || (k > 1.0)) {
        throw new JMetalException("Invalid K value: " + k) ;
      } else {
        this.k = k;
      }

      return this ;
    }

    public Builder setVariant(String variant) {
      Vector<String> validVariants = new Vector<String>(Arrays.asList(VALID_VARIANTS)) ;
      if (validVariants.contains(variant)) {
        this.variant = variant ;
      } else {
        throw new JMetalException("Invalid DE variant: " + variant) ;
      }

      return this ;
    }
    public DifferentialEvolutionCrossover build() {
      return new DifferentialEvolutionCrossover(this) ;
    }
  }

  public void setCurrentSolution(DoubleSolution current) {
    this.currentSolution = current ;
  }

  /** Execute() method */
  @Override
  public List<DoubleSolution> execute(List<DoubleSolution> parentSolutions) {

    DoubleSolution child;

    int jrand;

    child = (DoubleSolution)currentSolution.copy() ;

    int numberOfVariables = parentSolutions.get(0).getNumberOfVariables();
    jrand = PseudoRandom.randInt(0, numberOfVariables - 1);

    // STEP 4. Checking the DE variant
    if (("rand/1/bin".equals(variant)) ||
            "best/1/bin".equals(variant)) {
      for (int j = 0; j < numberOfVariables; j++) {
        if (PseudoRandom.randDouble(0, 1) < cr || j == jrand) {
          double value;
          value = parentSolutions.get(2).getVariableValue(j) + f * (parentSolutions.get(0).getVariableValue(
            j) -
            parentSolutions.get(1).getVariableValue(j));

          if (value < child.getLowerBound(j)) {
            value = child.getLowerBound(j);
          }
          if (value > child.getUpperBound(j)) {
            value = child.getUpperBound(j);
          }
          child.setVariableValue(j, value);
        } else {
          double value;
          value = currentSolution.getVariableValue(j);
          child.setVariableValue(j, value);
        }
      }
    } else if ("rand/1/exp".equals(variant) ||
            "best/1/exp".equals(variant)) {
      for (int j = 0; j < numberOfVariables; j++) {
        if (PseudoRandom.randDouble(0, 1) < cr || j == jrand) {
          double value;
          value = parentSolutions.get(2).getVariableValue(j) + f * (parentSolutions.get(0).getVariableValue(j) -
                  parentSolutions.get(1).getVariableValue(j));

          if (value < child.getLowerBound(j)) {
            value = child.getLowerBound(j);
          }
          if (value > child.getUpperBound(j)) {
            value = child.getUpperBound(j);
          }

          child.setVariableValue(j, value);
        } else {
          cr = 0.0;
          double value;
          value = currentSolution.getVariableValue(j);
          child.setVariableValue(j, value);
        }
      }
    } else if ("current-to-rand/1".equals(variant) ||
            "current-to-best/1".equals(variant)) {
      for (int j = 0; j < numberOfVariables; j++) {
        double value;
        value = currentSolution.getVariableValue(j) + k * (parentSolutions.get(2).getVariableValue(j) -
                currentSolution.getVariableValue(j)) +
                f * (parentSolutions.get(0).getVariableValue(j) - parentSolutions.get(1).getVariableValue(j));

        if (value < child.getLowerBound(j)) {
          value = child.getLowerBound(j);
        }
        if (value > child.getUpperBound(j)) {
          value = child.getUpperBound(j);
        }

        child.setVariableValue(j, value);
      }
    } else if ("current-to-rand/1/bin".equals(variant) ||
            "current-to-best/1/bin".equals(variant)) {
      for (int j = 0; j < numberOfVariables; j++) {
        if (PseudoRandom.randDouble(0, 1) < cr || j == jrand) {
          double value;
          value = currentSolution.getVariableValue(j) + k * (parentSolutions.get(2).getVariableValue(j) -
                  currentSolution.getVariableValue(j)) +
                  f * (parentSolutions.get(0).getVariableValue(j) - parentSolutions.get(1).getVariableValue(j));

          if (value < child.getLowerBound(j)) {
            value = child.getLowerBound(j);
          }
          if (value > child.getUpperBound(j)) {
            value = child.getUpperBound(j);
          }

          child.setVariableValue(j, value);
        } else {
          double value;
          value = currentSolution.getVariableValue(j);
          child.setVariableValue(j, value);
        }
      }
    } else if ("current-to-rand/1/exp".equals(variant) ||
            "current-to-best/1/exp".equals(variant)) {
      for (int j = 0; j < numberOfVariables; j++) {
        if (PseudoRandom.randDouble(0, 1) < cr || j == jrand) {
          double value;
          value = currentSolution.getVariableValue(j) + k * (parentSolutions.get(2).getVariableValue(j) -
                  currentSolution.getVariableValue(j)) +
                  f * (parentSolutions.get(0).getVariableValue(j) - parentSolutions.get(1).getVariableValue(j));

          if (value < child.getLowerBound(j)) {
            value = child.getLowerBound(j);
          }
          if (value > child.getUpperBound(j)) {
            value = child.getUpperBound(j);
          }

          child.setVariableValue(j, value);
        } else {
          cr = 0.0;
          double value;
          value = currentSolution.getVariableValue(j);
          child.setVariableValue(j, value);
        }
      }
    } else {
      JMetalLogger.logger.severe("DifferentialEvolutionCrossover.execute: " +
              " unknown DE variant (" + variant + ")");
      Class<String> cls = String.class;
      String name = cls.getName();
      throw new JMetalException("Exception in " + name + ".execute()");
    }

    ArrayList<DoubleSolution> result = new ArrayList<>(1) ;
    result.add(child) ;
    return result;
  }
}
