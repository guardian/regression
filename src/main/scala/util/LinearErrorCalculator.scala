package util

import breeze.linalg.{DenseMatrix, DenseVector, sum}
import breeze.numerics.abs
import model.SimplePoint


object LinearErrorCalculator {

  def linearMeanSquaredError(data: List[SimplePoint], theta0: Double, theta1: Double): Double = {
    def h(x: Double) = theta1 * x + theta0
    def errorForAPoint(point: SimplePoint) = (h(point.x) - point.y) * (h(point.x) - point.y)

    val summedErrors = data.foldLeft(0.0)((sum, point) => sum + errorForAPoint(point))

    val numberOfDataPointsDivisor: Double = 0.5 * (1/data.length.toDouble)
    summedErrors * numberOfDataPointsDivisor
  }

  def linearMeanAbsoluteError(data: List[SimplePoint], theta0: Double, theta1: Double): Double = {
    def h(x: Double) = theta1 * x + theta0
    def errorForAPoint(point: SimplePoint) = Math.abs(h(point.x) - point.y)

    val summedErrors = data.foldLeft(0.0)((sum, point) => sum + errorForAPoint(point))

    val numberOfDataPointsDivisor: Double = 1/data.length.toDouble
    summedErrors * numberOfDataPointsDivisor
  }
}

object VectorisedErrorCalculator {

  def h(theta: DenseVector[Double], data: DenseMatrix[Double]): DenseMatrix[Double] = theta.toDenseMatrix * data

  def linearMeanSquaredError(data: DenseMatrix[Double], y: DenseVector[Double], theta: DenseVector[Double]): Double = {

    val differences: DenseVector[Double] = (h(theta, data) - y.t).toDenseVector
    val differencesSquared = differences *:* differences

    val summed = sum(differencesSquared)

    summed / (data.cols.toDouble * 2)

  }

  def linearMeanAbsoluteError(data: DenseMatrix[Double], y: DenseVector[Double], theta: DenseVector[Double]): Double = {

    val errorForAPoint = abs(h(theta, data) - y.t)

    val summedErrors = sum(errorForAPoint)

    val numberOfDataPointsDivisor: Double = 1/data.cols.toDouble
    summedErrors * numberOfDataPointsDivisor
  }
}
