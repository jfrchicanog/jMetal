package org.uma.jmetal45.qualityindicator;

import org.uma.jmetal45.qualityindicator.util.MetricsUtil;
import org.uma.jmetal45.util.JMetalLogger;
import org.uma.jmetal45.util.JMetalException;

/**
 * Created by Antonio J. Nebro on 21/07/14.
 */
public class EpsilonCalculator {
  public static void main(String[] args) throws NumberFormatException, JMetalException {
    double indValue;
    MetricsUtil utils = new MetricsUtil() ;

    if (args.length < 2) {
      throw new JMetalException(
        "Error using Epsilon. Type: \n java AdditiveEpsilon " + "<FrontFile>"
          + "<TrueFrontFile>"
      );
    }

    Epsilon qualityIndicator = new Epsilon();
    double[][] solutionFront = utils.readFront(args[0]);
    double[][] trueFront = utils.readFront(args[1]);

    indValue = qualityIndicator.epsilon(solutionFront, trueFront);

    JMetalLogger.logger.info(""+indValue);
  }
}
