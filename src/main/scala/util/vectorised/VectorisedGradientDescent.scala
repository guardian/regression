package util.vectorised

import breeze.linalg.{DenseMatrix, DenseVector}
import model.GradientDescentHistoryPoint

import scala.collection.mutable.ListBuffer

object VectorisedGradientDescent {

  case class LearnedParameterSet(theta: DenseVector[Double], history: List[GradientDescentHistoryPoint])

  def vectorisedThetaUpdate(xData: DenseMatrix[Double], yData: DenseMatrix[Double], theta: DenseMatrix[Double], learningRate: Double, lambda: Option[Double]): DenseMatrix[Double] = {
    val m = xData.rows

    val thetaShrinker = lambda map { reg =>
      1.0 - ((learningRate * reg)/m)
    }

    val hy = theta * xData

    val predictionDifferenceTimesX = (xData * (hy - yData).t).t

    val multiplier = (learningRate / xData.rows.toDouble)
    val adjustment = predictionDifferenceTimesX *:* multiplier

    val thetaToAdjust = thetaShrinker match {
      case Some(scalar) => theta *:* scalar
      case None => theta
    }

    thetaToAdjust :-= adjustment
  }

  def gradientDescent(
                       xData: DenseMatrix[Double],
                       yData: DenseVector[Double],
                       theta: DenseVector[Double],
                       learningRate: Double,
                       iters: Int,
                       lambda: Option[Double]): LearnedParameterSet = {

    var cost = VectorisedErrorCalculator.linearMeanSquaredError(xData, yData, theta)
    var gradientDescentHistory = new ListBuffer[GradientDescentHistoryPoint]

    println(s"starting cost ${cost}")

    var counter = 0
    var thetas = theta

    while (counter < iters && cost > 0.001) {

      println(s"old thetas ${thetas.toString()}")

      val tempThetas: DenseMatrix[Double] = vectorisedThetaUpdate(xData, yData.toDenseMatrix, thetas.toDenseMatrix, learningRate, lambda)
      println(s"Theta updates ${tempThetas.toString()}")

      thetas = tempThetas.toDenseVector
      cost = VectorisedErrorCalculator.linearMeanSquaredError(xData, yData, thetas)
      println(s"new cost ${cost}")
      println(s"new thetas 0 ${thetas.toString()}")
      println(s"we are on iter ${counter}")

      gradientDescentHistory += GradientDescentHistoryPoint(counter, cost)

      counter = counter + 1
    }

    LearnedParameterSet(thetas, gradientDescentHistory.toList)
  }
}