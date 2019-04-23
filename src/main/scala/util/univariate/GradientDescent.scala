package util.univariate

import model.GradientDescentHistoryPoint
import model.univariate.SimplePoint

object GradientDescent {

  def theta0Updated(data: List[SimplePoint], theta0: Double, theta1: Double, alpha: Double): Double = ???

  def theta1Updated(data: List[SimplePoint], theta0: Double, theta1: Double, alpha: Double): Double = ???

  case class LearnedParameterSet(theta0: Double, theta1: Double, history: List[GradientDescentHistoryPoint])

  def gradientDescent(
                       data: List[SimplePoint],
                       startingTheta0: Double,
                       startingTheta1: Double,
                       alpha: Double,
                       iters: Int): LearnedParameterSet = {

    var lps = LearnedParameterSet(startingTheta0, startingTheta1, List(
      GradientDescentHistoryPoint(0, LinearErrorCalculator.linearMeanSquaredError(data, startingTheta0, startingTheta1))
    ))

    // history will be in reverse order
    while(lps.history.length < iters) {
      var nextTheta0 = theta0Updated(data, lps.theta0, lps.theta1, alpha)
      var nextTheta1 = theta1Updated(data, lps.theta0, lps.theta1, alpha)

      lps = LearnedParameterSet(
        nextTheta0,
        nextTheta1,
        GradientDescentHistoryPoint(
          lps.history.length,
          LinearErrorCalculator.linearMeanSquaredError(data, nextTheta0, nextTheta1)
        ) :: lps.history
      )
    }

    // so reverse it before returning
    lps.copy(history = lps.history.reverse)
  }
}
