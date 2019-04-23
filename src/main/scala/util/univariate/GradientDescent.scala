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
                       iters: Int): LearnedParameterSet = ???
}
