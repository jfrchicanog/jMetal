package org.uma.test.operator.selection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal45.core.Problem;
import org.uma.jmetal45.core.Solution;
import org.uma.jmetal45.core.SolutionSet;
import org.uma.jmetal45.operator.selection.WorstSolutionSelection;
import org.uma.jmetal45.problem.multiobjective.Kursawe;
import org.uma.jmetal45.util.JMetalException;
import org.uma.jmetal45.util.comparator.ObjectiveComparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WorstSolutionSelectionTest {
  static final int POPULATION_SIZE = 10 ;
  SolutionSet solutionSet ;
  WorstSolutionSelection selection;
  Problem problem ;

  @Before
  public void setUp() throws Exception {
    selection = new WorstSolutionSelection.Builder(new ObjectiveComparator(0))
            .build() ;

    problem = new Kursawe("Real") ;
    solutionSet = new SolutionSet(POPULATION_SIZE) ;
    for (int i = 0 ; i < POPULATION_SIZE; i++) {
      Solution solution = new Solution(problem) ;
      solution.setObjective(0, i);
      solutionSet.add(solution) ;
    }
  }

  @Test
  public void executeWithCorrectParametersTest() {
    assertNotNull(selection.execute(solutionSet));
    assertEquals(POPULATION_SIZE-1, selection.execute(solutionSet));
  }

  @Test (expected = JMetalException.class)
  public void executeWithPopulationSizeZeroTest() {
    solutionSet = new SolutionSet(0) ;
    selection.execute(solutionSet) ;
  }

  @Test (expected = JMetalException.class)
  public void wrongParameterClassTest() {
    selection.execute(new Integer(4)) ;
  }

  @Test (expected = JMetalException.class)
  public void nullParameterTest() {
    selection.execute(null) ;
  }

  @After
  public void tearDown() throws Exception {
    solutionSet = null ;
    selection = null ;
  }
}
