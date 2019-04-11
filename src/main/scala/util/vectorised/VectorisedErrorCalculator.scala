package util.vectorised

import breeze.linalg.{DenseMatrix, DenseVector, sum}

object VectorisedErrorCalculator {

  def h(theta: DenseVector[Double], data: DenseMatrix[Double]): DenseMatrix[Double] = theta.toDenseMatrix * data

  def linearMeanSquaredError(data: DenseMatrix[Double], y: DenseVector[Double], theta: DenseVector[Double]): Double = {

    val differences: DenseVector[Double] = (h(theta, data) - y.t).toDenseVector
    val differencesSquared = differences *:* differences

    val summed = sum(differencesSquared)

    summed / (data.cols.toDouble * 2)

  }
}