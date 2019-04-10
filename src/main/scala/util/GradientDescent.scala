package util

import breeze.linalg.{DenseMatrix, DenseVector}
import com.cibo.evilplot.displayPlot

import scala.collection.mutable.ListBuffer
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
import model.{GradientDescentHistoryPoint, SimplePoint}


object GradientDescent {
  def diffPoint(point: SimplePoint, theta0: Double, theta1: Double) = (theta1 * point.x + theta0) - point.y

  def theta0Updated(data: List[SimplePoint], theta0: Double, theta1: Double, learningRate: Double): Double = {
    def stepPoint(point: SimplePoint) = diffPoint(point, theta0, theta1)
    val numberOfDataPointsDivisor: Double = 1/data.length.toDouble
    val adjustment = data.foldLeft(0.0)((sum, point) => sum + stepPoint(point)) * numberOfDataPointsDivisor

    theta0 - (adjustment * learningRate)
  }

  def theta1Updated(data: List[SimplePoint], theta0: Double, theta1: Double, learningRate: Double): Double = {
    def stepPoint(point: SimplePoint) = diffPoint(point, theta0, theta1) * point.x
    val numberOfDataPointsDivisor: Double = 1/data.length.toDouble
    val adjustment = data.foldLeft(0.0)((sum, point) => sum + stepPoint(point)) * numberOfDataPointsDivisor

    theta1 - (adjustment * learningRate)
  }

  case class LearnedParameterSet(theta0: Double, theta1: Double, history: List[GradientDescentHistoryPoint])

  def gradientDescent(
                       data: List[SimplePoint],
                       startingTheta0: Double,
                       startingTheta1: Double,
                       learningRate: Double,
                       iters: Int): LearnedParameterSet = {

    var theta0 = startingTheta0
    var theta1 = startingTheta1
    var cost = LinearErrorCalculator.linearMeanSquaredError(data, theta0, theta1)
    var gradientDescentHistory = new ListBuffer[GradientDescentHistoryPoint]
    println(s"starting cost ${cost}")

    var counter = 0
    while(counter < iters && cost > 0.001) {

      println(s"old theta 0 ${theta0}")
      println(s"old theta 1 ${theta1}")

      println(s"theta 0 iter ${theta0Updated(data, theta0, theta1, learningRate)}")
      println(s"theta 1 iter ${theta1Updated(data, theta0, theta1, learningRate)}")

      val tempTheta0 = theta0Updated(data, theta0, theta1, learningRate)
      val tempTheta1 = theta1Updated(data, theta0, theta1, learningRate)

      theta0 = tempTheta0
      theta1 = tempTheta1

      cost = LinearErrorCalculator.linearMeanSquaredError(data, theta0, theta1)
      println(s"new theta 0 ${theta0}")
      println(s"new theta 1 ${theta1}")
      println(s"new cost ${cost}")
      println(s"we are on iter ${counter}")

      gradientDescentHistory += GradientDescentHistoryPoint(counter, cost)

      counter = counter + 1
    }

    LearnedParameterSet(theta0, theta1, gradientDescentHistory.toList)
  }
}

object VectorisedGradientDescent {
  def diffPoint(point: SimplePoint, theta0: Double, theta1: Double) = (theta1 * point.x + theta0) - point.y

//  def thetaUpdated(featureData: DenseMatrix[Double], theta: DenseVector[Double], y: DenseVector[Double], learningRate: Double): DenseVector[Double] = {
//    val h = theta.toDenseMatrix * featureData
//
//    val errors = h - y.toDenseMatrix
//
//    val divisor = learningRate/featureData.cols.toDouble
//    val thetaChange: DenseMatrix[Double] = (featureData * errors.t) * divisor
//
//    theta - thetaChange
//  }

  case class LearnedParameterSet(theta: DenseVector[Double], history: List[GradientDescentHistoryPoint])

  def vectorisedThetaUpdate(xData: DenseMatrix[Double], yData: DenseMatrix[Double], theta: DenseMatrix[Double], learningRate: Double): DenseMatrix[Double] = {
    val thetaT = theta.t
    val hy = theta * xData

    val errs = (hy - yData)

    val predictionDifferenceTimesX = (xData *(hy - yData).t).t

    val multiplier = (learningRate/xData.rows.toDouble)
    val adjustment =  predictionDifferenceTimesX *:* multiplier

    theta :-= adjustment
  }

  def gradientDescent(
    xData: DenseMatrix[Double],
    yData: DenseVector[Double],
    theta: DenseVector[Double],
    learningRate: Double,
    iters: Int): LearnedParameterSet = {

    var cost = VectorisedErrorCalculator.linearMeanAbsoluteError(xData, yData, theta)
    var gradientDescentHistory = new ListBuffer[GradientDescentHistoryPoint]

    println(s"starting cost ${cost}")

    var counter = 0
    var thetas = theta

    while(counter < iters && cost > 0.001) {

      println(s"old thetas ${thetas.toString()}")

      val tempThetas: DenseMatrix[Double] = vectorisedThetaUpdate(xData, yData.toDenseMatrix, thetas.toDenseMatrix, learningRate)
      println(s"Theta updates ${tempThetas.toString()}")

//      val updatedThetas = thetas - tempThetas.toDenseVector
//      println(s"new updated Theta  ${updatedThetas.toString()}")

//      thetas = thetas - tempThetas.toDenseVector

      thetas = tempThetas.toDenseVector
      cost = VectorisedErrorCalculator.linearMeanAbsoluteError(xData, yData, thetas)
      println(s"new cost ${cost}")
      println(s"new thetas 0 ${thetas.toString()}")
      println(s"we are on iter ${counter}")

      gradientDescentHistory += GradientDescentHistoryPoint(counter, cost)

      counter = counter + 1
    }

    LearnedParameterSet(thetas, gradientDescentHistory.toList)
  }


  import breeze.linalg.DenseMatrix.horzcat
  import breeze.linalg._

  import scala.annotation.tailrec

  type History = DenseMatrix[Double]
  type Theta = DenseMatrix[Double]

  def computeCost(X: DenseMatrix[Double], y: DenseMatrix[Double], theta: Theta): Double = {
    val m = y.rows
    sum((X * theta - y) :^ 2d) / (2 * m)
  }

  def gradientDescentRecursive(X: DenseMatrix[Double], y: DenseMatrix[Double],
                      theta: Theta, alpha: Double, numberItr: Int): LearnedParameterSet = {
    val m = y.rows

    @tailrec
    def descend(newTheta: DenseMatrix[Double], history: DenseMatrix[Double],
                decentRemaining: Int):
    LearnedParameterSet  = {
      decentRemaining match { //ls.zipWithIndex.foreach{ case (e, i) => println(i+" "+e) }
        case 0 => LearnedParameterSet(newTheta.toDenseVector, history.toArray.zipWithIndex.map{case (e, i) => GradientDescentHistoryPoint(i, e)}.toList)
        case _ =>
          val htheta = X * newTheta
          val theta0 = newTheta(0,0) - alpha / m * sum((htheta - y) :* X(::, 0).t)
          val theta1 = newTheta(1,0) - alpha / m * sum((htheta - y) :* X(::, 1).t)
          val cost = computeCost(X, y, DenseMatrix(theta0, theta1))
          descend(DenseMatrix(theta0, theta1),
            horzcat(DenseMatrix(cost), history), decentRemaining - 1)
      }
    }

    descend(theta, DenseMatrix(computeCost(X, y, theta)), numberItr)
  }
}
