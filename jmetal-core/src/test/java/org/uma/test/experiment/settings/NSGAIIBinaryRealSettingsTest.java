//  NSGAIIBinary_SettingsTest.java
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
//  along with this program.  If not, see <http://www.gnu.org/licenses/>

package org.uma.test.experiment.settings;

import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal45.core.Problem;
import org.uma.jmetal45.experiment.Settings;
import org.uma.jmetal45.experiment.settings.NSGAIIBinaryRealSettings;
import org.uma.jmetal45.metaheuristic.multiobjective.nsgaII.NSGAII;
import org.uma.jmetal45.operator.crossover.SinglePointCrossover;
import org.uma.jmetal45.operator.mutation.BitFlipMutation;
import org.uma.jmetal45.problem.multiobjective.zdt.ZDT1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: antelverde
 * Date: 26/06/13
 * Time: 19:41
 * To change this template use File | Settings | File Templates.
 */
public class NSGAIIBinaryRealSettingsTest {
  Properties configuration_ ;

  @Before
  public void init() throws FileNotFoundException, IOException {
    configuration_ = new Properties();
    InputStreamReader isr = new InputStreamReader(new FileInputStream(ClassLoader.getSystemResource("NSGAIIBinary.conf").getPath()));
    configuration_.load(isr);
  }

  @Test
  public void testConfigure() throws Exception {
    double epsilon = 0.000000000000001 ;
    Settings nsgaIISettings = new NSGAIIBinaryRealSettings("ZDT1");
    NSGAII algorithm = (NSGAII)nsgaIISettings.configure() ;
    Problem problem = new ZDT1("BinaryReal") ;

    SinglePointCrossover crossover =  (SinglePointCrossover) algorithm.getCrossoverOperator() ;
    double pc = crossover.getCrossoverProbability() ;
    BitFlipMutation mutation = (BitFlipMutation)algorithm.getMutationOperator() ;
    double pm = mutation.getMutationProbability() ;

    assertEquals(100, algorithm.getPopulationSize());
    assertEquals(25000, algorithm.getMaxEvaluations()) ;

    assertEquals(0.9, pc, epsilon);
    assertEquals(1.0/problem.getNumberOfBits(), pm, epsilon);
  }

  @Test
  public void testConfigure2() throws Exception {
    double epsilon = 0.000000000000001 ;
    Settings nsgaIISettings = new NSGAIIBinaryRealSettings("ZDT1");
    NSGAII algorithm = (NSGAII)nsgaIISettings.configure(configuration_) ;
    Problem problem = new ZDT1("BinaryReal") ;

    SinglePointCrossover crossover =  (SinglePointCrossover) algorithm.getCrossoverOperator() ;
    double pc = crossover.getCrossoverProbability() ;
    BitFlipMutation mutation = (BitFlipMutation)algorithm.getMutationOperator() ;
    double pm = mutation.getMutationProbability() ;

    assertEquals(100, algorithm.getPopulationSize());
    assertEquals(25000, algorithm.getMaxEvaluations()) ;

    assertEquals(0.9, pc, epsilon);
    assertEquals(1.0/problem.getNumberOfBits(), pm, epsilon);
  }
}
