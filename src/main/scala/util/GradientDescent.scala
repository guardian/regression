package util

import com.cibo.evilplot.displayPlot

import scala.collection.mutable.ListBuffer
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
import model.{GradientDescentHistoryPoint, SimplePoint}


object GradientDescent {

  def theta0Updated(data: List[SimplePoint], theta0: Double, theta1: Double, learningRate: Double): Double = ???

  def theta1Updated(data: List[SimplePoint], theta0: Double, theta1: Double, learningRate: Double): Double = ???

  case class LearnedParameterSet(theta0: Double, theta1: Double, history: List[GradientDescentHistoryPoint])

  def gradientDescent(
                       data: List[SimplePoint],
                       startingTheta0: Double,
                       startingTheta1: Double,
                       learningRate: Double,
                       iters: Int): LearnedParameterSet = ???
}
