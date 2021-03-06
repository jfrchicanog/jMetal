//  CrowdingDistanceImpl.java
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

package org.uma.jmetal.util.solutionattribute.impl;

import org.uma.jmetal.core.Solution;
import org.uma.jmetal.util.solutionattribute.DensityEstimator;
import org.uma.jmetal.util.comparator.ObjectiveComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class implements some utilities for calculating distances
 */
public class CrowdingDistance implements DensityEstimator<Solution<?>, Double> {

  /**
   * Assigns crowding distances to all solutions in a <code>SolutionSet</code>.
   *
   * @param solutionSet The <code>SolutionSet</code>.
   * @throws org.uma.jmetal45.util.JMetalException
   */

  @Override
  public void computeDensityEstimator(List<Solution<?>> solutionSet) {
    int size = solutionSet.size();

    if (size == 0) {
      return;
    }

    if (size == 1) {
      solutionSet.get(0).setAttribute(getAttributeID(), Double.POSITIVE_INFINITY);
      return;
    }

    if (size == 2) {
      solutionSet.get(0).setAttribute(getAttributeID(), Double.POSITIVE_INFINITY);
      solutionSet.get(1).setAttribute(getAttributeID(), Double.POSITIVE_INFINITY);

      return;
    }

    //Use a new SolutionSet to avoid altering the original solutionSet
    List<Solution<?>> front = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      front.add(solutionSet.get(i));
    }

    for (int i = 0; i < size; i++) {
      front.get(i).setAttribute(getAttributeID(), 0.0);
    }

    double objetiveMaxn;
    double objetiveMinn;
    double distance;

    int numberOfObjectives = solutionSet.get(0).getNumberOfObjectives() ;

    for (int i = 0; i < numberOfObjectives; i++) {
      // Sort the population by Obj n
      Collections.sort(front, new ObjectiveComparator(i)) ;
      objetiveMinn = front.get(0).getObjective(i);
      objetiveMaxn = front.get(front.size() - 1).getObjective(i);

      //Set de crowding distance
      front.get(0).setAttribute(getAttributeID(), Double.POSITIVE_INFINITY);
      front.get(size - 1).setAttribute(getAttributeID(), Double.POSITIVE_INFINITY);

      for (int j = 1; j < size - 1; j++) {
        distance = front.get(j + 1).getObjective(i) - front.get(j - 1).getObjective(i);
        distance = distance / (objetiveMaxn - objetiveMinn);
        distance += (double)front.get(j).getAttribute(getAttributeID());
        front.get(j).setAttribute(getAttributeID(), distance);
      }
    }
  }

  @Override
  public void setAttribute(Solution<?> solution, Double value) {
    solution.setAttribute(getAttributeID(), value);
  }

  @Override
  public Double getAttribute(Solution<?> solution) {
    return (Double) solution.getAttribute(getAttributeID());
  }

  @Override
  public Object getAttributeID() {
    return this.getClass();
  }
}

