//  FastSMSEMOATest.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//
//  Copyright (c) 2014 Antonio J. Nebro
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

package org.uma.test.metaheuristic.multiobjective.smsemoa;

import org.junit.Test;
import org.uma.jmetal45.core.Algorithm;
import org.uma.jmetal45.core.SolutionSet;
import org.uma.jmetal45.experiment.settings.FastSMSEMOASettings;
import org.uma.jmetal45.metaheuristic.multiobjective.smsemoa.FastSMSEMOA;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Antonio J. Nebro on 02/06/14.
 */
public class FastSMSEMOATest {
  Algorithm algorithm;

  @Test
  public void numberOfReturnedSolutionsInEasyProblemTest() throws Exception {
    algorithm = new FastSMSEMOASettings("Kursawe").configure() ;

    SolutionSet solutionSet = algorithm.execute() ;
    /*
    Rationale: the default problem is Kursawe, and usually SMS-EMOA, configured with standard
    settings, should return 100 solutions
     */
    int defaultMaxEvaluations = 25000 ;
    assertTrue(solutionSet.size() >= 98) ;
    assertEquals(defaultMaxEvaluations, ((FastSMSEMOA) algorithm).getEvaluations());
  }
}
