//  BinarySteadyStateGeneticAlgorithmRunner.java
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

package org.uma.jmetal45.runner.singleobjective;

import org.uma.jmetal45.core.Algorithm;
import org.uma.jmetal45.core.Problem;
import org.uma.jmetal45.core.SolutionSet;
import org.uma.jmetal45.metaheuristic.singleobjective.geneticalgorithm.SteadyStateGeneticAlgorithm;
import org.uma.jmetal45.operator.crossover.Crossover;
import org.uma.jmetal45.operator.crossover.SinglePointCrossover;
import org.uma.jmetal45.operator.mutation.BitFlipMutation;
import org.uma.jmetal45.operator.mutation.Mutation;
import org.uma.jmetal45.operator.selection.BinaryTournament;
import org.uma.jmetal45.operator.selection.Selection;
import org.uma.jmetal45.problem.singleobjective.OneMax;
import org.uma.jmetal45.util.AlgorithmRunner;
import org.uma.jmetal45.util.JMetalLogger;
import org.uma.jmetal45.util.fileoutput.DefaultFileOutputContext;
import org.uma.jmetal45.util.fileoutput.SolutionSetOutput;

/**
 * This class runs a single-objective genetic algorithm (GA). The GA can be
 * a steady-state GA (class SteadyStateGeneticAlgorithm), a generational GA (class gGA), a synchronous
 * cGA (class SynchronousCellularGeneticAlgorithm) or an asynchronous cGA (class AsynchronousCellularGA). The OneMax
 * problem is used to org.uma.test the algorithms.
 */
public class BinarySteadyStateGeneticAlgorithmRunner {

  public static void main(String[] args) throws Exception {
    Problem problem;
    Algorithm algorithm;
    Crossover crossover;
    Mutation mutation;
    Selection selection;

    int bits = 512;
    problem = new OneMax("Binary", bits);

    crossover = new SinglePointCrossover.Builder()
           .setProbability(0.9)
            .build() ;

    mutation = new BitFlipMutation.Builder()
            .setProbability(1.0/bits)
            .build() ;

    selection = new BinaryTournament.Builder()
            .build() ;

    algorithm = new SteadyStateGeneticAlgorithm.Builder(problem)
            .setPopulationSize(100)
            .setMaxEvaluations(25000)
            .setCrossover(crossover)
            .setMutation(mutation)
            .setSelection(selection)
            .build() ;

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
            .execute() ;

    SolutionSet population = algorithmRunner.getSolutionSet() ;
    long computingTime = algorithmRunner.getComputingTime() ;

    new SolutionSetOutput.Printer(population)
            .setSeparator("\t")
            .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
            .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
            .print();

    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
    JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
    JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");
  }
}
