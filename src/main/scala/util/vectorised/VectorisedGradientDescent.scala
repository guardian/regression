package util.vectorised

import breeze.linalg.{DenseMatrix, DenseVector}
import model.GradientDescentHistoryPoint

import scala.collection.mutable.ListBuffer

object VectorisedGradientDescent {

  case class LearnedParameterSet(theta: DenseVector[Double], history: List[GradientDescentHistoryPoint])

  def vectorisedThetaUpdate(xData: DenseMatrix[Double], yData: DenseMatrix[Double], theta: DenseMatrix[Double], learningRate: Double, lambda: Option[Double]): DenseMatrix[Double] = ???

  def gradientDescent(
                       xData: DenseMatrix[Double],
                       yData: DenseVector[Double],
                       theta: DenseVector[Double],
                       learningRate: Double,
                       iters: Int,
                       lambda: Option[Double]): LearnedParameterSet = ???
}